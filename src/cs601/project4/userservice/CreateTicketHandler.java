package cs601.project4.userservice;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import cs601.project4.database.Database;
import cs601.project4.server.CS601Handler;
import cs601.project4.server.Constants;
import cs601.project4.utility.ConnectionHelper;

/**
 * Internal API that takes as input id from URL, contacts Events Service
 * to check availability of tickets, then updates Tickets table.
 * 
 * Need to confirm that the users transferring to and from exist.
 * @author nkebbas
 *
 */
public class CreateTicketHandler extends CS601Handler {
	
	public synchronized void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}
	
	public synchronized void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException {	
		/* Parse the URL to get the UserID */
		boolean success = false;
		String[] parameters = request.getPathInfo().split("/");
		String getBody = "";
		if (parameters.length == 4 && isNumeric(parameters[1])) {
			/* Parse the request and get Event ID and number of tickets */
			getBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			/* Check for Correct JSON Issues and No null necessary parameters */
			JsonParser parser = new JsonParser();
			JsonObject jsonBody = new JsonObject();
			
			try {
				parser.parse(getBody);
			} catch (JsonParseException j) {
		    		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		    		return;
			}
			if (parser.parse(getBody).isJsonNull()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
			jsonBody = (JsonObject) parser.parse(getBody);
			
			int userId = Integer.parseInt(parameters[1]);
			if (jsonBody.get("eventid") == null || jsonBody.get("tickets") == null) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
			int eventId = jsonBody.get("eventid").getAsInt();
			int tickets = jsonBody.get("tickets").getAsInt();
			
			/* Open a connection with GET request to confirm event Exists */
	       	URL eventUrl = new URL (Constants.HOST + Constants.EVENTS_URL + "/" + eventId);
	        HttpURLConnection eventConnect = (HttpURLConnection) eventUrl.openConnection();
			eventConnect = ConnectionHelper.tryGetConnection(eventConnect);
			
			if (eventConnect.getResponseCode() != 200) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			/* Check that designated number of tickets is available first */
			
			/* Open a new POST Request to event server and update ticket availability */
			byte[] postData = getBody.getBytes( StandardCharsets.UTF_8 );
	       	URL url = new URL (Constants.HOST + Constants.EVENTS_URL + "/tickets/availability");
	        HttpURLConnection connect = (HttpURLConnection) url.openConnection();
	        connect = ConnectionHelper.tryPostConnection(connect, postData);
			
			/* Body is written above, so just connect to POST */
	        connect.connect();
	        
	        /* There are enough available tickets so proceed with updating the tickets table */
			Database db = Database.getInstance();
	        if (connect.getResponseCode() == 200) {
		        /* if we get a 200 back, return affirmative, else return that there was an error. */
				
				/* Loop through and create a row for each ticket */
				for (int i = 0; i < tickets; i++) {
					try {
						success = db.getDBManager().addTicket(userId, eventId, "users", "tickets");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (success) {
					response.setStatus(HttpServletResponse.SC_OK);
				} else {
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				}
				
	        } else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	
	public static boolean isNumeric(String s) {
		  try {  
		    int num = Integer.parseInt(s);
		  }	catch (NumberFormatException nfe) {  
		    return false;  
		  } return true;  
		}
}

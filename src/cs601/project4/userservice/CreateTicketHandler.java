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
import com.google.gson.JsonParser;

import cs601.project4.database.Database;
import cs601.project4.server.CS601Handler;
import cs601.project4.server.Constants;

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
			JsonParser parser = new JsonParser();
			JsonObject jBody = (JsonObject) parser.parse(getBody);
			int userId = Integer.parseInt(parameters[1]);
			if (jBody.get("eventid") == null || jBody.get("tickets") == null) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
			int eventId = jBody.get("eventid").getAsInt();
			int tickets = jBody.get("tickets").getAsInt();

			/* Check that designated number of tickets is available first */
			
			/* Open a new POST Request to event server and update ticket availability */
			byte[] postData = getBody.getBytes( StandardCharsets.UTF_8 );
	       	URL url = new URL (Constants.HOST + Constants.EVENTS_URL + "/tickets/availability");
	        HttpURLConnection connect = (HttpURLConnection) url.openConnection();
			connect.setDoOutput( true );
	        connect.setRequestMethod("POST");
			connect.setRequestProperty("Content-Type", "application/json"); 
			connect.setRequestProperty("charset", "utf-8");
			connect.setRequestProperty("Content-Length", Integer.toString( postData.length));
			try( DataOutputStream wr = new DataOutputStream( connect.getOutputStream())) {
				wr.write(postData);
			}
			
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

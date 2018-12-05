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
 * Get user id from URL, then create ticket row in ticketDB, 
 * then update available tickets in Event DB.
 * 
 * Need to confirm that the users transferring to and from exist.
 * @author nkebbas
 *
 */
public class CreateTicketHandler extends CS601Handler {
	
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}
	
	public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException {	
		/* Parse the URL to get the UserID */
		String[] parameters = request.getPathInfo().split("/");
		String getBody = "";
		if (parameters.length == 4 && isNumeric(parameters[1])) {
			
			/* Parse the request and get Event ID and number of tickets */
			getBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			JsonParser parser = new JsonParser();
			JsonObject jsonBody = (JsonObject) parser.parse(getBody);
			int userId = Integer.parseInt(parameters[1]);
			int eventId = jsonBody.get("eventid").getAsInt();
			int tickets = jsonBody.get("tickets").getAsInt();

			/* Check that designated number of tickets is available first */
			
			/* Open a new POST Request to event server and update ticket availability */
			byte[] postData = getBody.getBytes( StandardCharsets.UTF_8 );
	       	URL url = new URL (Constants.HOST + Constants.EVENTS_URL + "/tickets/availability");
	        HttpURLConnection connect = (HttpURLConnection) url.openConnection();
			connect.setDoOutput( true );
			connect.setInstanceFollowRedirects( false );
	        connect.setRequestMethod("POST");
			connect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
			connect.setRequestProperty("charset", "utf-8");
			connect.setRequestProperty("Content-Length", Integer.toString( postData.length));
			connect.setUseCaches( false );
			try( DataOutputStream wr = new DataOutputStream( connect.getOutputStream())) {
				wr.write(postData);
			}
			
			/* Body is written above, so just connect to POST */
	        connect.connect();  
	        System.out.println("Response: " + connect.getResponseCode());
	        
	        /* There are enough available tickets so proceed with updating the tickets table */
			Database db = Database.getInstance();
	        if (connect.getResponseCode() == 200) {
		        /* if we get a 200 back, return affirmative, else return that there was an error. */
				
				/* Loop through and create a row for each ticket */
				for (int i = 0; i < tickets; i++) {
					try {
						db.getDBManager().addTicket(userId, eventId, "tickets");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				response.setStatus(HttpServletResponse.SC_OK);
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

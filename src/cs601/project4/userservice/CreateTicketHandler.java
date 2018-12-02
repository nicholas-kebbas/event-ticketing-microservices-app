package cs601.project4.userservice;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cs601.project4.database.Database;
import cs601.project4.database.Event;
import cs601.project4.eventservice.CreateEventHandler;
import cs601.project4.server.CS601Handler;
import cs601.project4.server.Constants;

/**
 * Get user id from URL, then create ticket row in ticketDB, 
 * then update available tickets in Event DB
 * @author nkebbas
 *
 */
public class CreateTicketHandler extends CS601Handler {
	
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}
	
	public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException {	
		System.out.println("do post create ticket");
		
		/* Parse the URL to get the UserID */
		String[] parameters = request.getPathInfo().split("/");
		System.out.println(parameters.length);
		if (parameters.length == 4 && isNumeric(parameters[1])) {
			int userId = Integer.parseInt(parameters[1]);
			System.out.println(userId);
			/* Parse the request and get Event ID and number of tickets */
			String getBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			System.out.println("Body: " + request.getReader().readLine());
			JsonParser parser = new JsonParser();
			JsonObject jsonBody = (JsonObject) parser.parse(getBody);
			int eventId = jsonBody.get("eventid").getAsInt();
			int tickets = jsonBody.get("tickets").getAsInt();
			Database db = Database.getInstance();
			/* Loop through and create a row for each ticket */
			for (int i = 0; i < tickets; i++) {
				try {
					db.getDBManager().addTicket(userId, eventId, "tickets");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		
		/* Open a new Request to event server and update ticket availability */
	       	URL url = new URL (Constants.HOST + Constants.EVENTS_URL + "/tickets/availability");
	        System.out.println(url);
	        HttpURLConnection connect = (HttpURLConnection) url.openConnection();
	        connect.setRequestMethod("POST");
	        connect.connect();
	        System.out.println(connect.getResponseCode());
	        
	        
	}
	
	public static boolean isNumeric(String s) {
		  try {  
		    int num = Integer.parseInt(s);
		  }	catch (NumberFormatException nfe) {  
		    return false;  
		  } return true;  
		}
}

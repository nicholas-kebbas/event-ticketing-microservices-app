package cs601.project4.eventservice;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import cs601.project4.database.Database;
import cs601.project4.server.CS601ConnectionWrapper;
import cs601.project4.server.CS601Handler;
import cs601.project4.server.Constants;

/**
 * Purchase ticket to event. This will check if tickets are available, 
 * decrement the available tickets number,
 * and then open a connection to the user service
 * 
 * There's an issue here where if the user doesn't exist, the events table will still decrement.
 * @author nkebbas
 *
 */
public class PurchaseEventHandler extends CS601Handler {

	public synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}

	public synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String[] parameters = request.getPathInfo().split("/");
		/* Get body information from Post Request */
		String getBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
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
        
		if (jsonBody.get("eventid") == null || jsonBody.get("userid") == null || jsonBody.get("tickets") == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
        
		/* Open connection to check if event, users exist */
		
		
		
		
		
		/* If so, continue */
        
		if (isNumeric(parameters[1])) {
			// int eventId = Integer.parseInt(parameters[1]);
			/* Get the event from the event DB, and decrement tickets */
			Database db = Database.getInstance();
			int userId = jsonBody.get("userid").getAsInt();
			int eventId = jsonBody.get("eventid").getAsInt();
			int tickets = jsonBody.get("tickets").getAsInt();
			
			/* Make sure tickets are available. This decrements first, so need to roll back. */
			boolean canDecrement = false;
			try {
				canDecrement = db.getDBManager().decrementTicketAvailability(eventId, tickets, "events");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			/* If you can decrement tickets, call the User API that handles adding of tickets */
			if (canDecrement) {
			
				/* Get the data passed from request and convert to bytes, then open a connection */
				byte[] postData = getBody.getBytes( StandardCharsets.UTF_8 );
				URL url = new URL(Constants.HOST + Constants.USERS_URL + "/tickets/add");
				HttpURLConnection connect = (HttpURLConnection) url.openConnection();
				connect = tryConnection(connect, postData);
		        connect.connect();  
		        
		        if (connect.getResponseCode() == 200) {
		        		response.setStatus(HttpServletResponse.SC_OK);
		        } else {
		        		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		        }
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
			
		}
	}
	
	private HttpURLConnection tryConnection(HttpURLConnection connect, byte[] postData) throws IOException {
		connect.setDoOutput( true );
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json");
		connect.setRequestProperty("charset", "utf-8");
		connect.setRequestProperty("Content-Length", Integer.toString( postData.length ));
		try( DataOutputStream wr = new DataOutputStream( connect.getOutputStream())) {
			wr.write(postData);
		}
		return connect;
	}

	public static boolean isNumeric(String s) {
		  try {  
		    int num = Integer.parseInt(s);
		  }	catch (NumberFormatException nfe) {  
		    return false;  
		  } return true;  
		}

}

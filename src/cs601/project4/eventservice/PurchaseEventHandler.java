package cs601.project4.eventservice;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.JsonObject;
import cs601.project4.database.Database;
import cs601.project4.server.CS601Handler;
import cs601.project4.server.Constants;
import cs601.project4.utility.ConnectionHelper;
import cs601.project4.utility.JsonManager;
import cs601.project4.utility.Numeric;

/**
 * Purchase ticket to event. This will check if tickets are available, 
 * decrement the available tickets number,
 * and then open a connection to the user service
 * 
 * @author nkebbas
 *
 */

public class PurchaseEventHandler extends CS601Handler {

	@Override
	public synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}

	@Override
	public synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String[] parameters = request.getPathInfo().split("/");
		/* Get body information from Post Request */
		String getBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		/* Check for Correct JSON Issues and No null necessary parameters */
		JsonObject jsonBody = JsonManager.validateJsonString(getBody);
		if (jsonBody == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
        
		if (jsonBody.get("eventid") == null || jsonBody.get("userid") == null || jsonBody.get("tickets") == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
        
		if (Numeric.isNumeric(parameters[1])) {
			int eventIdUrl = Integer.parseInt(parameters[1]);
			/* Get the event from the event DB, and decrement tickets */
			Database db = Database.getInstance();
			int userId = jsonBody.get("userid").getAsInt();
			int eventId = jsonBody.get("eventid").getAsInt();
			int tickets = jsonBody.get("tickets").getAsInt();
			
			/* If ID in URL is not the same as what is in body, return 400 */
			if (eventIdUrl != eventId) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
			
			/* Open a connection and check if user exists */
			URL userExistsUrl = new URL(Constants.USERS_HOST + Constants.USERS_URL + "/" + userId);
			HttpURLConnection userExistsConnect = (HttpURLConnection) userExistsUrl.openConnection();
			userExistsConnect = ConnectionHelper.tryGetConnection(userExistsConnect);
			userExistsConnect.connect();  
			
	        /* Response Code indicates user exists */
	        if (userExistsConnect.getResponseCode() != 200) {
	        		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        		return;
	        }
			
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
				URL url = new URL(Constants.USERS_HOST + Constants.USERS_URL + "/tickets/add");
				HttpURLConnection connect = (HttpURLConnection) url.openConnection();
				connect = ConnectionHelper.tryPostConnection(connect, postData);
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
}

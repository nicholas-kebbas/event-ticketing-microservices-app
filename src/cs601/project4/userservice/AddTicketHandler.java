package cs601.project4.userservice;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.JsonObject;
import cs601.project4.database.Database;
import cs601.project4.server.CS601Handler;
import cs601.project4.server.Constants;
import cs601.project4.utility.JsonManager;
/**
 * Add a ticket. Called by Events server in PurchaseEventHandler after the event is confirmed to exist.
 * 
 * First, check the user exists, 
 * @author nkebbas
 *
 */
public class AddTicketHandler extends CS601Handler {

	public synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}

	/* Need to make sure user exists in DB here still. */
	public synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		boolean success = false;
		String getBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		/* Check for Correct JSON Issues and No null necessary parameters */
		JsonObject jsonBody = JsonManager.validateJsonString(getBody);
		if (jsonBody == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		if (jsonBody.get("userid") == null || jsonBody.get("eventid") == null || jsonBody.get("tickets") == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		int userId = jsonBody.get("userid").getAsInt();
		int eventId = jsonBody.get("eventid").getAsInt();
		int tickets = jsonBody.get("tickets").getAsInt();
		
		/* Confirm Event Exists */
       	URL eventUrl = new URL (Constants.HOST + Constants.EVENTS_URL + "/" + eventId);
        HttpURLConnection eventConnect = (HttpURLConnection) eventUrl.openConnection();
		eventConnect = tryGetConnection(eventConnect);
		
		if (eventConnect.getResponseCode() != 200) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		/* Confirm User Exists */
       	URL url = new URL (Constants.HOST + Constants.USERS_URL + "/" + userId);
        HttpURLConnection userConnect = (HttpURLConnection) url.openConnection();
		userConnect = tryGetConnection(userConnect);
		
		if (userConnect.getResponseCode() != 200) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		/* Make a transaction in the ticket table of the database */
		Database db = Database.getInstance();
		try {
			for (int i = 0; i < tickets; i++) {
				success = db.getDBManager().addTicket(userId, eventId, "users" ,"tickets");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (success) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		
	}
	
	private HttpURLConnection tryGetConnection(HttpURLConnection connect) throws IOException {
		connect.setDoOutput( true );
        connect.setRequestMethod("GET");
		connect.setRequestProperty("Content-Type", "application/json"); 
		connect.setRequestProperty("charset", "utf-8");
		return connect;
	}
	
}

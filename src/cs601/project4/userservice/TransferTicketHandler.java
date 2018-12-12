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
import cs601.project4.utility.ConnectionHelper;
import cs601.project4.utility.JsonManager;

/**
 * Connect to Transfer API, will change ticket owner.
 * @author nkebbas
 *
 */
public class TransferTicketHandler extends CS601Handler {

	public synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}

	public synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		/* Get userId of requester */
		String[] parameters = request.getPathInfo().split("/");
		int userId = Integer.parseInt(parameters[1]);
		/* Get body of request */
		String getBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		/* Check for Correct JSON Issues and No null necessary parameters */
		JsonObject jsonBody = JsonManager.validateJsonString(getBody);
		if (jsonBody == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		
		if (jsonBody.get("eventid") == null || jsonBody.get("targetuser") == null || jsonBody.get("tickets") == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		int eventId = jsonBody.get("eventid").getAsInt();
		int tickets = jsonBody.get("tickets").getAsInt();
		int targetUserId = jsonBody.get("targetuser").getAsInt();
		
		/* Confirm Event Exists */
       	URL eventUrl = new URL (Constants.HOST + Constants.EVENTS_URL + "/" + eventId);
        HttpURLConnection eventConnect = (HttpURLConnection) eventUrl.openConnection();
		eventConnect = ConnectionHelper.tryGetConnection(eventConnect);
		
		if (eventConnect.getResponseCode() != 200) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		/* Confirm Both Users Exist */
		/* Confirm User Exists */
//       	URL userUrl1 = new URL (Constants.HOST + Constants.USERS_URL + "/" + userId);
//        HttpURLConnection userConnect1 = (HttpURLConnection) userUrl1.openConnection();
//		userConnect1 = tryGetConnection(userConnect1);
//		
//		if (userConnect1.getResponseCode() != 200) {
//			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//			return;
//		}
		
		/* Confirm User Exists */
//       	URL userUrl2 = new URL (Constants.HOST + Constants.USERS_URL + "/" + targetUserId);
//        HttpURLConnection userConnect2 = (HttpURLConnection) userUrl2.openConnection();
//		userConnect2 = tryGetConnection(userConnect2);
//		
//		if (userConnect2.getResponseCode() != 200) {
//			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//			return;
//		}
		
		
		/* If so, continue */
		
		Database db = Database.getInstance();
		boolean didTransfer = false;
		try {
			didTransfer = db.getDBManager().transferTicket(userId, targetUserId, eventId, tickets, "tickets");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		/* If successful */
		if (didTransfer) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

}

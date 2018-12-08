package cs601.project4.userservice;

import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import cs601.project4.database.Database;
import cs601.project4.server.CS601Handler;

/**
 * Hit Transfer API, will change ticket owner.
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
		
		
		if (jsonBody.get("eventid") == null || jsonBody.get("targetuser") == null || jsonBody.get("tickets") == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		int eventId = jsonBody.get("eventid").getAsInt();
		int tickets = jsonBody.get("tickets").getAsInt();
		int targetUserId = jsonBody.get("targetuser").getAsInt();
		
		/* Check if event, users exist */
		
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

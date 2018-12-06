package cs601.project4.userservice;

import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
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
		System.out.println("transfer ticket get");
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}

	public synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		System.out.println("transfer ticket");
		
		/* Get userId of requester */
		String[] parameters = request.getPathInfo().split("/");
		int userId = Integer.parseInt(parameters[1]);

		/* Get body of request */
		String getBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		JsonParser parser = new JsonParser();
		JsonObject jsonBody = (JsonObject) parser.parse(getBody);
		
		int eventId = jsonBody.get("eventid").getAsInt();
		int tickets = jsonBody.get("tickets").getAsInt();
		int targetUserId = jsonBody.get("targetuser").getAsInt();
		
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

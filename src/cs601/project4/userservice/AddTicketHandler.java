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
 * Add a ticket. Called by Events server.
 * @author nkebbas
 *
 */
public class AddTicketHandler extends CS601Handler {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		
	}

	@Override
	/* Need to make sure user exists in db here still. */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String getBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		JsonParser parser = new JsonParser();
		JsonObject jsonBody = (JsonObject) parser.parse(getBody);
		
		int userId = jsonBody.get("userid").getAsInt();
		int eventId = jsonBody.get("eventid").getAsInt();
		int tickets = jsonBody.get("tickets").getAsInt();
		
		/* Make a transaction in the ticket table of the database */
		
		Database db = Database.getInstance();
		try {
			for (int i = 0; i < tickets; i++) {
				db.getDBManager().addTicket(userId, eventId, "tickets");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}

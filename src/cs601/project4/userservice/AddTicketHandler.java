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
 * Add a ticket. Called by Events server.
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
		
		if (jsonBody.get("userid") == null || jsonBody.get("eventid") == null || jsonBody.get("tickets") == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		int userId = jsonBody.get("userid").getAsInt();
		int eventId = jsonBody.get("eventid").getAsInt();
		int tickets = jsonBody.get("tickets").getAsInt();
		
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
	
}

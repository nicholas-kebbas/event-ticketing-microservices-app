package cs601.project4.eventservice;

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
 * Update number of available tickets after ticket is purchased.
 * This will be contacted by servlet in user when ticket is purchased.
 * Get the eventId and the number of tickets from the user handler.
 * @author nkebbas
 *
 */
public class UpdateTicketAvailabilityHandler extends CS601Handler {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("do get available tickets");
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("do post available tickets");
		
		/* Now we read the request body */
		String getBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		System.out.println("Body: " + request.getReader().readLine());
		JsonParser parser = new JsonParser();
		JsonObject jsonBody = (JsonObject) parser.parse(getBody);
		int eventId = jsonBody.get("eventid").getAsInt();
		int tickets = jsonBody.get("tickets").getAsInt();
		
		/* Now we update the DB with the change in tickets */
		Database db = Database.getInstance();
		boolean canDecrement = false;
		try {
			canDecrement = db.getDBManager().decrementTicketAvailability(eventId, tickets, "events");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (canDecrement) {
		/* Send the response back. If there are enough available tickets, send a 200, else send a 400. */
			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().print("Tickets updated");
		} else {
			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

}

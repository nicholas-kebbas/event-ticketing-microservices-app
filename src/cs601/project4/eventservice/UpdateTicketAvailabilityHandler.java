package cs601.project4.eventservice;

import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.JsonObject;
import cs601.project4.database.Database;
import cs601.project4.server.CS601Handler;
import cs601.project4.utility.JsonManager;

/**
 * Update number of available tickets after ticket is purchased.
 * This is contacted by Handler in User Server when ticket is purchased.
 * Get the eventId and the number of tickets from the user handler.
 * @author nkebbas
 *
 */

public class UpdateTicketAvailabilityHandler extends CS601Handler {

	@Override
	public synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}

	@Override
	public synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		/* Now we read the request body */
		String getBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

		/* Check for Correct JSON Issues and No null necessary parameters */
		JsonObject jsonBody = JsonManager.validateJsonString(getBody);
		if (jsonBody == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
        
        if (jsonBody.get("eventid") == null || jsonBody.get("tickets") == null) {
	        	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        	return;
        }
        
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

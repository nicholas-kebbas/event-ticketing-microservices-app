package cs601.project4.eventservice;

import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cs601.project4.database.Database;
import cs601.project4.database.Event;

/**
 *  Handler to Create an Event POST /event. Responds with JSON Body
 *  Needs fields in incoming post body: userid, eventname, numtickets 
 * @author nkebbas
 *
 */
public class CreateEventHandler extends HttpServlet {
	public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException {	
		/* Build the object based on json request */
		String getBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		JsonParser parser = new JsonParser();
        JsonObject jsonBody = (JsonObject) parser.parse(getBody);
        int userId = jsonBody.get("userid").getAsInt();
        String eventName = jsonBody.get("eventname").getAsString(); 
        int numTickets = jsonBody.get("numtickets").getAsInt();
        /* Now save this info to database, and pass back the ID of the new event */
        Event event = new Event(userId, eventName, numTickets);
        Database db = Database.getInstance();
        String intString = "";
        try {
			int id = db.getDBManager().createEvent(event, "events");
			intString = Integer.toString(id);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
        
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print("{" + "id: " + intString  +"}");
	}
}

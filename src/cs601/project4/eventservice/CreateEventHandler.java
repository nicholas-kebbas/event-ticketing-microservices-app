package cs601.project4.eventservice;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import cs601.project4.database.Database;
import cs601.project4.database.Event;
import cs601.project4.server.Constants;

/**
 *  Handler to Create an Event POST /event. Responds with JSON Body
 *  Needs fields in incoming post body: userid, eventname, numtickets 
 * @author nkebbas
 *
 */
public class CreateEventHandler extends HttpServlet {
	
	public synchronized void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException {	
		/* Build the object based on json request */
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
        
        if (jsonBody.get("userid") == null || jsonBody.get("eventname") ==  null || jsonBody.get("numtickets") == null) {
	    		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	    		return;
        }
        int userId = jsonBody.get("userid").getAsInt();
        String eventName = jsonBody.get("eventname").getAsString(); 
        int numTickets = jsonBody.get("numtickets").getAsInt();
        
        /* After checking for correct JSON, check users server to confirm user exists */
       	URL url = new URL (Constants.HOST + Constants.USERS_URL + "/" + userId);
        HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect = tryGetConnection(connect);
		if (connect.getResponseCode() != 200) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
        
        /* Now save this info to database, and pass back the ID of the new event */
        Event event = new Event(userId, eventName, numTickets);
        Database db = Database.getInstance();
        String intString = "";
        int id;
        try {
			id = db.getDBManager().createEvent(event, "events");
			if (id == -1) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
			intString = Integer.toString(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print("{" + "\"eventid\": " + intString  +"}");
	}
	
	private HttpURLConnection tryGetConnection(HttpURLConnection connect) throws IOException {
		connect.setDoOutput( true );
        connect.setRequestMethod("GET");
		connect.setRequestProperty("Content-Type", "application/json"); 
		connect.setRequestProperty("charset", "utf-8");
		return connect;
	}
	
}

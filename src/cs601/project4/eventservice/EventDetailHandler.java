package cs601.project4.eventservice;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs601.project4.database.Database;
import cs601.project4.database.Event;
import cs601.project4.server.CS601Handler;

public class EventDetailHandler extends CS601Handler {
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}
	
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException {
		String[] parameters = request.getPathInfo().split("/");
			if (parameters.length == 2 && isNumeric(parameters[1])) {
				int id = Integer.parseInt(parameters[1]);
				Database db = Database.getInstance();
				Event event;
				try {
					event = db.getDBManager().getEvent(id, "events");
					if (event == null) {
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						return;
					}
					response.setStatus(HttpServletResponse.SC_OK);
					response.getWriter().print("{" +
							"\"eventid\": " + event.getEventId() + ", " +
							"\"eventname\": \"" + event.getEventName() + "\", " + 
							"\"userid\": " + event.getUserId() +  ", " +
							"\"avail\": " + event.getAvailableTickets() +  ", " +
							"\"purchased\": " + event.getPurchasedTickets() +
							"}");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
	}
	
	public static boolean isNumeric(String s) {
	  try {  
	    int num = Integer.parseInt(s);
	  }	catch (NumberFormatException nfe) {  
	    return false;  
	  } return true;  
	}
	
}

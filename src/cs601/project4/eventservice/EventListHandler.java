package cs601.project4.eventservice;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs601.project4.database.Database;
import cs601.project4.database.Event;

public class EventListHandler extends HttpServlet {
	
	public synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Database db = Database.getInstance();
		ArrayList<Event> eventsList = new ArrayList<Event>();
		try {
			eventsList = db.getDBManager().getEventList("events");
			response.getWriter().print("[");
			for (int i = 0; i < eventsList.size(); i++) {
				if (i != 0) {
					response.getWriter().print(",");
				}
				response.getWriter().print(eventsList.get(i).toJsonFormattedString());
			}
			response.getWriter().print("]");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

package cs601.project4.eventservice;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs601.project4.database.Database;
import cs601.project4.database.Event;

public class EventListHandler extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		Database db = Database.getInstance();
		ArrayList<Event> eventsList = new ArrayList<Event>();
		try {
			eventsList = db.getDBManager().getEventList("events");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

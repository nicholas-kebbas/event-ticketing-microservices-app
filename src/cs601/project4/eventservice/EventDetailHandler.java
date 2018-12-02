package cs601.project4.eventservice;

import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs601.project4.database.Database;
import cs601.project4.database.Event;

public class EventDetailHandler extends HttpServlet {
	public void doGet (HttpServletRequest request, HttpServletResponse response) {
		String[] parameters = request.getPathInfo().split("/");
			if (parameters.length == 2 && isNumeric(parameters[1])) {
				int id = Integer.parseInt(parameters[1]);
				Database db = Database.getInstance();
				try {
					Event event = db.getDBManager().getEvent(id, "events");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println(id);
			} else {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
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

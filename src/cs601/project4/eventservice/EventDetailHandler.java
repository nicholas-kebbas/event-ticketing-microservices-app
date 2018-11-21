package cs601.project4.eventservice;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EventDetailHandler extends HttpServlet {
	public void doGet (HttpServletRequest request, HttpServletResponse response) {
		System.out.println("event detail");
	}
}

package cs601.project4.eventservice;

import cs601.project4.database.Database;
import cs601.project4.server.Constants;
import cs601.project4.server.JettyServer;

public class EventServer {
	
	public static void main(String[] args) {
		JettyServer server = new JettyServer(Constants.EVENTS_URL);
		server.addServlet(CreateEventHandler.class, "/create");
		server.addServlet(EventListHandler.class, "/list");
		server.addServlet(PurchaseEventHandler.class, "/purchase/*");
		server.addServlet(EventDetailHandler.class, "/*");
		/* Accessed internally when a ticket is purchased */
		server.addServlet(UpdateTicketAvailabilityHandler.class, "/tickets/availability");
		try {
			server.start();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	    /* Create a connection to the database. */
	    Database db = Database.getInstance();
	    db.connect();
	}
}

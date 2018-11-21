package cs601.project4.eventservice;

import cs601.project4.database.Database;
import cs601.project4.server.JettyServer;

public class EventServer {
	
	public static void main(String[] args) {
		JettyServer server = new JettyServer(5000);
		server.addServlet(CreateEventHandler.class, "/create");
		server.addServlet(EventListHandler.class, "/list");
		server.addServlet(EventDetailHandler.class, "/*");
		server.addServlet(PurchaseEventHandler.class, "/purchase/*");
		try {
			server.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    System.out.println("server started");
	    /* Create a connection to the database. 
	     * Could also move this into separate (singleton) class and 
	     * perform this logic there.  */
	    Database db = Database.getInstance();
	    db.connect();
	}
}

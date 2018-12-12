package cs601.project4.frontend;

import cs601.project4.database.Database;
import cs601.project4.server.Constants;
import cs601.project4.server.JettyServer;

public class FrontendServer {
	
	public static void main(String[] args) {
		JettyServer server = new JettyServer(Constants.FRONTEND_URL);
		server.addServlet(FrontendEventListHandler.class, "/events");
		server.addServlet(FrontendCreateEventHandler.class, "/events/create");
		server.addServlet(FrontendEventsRoutingHandler.class, "/events/*");
		server.addServlet(FrontendCreateUserHandler.class, "/users/create");
		server.addServlet(FrontendUsersRoutingHandler.class, "/users/*");
		server.addServlet(FrontendUsersRoutingHandler.class, "/*");
		
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

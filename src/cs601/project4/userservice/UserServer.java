package cs601.project4.userservice;

import cs601.project4.database.Database;
import cs601.project4.server.*;
import cs601.project4.server.JettyServer;

public class UserServer {
	
	public static void main(String[] args) {
		JettyServer userServer = new JettyServer(Constants.USERS_URL);
		userServer.addServlet(CreateUserHandler.class, "/create");
		userServer.addServlet(AddTicketHandler.class, "/tickets/add");
		userServer.addServlet(UserTicketRoutingHandler.class, "/*");
		
		try {
			userServer.start();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	    /* Create a connection to the database. */
	    Database db = Database.getInstance();
	    db.connect();
	}
}

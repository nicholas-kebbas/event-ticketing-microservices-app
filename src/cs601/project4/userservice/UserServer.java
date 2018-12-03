package cs601.project4.userservice;

import cs601.project4.database.Database;
import cs601.project4.server.*;
import cs601.project4.server.JettyServer;

public class UserServer {
	
	public static void main(String[] args) {
		JettyServer userServer = new JettyServer(Constants.USERS_URL);
		userServer.addServlet(CreateUserHandler.class, "/create");
		/* Need to do additional parsing in the singler /* Handler 
		 * So the below 3 should all be directed to one routing handler,
		 * and then the correct handler will be called in that servlet
		 * */
		userServer.addServlet(UserTicketRoutingHandler.class, "/*");
		
		try {
			userServer.start();
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

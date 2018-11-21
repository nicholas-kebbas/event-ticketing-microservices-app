package cs601.project4.frontend;

import cs601.project4.database.Database;
import cs601.project4.server.JettyServer;
import cs601.project4.userservice.CreateTicketHandler;
import cs601.project4.userservice.CreateUserHandler;
import cs601.project4.userservice.TransferTicketHandler;
import cs601.project4.userservice.UserDetailHandler;

public class FrontendServer {
	
	public static void main(String[] args) {
		JettyServer server = new JettyServer(7070);
		server.addServlet(CreateUserHandler.class, "/create");
		server.addServlet(CreateTicketHandler.class, "/*/tickets/add");
		server.addServlet(UserDetailHandler.class, "/*");
		server.addServlet(TransferTicketHandler.class, "/*/tickets/transfer/");
		
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

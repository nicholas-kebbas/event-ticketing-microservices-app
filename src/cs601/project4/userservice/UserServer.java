package cs601.project4.userservice;

import cs601.project4.database.Database;
import cs601.project4.server.JettyServer;

public class UserServer {
	
	public static void main(String[] args) {
		JettyServer server = new JettyServer(7000);
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

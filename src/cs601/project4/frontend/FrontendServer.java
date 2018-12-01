package cs601.project4.frontend;

import cs601.project4.database.Database;
import cs601.project4.server.Constants;
import cs601.project4.server.JettyServer;

public class FrontendServer {
	
	public static void main(String[] args) {
		JettyServer server = new JettyServer(Constants.FRONTEND_URL);
		server.addServlet(FrontendEventListHandler.class, "/events");
		server.addServlet(FrontendCreateEventHandler.class, "/events/create");
		
		// This will need to parse /events/*/purchase/transfer/* as well 
		server.addServlet(FrontendEventDetailHandler.class, "/events/*");
		// server.addServlet(FrontendPurchaseEventTicketHandler.class, "/events/*/purchase/transfer/*");
		server.addServlet(FrontendCreateUserHandler.class, "/users/create");
		
		// This will need to parse /users/*/tickets/transfer as well
		server.addServlet(FrontendUserDetailHandler.class, "/users/*");
		// server.addServlet(FrontendTransferTicketHandler.class, "/users/*/tickets/transfer");
		
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

package cs601.project4.server;

import cs601.project4.database.Database;

/**
 * Start Jetty Server and Create instance of Database
 * 
 * @author nkebbas
 */
public class StartServer {

	public static void main(String[] args){
	    JettyServer jettyServer = new JettyServer();
	    try {
			jettyServer.start();
		} catch (Exception e) {
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
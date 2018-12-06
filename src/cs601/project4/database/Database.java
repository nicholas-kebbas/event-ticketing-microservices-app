package cs601.project4.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import cs601.project4.database.DBConstants;

/**
 * Generic Database class that utilizes the singleton pattern to access 
 * the database instance.
 * @author nkebbas
 *
 */
public final class Database {
	private static Database INSTANCE;
	private DBManager dbm;
	private Connection con;

	public Database() {
		dbm = new DBManager();
	}
	
	/* TODO: Make threadsafe without using synchronized */
	public synchronized static Database getInstance() {
		
		if(INSTANCE == null) {
            INSTANCE = new Database();
        }
		
        return INSTANCE;
	}
	
	public void connect() {
		try {
			// load driver
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		}
		catch (Exception e) {
			System.err.println("Can't find driver");
			System.exit(1);
		}
		// format "jdbc:mysql://[hostname][:port]/[dbname]"
		//note: if connecting through an ssh tunnel make sure to use 127.0.0.1 and
		//also to that the ports are set up correctly
		String urlString = "jdbc:mysql://127.0.0.1:3306/"+DBConstants.DB;
		//Must set time zone explicitly in newer versions of mySQL.
		String timeZoneSettings = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";


		try {
			con = DriverManager.getConnection(urlString+timeZoneSettings,
					DBConstants.USERNAME,
					DBConstants.PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.dbm.setCon(con);
	    System.out.println("DB connection established");
	}
	
	public DBManager getDBManager() {
		return this.dbm;
	}
	
	public Connection getConnection() {
		return this.con;
	}
}

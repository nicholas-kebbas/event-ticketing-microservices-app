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
	
	public synchronized static Database getInstance() {
		
		if(INSTANCE == null) {
            INSTANCE = new Database();
        }
		
        return INSTANCE;
	}
	
	public void connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		}
		catch (Exception e) {
			System.err.println("Can't find driver");
			System.exit(1);
		}
		// format "jdbc:mysql://[hostname][:port]/[dbname]"
		String urlString = DBConstants.URL_STRING+DBConstants.DB;
		//Must set time zone explicitly in newer versions of mySQL.
		String timeZoneSettings = DBConstants.TIME_ZONE_SETTINGS;

		try {
			con = DriverManager.getConnection(urlString+timeZoneSettings,
					DBConstants.USERNAME,
					DBConstants.PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.dbm.setCon(con);
	}
	
	public DBManager getDBManager() {
		return this.dbm;
	}
	
	public Connection getConnection() {
		return this.con;
	}
}

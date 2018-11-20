package cs601.project4.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import cs601.project4.database.User;



/**
 * 
 * Must ensure isolation.
 * 	- Easy (inefficient) way is to make all methods synchronized
 *	- Alternatively, Transactions can be interleaved because they're 
 *	not touching any of the same data.
 * @author nkebbas
 *
 */
public class DBManager {
	Connection con;

	public User getUser(int id) {
		return null;	
	}
	
	/**
	 * Create account and give password.
	 * Generate a salt. Hash password and store it.
	 * When you log in, give user and password. 
	 * Get username, get salt out of row, add it to username,
	 * then hash that. If they're the same, allow access. If not, deny.
	 * Implement something like https://www.baeldung.com/java-password-hashing
	 * 
	 * @return
	 * @throws SQLException 
	 */
	public void createUser(User user, String tableName) throws SQLException {
		PreparedStatement updateStmt = con.prepareStatement("INSERT INTO " + tableName + " (name, slack_id, access_token) VALUES (?, ?, ?)");
		updateStmt.setString(1, user.getName());
		updateStmt.setString(2, user.getId());
		updateStmt.setString(3, user.getAccessToken());
		updateStmt.execute();	
	}
	
	public void createEvent(Event event, String tableName) throws SQLException {
		PreparedStatement updateStmt = con.prepareStatement("INSERT INTO " + tableName + " (value, operation, user_id, description) VALUES (?, ?, ?, ?)");
		updateStmt.execute();	
	}
	
	public Event getEvent() {
		return null;
	}
	
/**
 * Take as input username and password, return User object data
 * @param username
 * @param password
 * @return
 */
	public User authenticate(String username, String password) {
		return null;
	}
	
	/* Sign user in, don't create user. Then we can get all the transactions */
	public boolean authenticate(String accessToken) throws SQLException {
		String selectStmt = "SELECT * FROM spusers";
		PreparedStatement stmt = con.prepareStatement(selectStmt);
		ResultSet result = stmt.executeQuery();
		while (result.next()) {
			String dbToken= result.getString("access_token");
			if (accessToken.equals(dbToken) ) {
				return true;
			}
		}
		return false;
	}
	
	public boolean signIn(String userId) throws SQLException {
		String selectStmt = "SELECT * FROM spusers";
		PreparedStatement stmt = con.prepareStatement(selectStmt);
		ResultSet result = stmt.executeQuery();
		while (result.next()) {
			String dbUserId= result.getString("slack_id");
			if (userId.equals(dbUserId) ) {
				return true;
			}
		}
		return false;
	}
	
	public String getAllUserTransactions(String userId) throws SQLException {
		System.out.println ("User id:" + userId); 
		String output ="";
		String selectStmt = "SELECT * FROM transactions"; 
		PreparedStatement stmt = con.prepareStatement(selectStmt);
		ResultSet result = stmt.executeQuery();
		while (result.next()) {
			String id = result.getString("user_id");
			if (userId.equals(id)) {
				String operation = result.getString("operation");
				String value = result.getString("value");
				String description = result.getString("description");
			
				if (operation.contains("charge") || operation.contains("buy")) {
					output += "<p>Charge $";
				} else if (operation.contains("credit") || operation.contains("sell")) {
					output += "Credit $";
				}
				output+= value;
				output+= " for " + description; 
				output += "</p>";
			}
		}
		if (output.equals("")) {
			output += "<p>No transactions recorded. Please record some in slack.</p>";
		}
		return output;
	}
	
	
	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}
}

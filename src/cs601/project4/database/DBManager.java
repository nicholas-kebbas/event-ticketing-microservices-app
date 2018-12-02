package cs601.project4.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	public int createUser(User user, String tableName) throws SQLException {
		PreparedStatement printStmt = con.prepareStatement("SELECT * FROM " + tableName);
		PreparedStatement updateStmt = con.prepareStatement("INSERT INTO " + tableName + " (name) VALUES (?)");
		updateStmt.setString(1, user.getName());
		/* Create the new item */
		updateStmt.execute();
		/* Then get the id of the new user to return */
		ResultSet rs = printStmt.executeQuery();
		int idres = 0;
		while (rs.next()) {
			//for each result, get the value of the columns name and id
			idres = rs.getInt("id");
		}
		System.out.println("id" + idres);
		return idres;
	}
	
	public int createEvent(Event event, String tableName) throws SQLException {
		String createSql = "INSERT INTO " + tableName + " (userid, eventname, numtickets) VALUES (?, ?, ?)";
		PreparedStatement updateStmt = con.prepareStatement(createSql, Statement.RETURN_GENERATED_KEYS);
		updateStmt.setInt(1, event.getUserId());
		updateStmt.setString(2, event.getEventName());
		updateStmt.setInt(3, event.getNumTickets());

		/* Create the new item */
		updateStmt.executeUpdate();
		/* Get the latest id */
		ResultSet rs = updateStmt.getGeneratedKeys();
		int idres = 0;
		while (rs.next()) {
			//for each result, get the value of the columns name and id
			idres = rs.getInt("name");
		}
		System.out.println("id" + idres);
		return idres;
	}
	
	public void addTicket(int userId, int eventId, String tableName) throws SQLException {
		PreparedStatement updateStmt;
		updateStmt = con.prepareStatement("INSERT INTO " + tableName + " (user_id, event_id) VALUES (?, ?)");
		updateStmt.setInt(1, userId);
		updateStmt.setInt(2, eventId);
		/* Create the new item */
		updateStmt.execute();
	}
	
	public void decrementTicketAvailability(int eventId, String tableName) throws SQLException {
		PreparedStatement updateStmt;
		updateStmt = con.prepareStatement("UPDATE " + tableName + "SET tickets = tickets -1 WHERE event_id=" + eventId + "and tickets > 0");
		updateStmt.execute();
	}
	
	/* Get ID from request.getPathInfo() in handler */
	public Event getEvent(int id, String tableName) throws SQLException {
		Event returnEvent = new Event();
		PreparedStatement updateStmt;
		updateStmt = con.prepareStatement("SELECT id FROM " + tableName + "WHERE id=" + id);
		return returnEvent;
	}
	
	public User getUser(int id, String tableName) throws SQLException {
		User returnUser = new User();
		PreparedStatement updateStmt;
		updateStmt = con.prepareStatement("SELECT id FROM " + tableName + "WHERE id=" + id);
		return returnUser;
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
		String selectStmt = "SELECT * FROM users";
		PreparedStatement stmt = con.prepareStatement(selectStmt);
		ResultSet result = stmt.executeQuery();
		while (result.next()) {
			String dbUserId= result.getString("id");
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

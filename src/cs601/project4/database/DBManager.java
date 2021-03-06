package cs601.project4.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Object that provides the database with methods to interact with database
 * @author nkebbas
 *
 */
public class DBManager {
	private Connection con;
	
	public synchronized int createUser(User user, String tableName) throws SQLException {
		PreparedStatement printStmt = this.con.prepareStatement("SELECT * FROM " + tableName);
		PreparedStatement updateStmt = this.con.prepareStatement("INSERT INTO " + tableName + " (name) VALUES (?)");
		updateStmt.setString(1, user.getName());
		/* Create the new item */
		updateStmt.execute();
		/* Then get the id of the new user to return */
		ResultSet rs = printStmt.executeQuery();
		int idres = 0;
		while (rs.next()) {
			idres = rs.getInt("id");
		}
		return idres;
	}
	
	public synchronized int createEvent(Event event, String tableName) throws SQLException {		
		/* Create the new item */
		String createSql = "INSERT INTO " + tableName + " (userid, eventname, available_tickets, total_tickets) VALUES (?, ?, ?, ?)";
		PreparedStatement updateStmt = this.con.prepareStatement(createSql, Statement.RETURN_GENERATED_KEYS);
		updateStmt.setInt(1, event.getUserId());
		updateStmt.setString(2, event.getEventName());
		updateStmt.setInt(3, event.getAvailableTickets());
		updateStmt.setInt(4, event.getAvailableTickets());
		updateStmt.execute();
		/* Get the latest id */
		PreparedStatement getAllStmt = this.con.prepareStatement("SELECT * FROM " + tableName);
		ResultSet rs = getAllStmt.executeQuery();
		int idres = 0;
		while (rs.next()) {
			idres = rs.getInt("id");
		}
		return idres;
	}
	
	/**
	 * Add Ticket to table
	 * @param userId
	 * @param eventId
	 * @param tableName
	 * @throws SQLException
	 */
	public synchronized boolean addTicket(int userId, int eventId, String usersTableName, String ticketsTableName) throws SQLException {
		PreparedStatement updateStmt;
		
		if (!checkIdExists(userId, "users")) {
			return false;
		}
		
		updateStmt = this.con.prepareStatement("INSERT INTO " + ticketsTableName + " (user_id, event_id) VALUES (?, ?)");
		updateStmt.setInt(1, userId);
		updateStmt.setInt(2, eventId);
		/* Create the new item */
		updateStmt.execute();
		return true;
	}
	
	/**
	 * Update table with new userId. Should make ticket an object as well.
	 * @param userId
	 * @param targetUserId
	 * @param eventId
	 * @param tickets
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public synchronized boolean transferTicket(int userId, int targetUserId, int eventId, int tickets, String tableName) throws SQLException {
		PreparedStatement checkStmt;
		
		if (!checkIdExists(userId, "users")) {
			return false;
		}
		
		if (!checkIdExists(targetUserId, "users")) {
			return false;
		}
		
		checkStmt = this.con.prepareStatement("SELECT user_id FROM " + tableName + " WHERE user_id= " + userId + " AND event_id= " + eventId);
		ResultSet rs = checkStmt.executeQuery();
		
		int checkCount = 0;
		while(rs.next()) {
			checkCount++;
		}
		
		PreparedStatement updateStmt;
		if (checkCount >= tickets) {
			updateStmt = this.con.prepareStatement("UPDATE " + tableName + " SET user_id = " + targetUserId + " WHERE user_id= " + userId + " AND event_id= " + eventId + " LIMIT " + tickets);
			updateStmt.execute();
			return true;
		}
		return false;
	}
	
	/**
	 * Decrement ticket availability by number of tickets
	 * @param eventId
	 * @param numTickets
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	
	public synchronized boolean decrementTicketAvailability(int eventId, int numTickets, String tableName) throws SQLException {
		PreparedStatement checkStmt;
		PreparedStatement updateStmt;
		checkStmt = this.con.prepareStatement("SELECT available_tickets FROM " + tableName + " WHERE  id= " + eventId);
		updateStmt = this.con.prepareStatement("UPDATE " + tableName + " SET available_tickets = available_tickets - " + numTickets + " WHERE id=" + eventId);
		ResultSet results = checkStmt.executeQuery();
	
		int dbTickets = 0;
		while (results.next()) {
			dbTickets = results.getInt(1);
		}
		/* If there are enough tickets */
		if (dbTickets >= numTickets) {
			updateStmt.execute();
			return true;
		}
		
		/* If not enough tickets */
		return false;
	}
	
	/**
	 * Get Event
	 * @param id
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	
	public synchronized Event getEvent(int id, String tableName) throws SQLException {
		Event returnEvent = new Event();
		PreparedStatement eventStmt;
		eventStmt = this.con.prepareStatement("SELECT * FROM " + tableName + " WHERE id=" + id);
		ResultSet eventResultSet = eventStmt.executeQuery();
		
		eventResultSet.beforeFirst();
		if (!eventResultSet.next()) {
			return null;
		}
		
		eventResultSet.beforeFirst();
		while(eventResultSet.next()) {
			returnEvent.setEventId(eventResultSet.getInt(1));
			returnEvent.setEventName(eventResultSet.getString(2));
			returnEvent.setUserId(eventResultSet.getInt(3));
			returnEvent.setAvailableTickets(eventResultSet.getInt(4));
			returnEvent.setTotalTickets(eventResultSet.getInt(5));
		}
		return returnEvent;
	}
	
	public synchronized ArrayList<Event> getEventList(String tableName) throws SQLException {
		ArrayList<Event> outputList = new ArrayList<Event>(); 
		String stmt = "SELECT * FROM " + tableName;
		PreparedStatement eventStmt;
		eventStmt = this.con.prepareStatement(stmt);
		ResultSet eventResultSet = eventStmt.executeQuery();
		while (eventResultSet.next()) {
			Event event = new Event();
			event.setEventId(eventResultSet.getInt(1));
			event.setEventName(eventResultSet.getString(2));
			event.setUserId(eventResultSet.getInt(3));
			event.setAvailableTickets(eventResultSet.getInt(4));
			event.setTotalTickets(eventResultSet.getInt(5));
			outputList.add(event);
		}
		if (outputList.isEmpty()) {
			return null;
		}
		return outputList;
	}
	
	public synchronized User getUser(int id, String tableName) throws SQLException {
		User returnUser = new User();
		PreparedStatement userStmt;
		userStmt = this.con.prepareStatement("SELECT * FROM " + tableName + " WHERE id=" + id);
		ResultSet userRs = userStmt.executeQuery();
		
		/* Check for no reults */
		userRs.beforeFirst();
		if (!userRs.next()) {
			return null;
		}
		
		userRs.beforeFirst();
		while(userRs.next()) {
			String userName = userRs.getString(2);
			returnUser.setName(userName);
		}

		PreparedStatement ticketStmt;
		ticketStmt = this.con.prepareStatement("SELECT tickets.id, tickets.event_id FROM tickets JOIN users"
				+ " ON users.id=tickets.user_id WHERE tickets.user_id=" + id);
		ResultSet  ticketRs = ticketStmt.executeQuery();
		
		/* Add the ticket Id to the list of the user's list of tickets */
		while (ticketRs.next()) {
			returnUser.addTicketId(ticketRs.getInt(1));
			returnUser.addTicketEventId(ticketRs.getInt(2));
		}
		
		return returnUser;
	}
	
	private synchronized boolean checkIdExists(int id, String tableName) throws SQLException {
		PreparedStatement checkStmt;
		checkStmt = this.con.prepareStatement("SELECT * FROM " + tableName + " WHERE id=" + id);
		ResultSet resultSet = checkStmt.executeQuery();
		resultSet.beforeFirst();
		if (!resultSet.next()) {
			return false;
		}
		return true;
	}
	
	
	public Connection getCon() {
		return this.con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}
}

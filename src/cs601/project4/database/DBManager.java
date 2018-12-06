package cs601.project4.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



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
			// for each result, get the value of the columns name and id
			idres = rs.getInt("id");
		}
		System.out.println("id " + idres);
		return idres;
	}
	
	public int createEvent(Event event, String tableName) throws SQLException {
		PreparedStatement printStmt = con.prepareStatement("SELECT * FROM " + tableName);
		String createSql = "INSERT INTO " + tableName + " (userid, eventname, available_tickets, total_tickets) VALUES (?, ?, ?, ?)";
		PreparedStatement updateStmt = con.prepareStatement(createSql, Statement.RETURN_GENERATED_KEYS);
		updateStmt.setInt(1, event.getUserId());
		updateStmt.setString(2, event.getEventName());
		updateStmt.setInt(3, event.getAvailableTickets());
		updateStmt.setInt(4, event.getAvailableTickets());
		/* Create the new item */
		updateStmt.execute();
		/* Get the latest id */
		ResultSet rs = printStmt.executeQuery();
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
	public void addTicket(int userId, int eventId, String tableName) throws SQLException {
		PreparedStatement updateStmt;
		updateStmt = con.prepareStatement("INSERT INTO " + tableName + " (user_id, event_id) VALUES (?, ?)");
		updateStmt.setInt(1, userId);
		updateStmt.setInt(2, eventId);
		/* Create the new item */
		updateStmt.execute();
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
	public boolean transferTicket(int userId, int targetUserId, int eventId, int tickets, String tableName) throws SQLException {
		PreparedStatement checkStmt;
		checkStmt = con.prepareStatement("SELECT user_id FROM " + tableName + " WHERE user_id= " + userId + " AND event_id= " + eventId);
		ResultSet rs = checkStmt.executeQuery();
		
		int checkCount = 0;
		while(rs.next()) {
			checkCount++;
		}
		
		PreparedStatement updateStmt;
		if (checkCount >= tickets) {
			updateStmt = con.prepareStatement("UPDATE " + tableName + " SET user_id = " + targetUserId + " WHERE user_id= " + userId + " AND event_id= " + eventId + " LIMIT " + tickets);
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
	public boolean decrementTicketAvailability(int eventId, int numTickets, String tableName) throws SQLException {
		PreparedStatement checkStmt;
		PreparedStatement updateStmt;
		checkStmt = con.prepareStatement("SELECT available_tickets FROM " + tableName + " WHERE  id= " + eventId);
		updateStmt = con.prepareStatement("UPDATE " + tableName + " SET available_tickets = available_tickets - " + numTickets + " WHERE id=" + eventId);
		System.out.println(checkStmt);
		System.out.println(checkStmt.getResultSet());
		boolean canDecrement = false;
		ResultSet results = checkStmt.executeQuery();
		
		/* Have to figure out how to check if there are not enough available tickets */
		/* Can also use isBeforeFirst() on ResultSet. */
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
	
	/* TODO: Return null if Event does not exist. */
	public Event getEvent(int id, String tableName) throws SQLException {
		Event returnEvent = new Event();
		PreparedStatement eventStmt;
		eventStmt = con.prepareStatement("SELECT * FROM " + tableName + " WHERE id=" + id);
		ResultSet eventResultSet = eventStmt.executeQuery();
		while(eventResultSet.next()) {
			returnEvent.setEventId(eventResultSet.getInt(1));
			returnEvent.setEventName(eventResultSet.getString(2));
			returnEvent.setUserId(eventResultSet.getInt(3));
			returnEvent.setAvailableTickets(eventResultSet.getInt(4));
			returnEvent.setTotalTickets(eventResultSet.getInt(5));
		}
		return returnEvent;
	}
	
	public ArrayList<Event> getEventList(String tableName) throws SQLException {
		ArrayList<Event> outputList = new ArrayList<Event>(); 
		String stmt = "SELECT * FROM " + tableName;
		PreparedStatement eventStmt;
		eventStmt = con.prepareStatement(stmt);
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
		
		return outputList;
	}
	
	/* TODO: Return null if User does not exist. */
	public User getUser(int id, String tableName) throws SQLException {
		User returnUser = new User();
		PreparedStatement userStmt;
		userStmt = con.prepareStatement("SELECT * FROM " + tableName + " WHERE id=" + id);
		ResultSet userRs = userStmt.executeQuery();
		while(userRs.next()) {
			String userName = userRs.getString(2);
			returnUser.setName(userName);
		}
		PreparedStatement ticketStmt;
		ticketStmt = con.prepareStatement("SELECT tickets.id, tickets.event_id FROM tickets JOIN users"
				+ " ON users.id=tickets.user_id WHERE tickets.user_id=" + id);
		ResultSet  ticketRs = ticketStmt.executeQuery();
		
		/* Add the ticket Id to the list of the user's list of tickets */
		while (ticketRs.next()) {
			returnUser.addTicketId(ticketRs.getInt(1));
			returnUser.addTicketEventId(ticketRs.getInt(2));
		}
		
		return returnUser;
	}
	
	
	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}
}

package cs601.project4.database;

import java.util.ArrayList;

/**
 * Creates User Object, has name, id, and arraylists that hold IDs of tickets and EventIDs of tickets.
 * @author nkebbas
 *
 */

public class User {
	private String id;
	private String name;
	private ArrayList<Integer> ticketsIds;
	private ArrayList<Integer> ticketsEventIds;

	public User() {
		this.ticketsIds = new ArrayList<Integer>();
		this.setTicketsEventIds(new ArrayList<Integer>());
	}
	
	public User(String name) {
		this.name = name;
		this.ticketsIds = new ArrayList<Integer>();
	}
	
	public User(String id, String name) {
		this.id = id;
		this.name = name;
		this.ticketsIds = new ArrayList<Integer>();
		this.setTicketsEventIds(new ArrayList<Integer>());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addTicketId(int ticketId) {
		this.ticketsIds.add(ticketId);
	}
	
	public void addTicketEventId(int ticketId) {
		this.ticketsEventIds.add(ticketId);
	}
	
	public ArrayList<Integer> getTicketIds() {
		return ticketsIds;
	}

	public void setTicketIds(ArrayList<Integer> tickets) {
		this.ticketsIds = tickets;
	}
	
	public String toString() {
		String toString = "";
		toString = this.name + " " + this.id;
		return toString;
	}

	public ArrayList<Integer> getTicketsEventIds() {
		return ticketsEventIds;
	}

	public void setTicketsEventIds(ArrayList<Integer> ticketsEventIds) {
		this.ticketsEventIds = ticketsEventIds;
	}
}

package cs601.project4.database;

public class Event {
	private int userId;
	private String eventName;
	private int numTickets;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public int getNumTickets() {
		return numTickets;
	}
	public void setNumTickets(int numTickets) {
		this.numTickets = numTickets;
	}
}

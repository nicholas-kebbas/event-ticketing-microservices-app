package cs601.project4.database;

public class Event {
	private int userId;
	private int eventId;
	private String eventName;
	private int availableTickets;
	private int totalTickets;

	public Event() {
		
	}
	
	public Event (int userId, String eventName, int numTickets) {
		this.userId = userId;
		this.eventName = eventName;
		this.availableTickets = numTickets;
		this.totalTickets = numTickets;
	}

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
	
	public int getAvailableTickets() {
		return availableTickets;
	}

	public void setAvailableTickets(int availableTickets) {
		this.availableTickets = availableTickets;
	}

	public int getTotalTickets() {
		return totalTickets;
	}

	public void setTotalTickets(int totalTickets) {
		this.totalTickets = totalTickets;
	}
	
	public int getPurchasedTickets() {
		return this.totalTickets - this.availableTickets;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	
	public String toJsonFormattedString() {
		String jsonString = "";
		jsonString += "{" +
				"\"eventid\":" + this.eventId + "," +
				"\"eventname\":" + "\"" +this.eventName+ "\"" + "," +
				"\"userid\":" + this.userId + "," +
				"\"avail\":" + this.availableTickets + "," +
				"\"purchased\":" + this.getPurchasedTickets() +
			"}";
		return jsonString;
	}
}

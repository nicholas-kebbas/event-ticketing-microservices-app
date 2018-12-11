package cs601.project4.database;

public class Ticket {
	private int id;
	private int userid;
	private int eventid;
	
	public Ticket() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getEventid() {
		return eventid;
	}

	public void setEventid(int eventid) {
		this.eventid = eventid;
	}
}

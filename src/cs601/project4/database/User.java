package cs601.project4.database;

import java.util.ArrayList;

public class User {
	private String id;
	private String name;
	private ArrayList<String> tickets;
	
	public User() {
		tickets = new ArrayList<String>();
	}
	
	public User(String name) {
		this.name = name;
		tickets = new ArrayList<String>();
	}
	
	public User(String id, String name) {
		this.id = id;
		this.name = name;
		tickets = new ArrayList<String>();
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
	
	public String toString() {
		String toString = "";
		toString = this.name + " " + this.id;
		return toString;
	}
}

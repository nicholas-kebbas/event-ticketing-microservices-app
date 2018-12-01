package cs601.project4.database;

public class User {
	private String id;
	private String name;
	
	public User() {
		
	}
	
	public User(String name) {
		this.name = name;
	}
	
	public User(String id, String name) {
		this.id = id;
		this.name = name;
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

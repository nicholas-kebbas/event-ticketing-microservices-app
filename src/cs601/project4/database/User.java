package cs601.project4.database;

public class User {
	private String id;
	private String name;
	private String accessToken;
	
	public User() {
		
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

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public String toString() {
		String toString = "";
		toString = this.name + " " + this.id;
		return toString;
	}
}

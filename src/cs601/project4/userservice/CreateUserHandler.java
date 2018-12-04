package cs601.project4.userservice;

import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cs601.project4.database.Database;
import cs601.project4.database.User;
import cs601.project4.server.CS601Handler;

/**
 * Need to figure out how to split appropriately when there's a comma in the json request
 * @author nkebbas
 *
 */
public class CreateUserHandler extends CS601Handler {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("create handler posted");
		String getBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		JsonParser parser = new JsonParser();
		JsonObject jBody = (JsonObject) parser.parse(getBody);
		String userName = jBody.get("username").getAsString();
		/* Now save this info to database, and pass back the ID of the new event */
		User user = new User(userName);
		Database db = Database.getInstance();
		String intString = "";
		try {
			int id = db.getDBManager().createUser(user, "users");
			intString = Integer.toString(id);
			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().print("{" + "\"userid\": " + intString  +"}");
		} catch (SQLException e) {
			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			e.printStackTrace();
		}
	    System.out.println(response.getStatus());
	}
}

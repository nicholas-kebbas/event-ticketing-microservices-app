package cs601.project4.userservice;

import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
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
	
	public synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}
	
	public synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("create handler posted");
		String getBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		JsonParser parser = new JsonParser();
		JsonObject jsonBody = new JsonObject();
		try {
			parser.parse(getBody);
		} catch (JsonParseException j) {
	    		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	    		return;
		}
		if (parser.parse(getBody).isJsonNull()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		jsonBody = (JsonObject) parser.parse(getBody);

		if (jsonBody.get("username") == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		String userName = jsonBody.get("username").getAsString();
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

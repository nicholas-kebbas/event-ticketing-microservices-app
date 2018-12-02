package cs601.project4.userservice;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs601.project4.database.Database;
import cs601.project4.database.User;
import cs601.project4.server.CS601Handler;

public class UserDetailHandler extends CS601Handler {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String[] parameters = request.getPathInfo().split("/");
		if (parameters.length == 2 && isNumeric(parameters[1])) {
			int id = Integer.parseInt(parameters[1]);
			Database db = Database.getInstance();
			try {
				User user = db.getDBManager().getUser(id, "users");
				response.setContentType("application/json");
				response.setStatus(HttpServletResponse.SC_ACCEPTED);
				/* Not correct */
				response.getWriter().print("{userid\": " + id  +"}");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println(id);
		} else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}
	
	public static boolean isNumeric(String s) {
	  try {  
	    int num = Integer.parseInt(s);
	  }	catch (NumberFormatException nfe) {  
	    return false;  
	  } return true;  
	}

}

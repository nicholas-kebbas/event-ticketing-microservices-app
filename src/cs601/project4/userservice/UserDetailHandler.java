package cs601.project4.userservice;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cs601.project4.database.Database;
import cs601.project4.database.User;
import cs601.project4.server.CS601Handler;
import cs601.project4.utility.Numeric;

/**
 * Retrieve the users information from the database.Return their userId, username, and an array of the eventIds of
 * their tickets as a JSON object.
 * @author nkebbas
 *
 */

public class UserDetailHandler extends CS601Handler {

	public synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String[] parameters = request.getPathInfo().split("/");
		if (parameters.length == 2 && Numeric.isNumeric(parameters[1])) {
			int id = Integer.parseInt(parameters[1]);
			Database db = Database.getInstance();
			try {
				User user = db.getDBManager().getUser(id, "users");
				if (user == null) {
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					return;
				}
				response.setContentType("application/json");
				response.setStatus(HttpServletResponse.SC_OK);
				
				response.getWriter().print("{"
						+ " \"userid\": " + id  +", "
						+ " \"username\": " + "\"" + user.getName() + "\"" + ", " 
						+ " \"tickets\": [ ");
				for (int i = 0; i < user.getTicketIds().size(); i++) {
					if (i != 0) {
						response.getWriter().print(",");
					}
					response.getWriter().print("{ \"eventid\": " + user.getTicketsEventIds().get(i) + "}");
				}
				response.getWriter().print(" ] }" );
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	public synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}

}

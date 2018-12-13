package cs601.project4.userservice;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cs601.project4.server.CS601Handler;
import cs601.project4.utility.Numeric;

/**
 * Route between various handlers (Create or Transfer Ticket)
 * author: nkebbas
 * */

public class UserTicketRoutingHandler extends CS601Handler {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		CS601Handler handler = parseString(request.getPathInfo());
		if (handler != null) {
			handler.doGet(request, response);
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		CS601Handler handler = parseString(request.getPathInfo());
		if (handler != null) {
			handler.doPost(request, response);
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	
	public CS601Handler parseString(String requestUrl) {
		String[] parameters = requestUrl.split("/");
		if (parameters.length == 2) {
			/* Work around chrome pulling in favicon */
			if (parameters[1].equals("favicon.ico")) {
				return null;
			}
			if (parameters[1].equals("create")) {
				CreateUserHandler createUserHandler = new CreateUserHandler();
				return createUserHandler;
			} else if(Numeric.isNumeric(parameters[1])){
				UserDetailHandler userDetailHandler = new UserDetailHandler();
				return userDetailHandler;
			}
		} else if (parameters.length == 4) {
			if (parameters[3].equals("add")) {
				CreateTicketHandler createTicketHandler = new CreateTicketHandler();
				return createTicketHandler;
			} else if (parameters[3].equals("transfer")) {
				TransferTicketHandler transferTicketHandler = new TransferTicketHandler();
				return transferTicketHandler;
			}
		}
		return null;
	}
}

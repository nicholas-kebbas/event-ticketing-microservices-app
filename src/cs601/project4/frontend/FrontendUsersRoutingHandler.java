package cs601.project4.frontend;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs601.project4.server.CS601Handler;
import cs601.project4.userservice.CreateTicketHandler;
import cs601.project4.userservice.CreateUserHandler;
import cs601.project4.userservice.TransferTicketHandler;
import cs601.project4.userservice.UserDetailHandler;
/**
 * Parse /users//tickets/transfer and /users/
 * 
 * @author nkebbas
 *
 */
public class FrontendUsersRoutingHandler extends CS601Handler {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		CS601Handler handler = parseString(request.getPathInfo());
		if (handler != null) {
			handler.doGet(request, response);
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		CS601Handler handler = parseString(request.getPathInfo());
		if (handler != null) {
			handler.doPost(request, response);
		}
		
	}
	
	public CS601Handler parseString(String requestUrl) {
		String[] parameters = requestUrl.split("/");
		System.out.println(parameters.length);
		if (parameters.length == 2) {
			if (isNumeric(parameters[1])) {
				FrontendUserDetailHandler frontendUserDetailHandler = new FrontendUserDetailHandler();
				return frontendUserDetailHandler;
			}
		} else if (parameters.length == 5) {
			if (isNumeric(parameters[2]) && parameters[4].equals("transfer")) {
				FrontendTransferTicketHandler frontendTransferTicketHandler = new FrontendTransferTicketHandler();
				return frontendTransferTicketHandler;
			}
		}
		return null;
	}

	public static boolean isNumeric(String s) {
		  try {  
		    int num = Integer.parseInt(s);
		  }	catch (NumberFormatException nfe) {  
		    return false;  
		  } return true;  
		}

}

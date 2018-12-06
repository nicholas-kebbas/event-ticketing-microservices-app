package cs601.project4.frontend;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs601.project4.server.CS601Handler;
/**
 * Routes /users/ URLs to appropriate handler
 * Parse /users//tickets/transfer and /users/
 * 
 * @author nkebbas
 *
 */
public class FrontendUsersRoutingHandler extends CS601Handler {

	public synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		CS601Handler handler = parseUrl(request.getPathInfo());
		if (handler != null) {
			handler.doGet(request, response);
		}
		
	}

	public synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		CS601Handler handler = parseUrl(request.getPathInfo());
		if (handler != null) {
			handler.doPost(request, response);
		}
		
	}
	
	public CS601Handler parseUrl(String requestUrl) {
		String[] parameters = requestUrl.split("/");
		System.out.println(parameters.length);
		if (parameters.length == 2) {
			if (isNumeric(parameters[1])) {
				FrontendUserDetailHandler frontendUserDetailHandler = new FrontendUserDetailHandler();
				return frontendUserDetailHandler;
			}
		} else if (parameters.length == 4) {
			if (isNumeric(parameters[1]) && parameters[3].equals("transfer")) {
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

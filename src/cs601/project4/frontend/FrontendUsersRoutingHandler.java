package cs601.project4.frontend;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cs601.project4.server.CS601Handler;
import cs601.project4.utility.Numeric;

/**
 * Routes /users/ URLs to appropriate handler
 * Parses /users/{id}/tickets/transfer and /users/{id}
 * 
 * @author nkebbas
 *
 */

public class FrontendUsersRoutingHandler extends CS601Handler {

	public synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		CS601Handler handler = parseUrl(request.getPathInfo());
		if (handler != null) {
			handler.doGet(request, response);
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		
	}

	public synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		CS601Handler handler = parseUrl(request.getPathInfo());
		if (handler != null) {
			handler.doPost(request, response);
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		
	}
	
	public CS601Handler parseUrl(String requestUrl) {
		String[] parameters = requestUrl.split("/");
		if (parameters.length == 2) {
			if (Numeric.isNumeric(parameters[1])) {
				FrontendUserDetailHandler frontendUserDetailHandler = new FrontendUserDetailHandler();
				return frontendUserDetailHandler;
			}
		} else if (parameters.length == 4) {
			if (Numeric.isNumeric(parameters[1]) && parameters[3].equals("transfer")) {
				FrontendTransferTicketHandler frontendTransferTicketHandler = new FrontendTransferTicketHandler();
				return frontendTransferTicketHandler;
			}
		}
		return null;
	}

}

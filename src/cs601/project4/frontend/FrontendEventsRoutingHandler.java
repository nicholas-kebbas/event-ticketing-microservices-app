package cs601.project4.frontend;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cs601.project4.server.CS601Handler;
import cs601.project4.utility.Numeric;

/**
 * Route requests to appropriate URLs
 * @author nkebbas
 *
 */

public class FrontendEventsRoutingHandler extends CS601Handler {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		CS601Handler handler = parseUrl(request.getPathInfo());
		if (handler != null) {
			handler.doGet(request, response);
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		CS601Handler handler = parseUrl(request.getPathInfo());
		if (handler != null) {
			handler.doPost(request, response);
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	
	private CS601Handler parseUrl(String requestUrl) {
		String[] parameters = requestUrl.split("/");
		if (parameters.length == 2) {
			if (Numeric.isNumeric(parameters[1])) {
				FrontendEventDetailHandler frontendEventDetailHandler = new FrontendEventDetailHandler();
				return frontendEventDetailHandler;
			}
		} else if (parameters.length == 4) {
			if (Numeric.isNumeric(parameters[1]) && Numeric.isNumeric(parameters[3])) {
				FrontendPurchaseEventTicketHandler frontendPurchaseEventTicketHandler = new FrontendPurchaseEventTicketHandler();
				return frontendPurchaseEventTicketHandler;
			}
		}
		return null;
	}

}

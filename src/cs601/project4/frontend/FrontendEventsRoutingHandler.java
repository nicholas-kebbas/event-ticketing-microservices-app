package cs601.project4.frontend;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs601.project4.server.CS601Handler;

public class FrontendEventsRoutingHandler extends CS601Handler {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
	}
	
	private CS601Handler parseUrl(String requestUrl) {
		String[] parameters = requestUrl.split("/");
		System.out.println(parameters.length);
		if (parameters.length == 2) {
			if (isNumeric(parameters[1])) {
				FrontendEventDetailHandler frontendEventDetailHandler = new FrontendEventDetailHandler();
				return frontendEventDetailHandler;
			}
		} else if (parameters.length == 5) {
			if (isNumeric(parameters[2]) && parameters[4].equals("purchase")) {
				FrontendPurchaseEventTicketHandler frontendPurchaseEventTicketHandler = new FrontendPurchaseEventTicketHandler();
				return frontendPurchaseEventTicketHandler;
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

package cs601.project4.userservice;

import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cs601.project4.database.Database;
import cs601.project4.database.Event;
import cs601.project4.server.CS601Handler;

public class UserTicketRoutingHandler extends CS601Handler {
	
	
	public synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		CS601Handler handler = parseString(request.getPathInfo());
		if (handler != null) {
			handler.doGet(request, response);
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		
	}
	
	public synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		CS601Handler handler = parseString(request.getPathInfo());
		if (handler != null) {
			handler.doPost(request, response);
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	
	public static boolean isNumeric(String s) {  
		  try {  
		    int num = Integer.parseInt(s);
		  }	catch (NumberFormatException nfe) {  
		    return false;  
		  } return true;  
	}
	
	// Route to appropriate Servlet. For users, this is deciding between
	// (CreateTicketHandler.class, "*/tickets/add");
	// (UserTicketRoutingHandler.class, "/*");
	// (TransferTicketHandler.class, "/*/tickets/transfer/");
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
			} else if(isNumeric(parameters[1])){
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

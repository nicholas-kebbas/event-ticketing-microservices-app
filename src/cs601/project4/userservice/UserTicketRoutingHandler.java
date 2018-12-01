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
//		String getBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
//		System.out.println("Body: " + request.getReader().readLine());
//		System.out.println(request == null);
//		JsonParser parser = new JsonParser();
//        JsonObject jsonBody = (JsonObject) parser.parse(getBody);
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
	//
	public CS601Handler parseString(String requestUrl) {
		String[] parameters = requestUrl.split("/");
		System.out.println(parameters.length);
		if (parameters.length == 2) {
			/* Work around chrome pulling in favicon */
			if (parameters[1].equals("favicon.ico")) {
				return null;
			}
			if (parameters[1].equals("create")) {
				System.out.println("create handler");
				CreateUserHandler createUserHandler = new CreateUserHandler();
				return createUserHandler;
			}
			UserDetailHandler userDetailHandler = new UserDetailHandler();
			return userDetailHandler;
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

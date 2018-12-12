package cs601.project4.frontend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import cs601.project4.server.CS601Handler;
import cs601.project4.server.Constants;
import cs601.project4.utility.ConnectionHelper;
import cs601.project4.utility.JsonManager;

/**
 * External facing API to return user information based on User ID.
 * 
 * Supports GET request.
 * @author nkebbas
 *
 */
public class FrontendUserDetailHandler extends CS601Handler {

	@Override
	public synchronized void doGet(HttpServletRequest request, HttpServletResponse response) {
		String[] parameters = request.getPathInfo().split("/");
		try {
	       	URL urlUsers = new URL (Constants.HOST + Constants.USERS_URL + "/" + parameters[1]);
	        HttpURLConnection connectUsers = (HttpURLConnection) urlUsers.openConnection();
	        connectUsers = ConnectionHelper.tryGetConnection(connectUsers);
			/* Get response from User server */
			BufferedReader in = new BufferedReader(new InputStreamReader(connectUsers.getInputStream()));
			String inputLine;
			StringBuffer responseString = new StringBuffer();
		    while ((inputLine = in.readLine()) != null) {
		    		responseString.append(inputLine);
		    	}
		    in.close();
		    /* So we got all the tickets, now we need to get information for each event tied to ticket */
		    /* So turn the response string back into a json Object */
		    JsonObject jsonBody = JsonManager.validateJsonString(responseString.toString());
		    
		    /* Get all the ticket IDs */
		    JsonArray ticketsArray = jsonBody.get("tickets").getAsJsonArray();
		    ArrayList<Integer> eventIds = new ArrayList<Integer>();
		    for (JsonElement ticketElement: ticketsArray) {
		    		JsonObject ticket = (JsonObject) ticketElement;
		    		int eventId = ticket.get("eventid").getAsInt();
		    		eventIds.add(eventId);
		    }
		    StringBuffer finalResponseString = new StringBuffer();
		    finalResponseString.append("{ \"userid\": " + jsonBody.get("userid").getAsInt() + "," +   
		    "\"username\": " + "\"" + jsonBody.get("username").getAsString() + "\"" + "," +   "\"tickets\": [");
		    /* Call events server now */
		    for (int i = 0; i < eventIds.size(); i++) {
				if (i != 0) {
					finalResponseString.append(",");
				}
		       	URL urlEvents = new URL (Constants.HOST + Constants.EVENTS_URL + "/" +eventIds.get(i));
		        HttpURLConnection connectEvents = (HttpURLConnection) urlEvents.openConnection();
		        connectEvents = ConnectionHelper.tryGetConnection(connectEvents);
				/* Get response from User server */
				BufferedReader inEvents = new BufferedReader(new InputStreamReader(connectEvents.getInputStream()));
				String inputLineEvents;
			    while ((inputLineEvents = inEvents.readLine()) != null) {
			    		finalResponseString.append(inputLineEvents);
			    	}
			    inEvents.close();
		    }
		    finalResponseString.append("]}");
		    response.getWriter().print(finalResponseString.toString());
		} catch (IOException e) {
			System.out.println(e.getMessage());
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	@Override
	public synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}

}

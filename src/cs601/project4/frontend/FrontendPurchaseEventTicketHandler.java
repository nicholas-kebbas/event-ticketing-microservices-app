package cs601.project4.frontend;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import cs601.project4.server.CS601Handler;
import cs601.project4.server.Constants;
import cs601.project4.utility.ConnectionHelper;
import cs601.project4.utility.JsonManager;

/**
 * There's an issue here where if the user doesn't exist, the events table will still decrement.
 * 
 * @author nkebbas
 *
 */
public class FrontendPurchaseEventTicketHandler extends CS601Handler {

	public synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}

	/* Need to edit the post request to include userId and eventId */
	public synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String getBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		/* Check for Correct JSON Issues and No null necessary parameters */
		JsonObject jsonBody = JsonManager.validateJsonString(getBody);
		if (jsonBody == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		if (jsonBody.get("tickets") == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		int tickets = jsonBody.get("tickets").getAsInt();
		
		String[] parameters = request.getPathInfo().split("/");
		int eventId = Integer.parseInt(parameters[1]);
		int userId = Integer.parseInt(parameters[3]);
		String newBody = "{" + "\"userid\":" + userId + "," + "\"eventid\":" + eventId + "," + "\"tickets\":" + tickets + "}";
		byte[] newBodyBytes = newBody.getBytes(StandardCharsets.UTF_8);
		URL url = new URL(Constants.HOST + Constants.EVENTS_URL + "/purchase/" + eventId);
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect = ConnectionHelper.tryPostConnection(connect, newBodyBytes);
		if (connect.getResponseCode() == 200) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
        connect.connect();  
	}

}

package cs601.project4.frontend;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs601.project4.server.CS601Handler;
import cs601.project4.server.Constants;

public class FrontendPurchaseEventTicketHandler extends CS601Handler {

	public synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}

	/* Need to edit the post request to include userId and eventId */
	public synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String getBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		
		/* Open connection to Events Server and send over */
		byte[] postData = getBody.getBytes(StandardCharsets.UTF_8);
		
		String[] parameters = request.getPathInfo().split("/");
		int eventId = Integer.parseInt(parameters[2]);
		int userId = Integer.parseInt(parameters[4]);
		URL url = new URL(Constants.HOST + Constants.EVENTS_URL + "/purchase/" + eventId);
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		connect.setRequestProperty("charset", "utf-8");
		connect.setRequestProperty("Content-Length", Integer.toString( postData.length));
		/* Then get response and write that */
		try(DataOutputStream wr = new DataOutputStream(connect.getOutputStream())) {
			/* Write user id */
			wr.writeChars("\"userid\":" + userId);
			/* Write event id */
			wr.writeChars("\"eventid\":" + eventId);
			wr.write(postData);
		}
        connect.connect();  
        System.out.println("Response: " + connect.getResponseCode());	
	}

}

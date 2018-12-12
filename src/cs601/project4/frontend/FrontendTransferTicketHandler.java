package cs601.project4.frontend;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cs601.project4.server.CS601Handler;
import cs601.project4.server.Constants;
import cs601.project4.utility.ConnectionHelper;

/** 
 * External facing API that takes eventid, tickets, targetuser as input as
 * json object.
 * 
 * Supports POST requests. Contacts /users/{userid}/tickets/transfer internal API.
 * @author nkebbas
 *
 */
public class FrontendTransferTicketHandler extends CS601Handler {

	public synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}

	public synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String getBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		
		/* Open connection to Events Server and send over */
		byte[] postData = getBody.getBytes( StandardCharsets.UTF_8 );
		
		String[] parameters = request.getPathInfo().split("/");
		int userId = Integer.parseInt(parameters[1]);
		URL url = new URL(Constants.HOST + Constants.USERS_URL + "/" + userId + "/tickets/transfer");
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect = ConnectionHelper.tryPostConnection(connect, postData);
      
        if (connect.getResponseCode() == 200) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
        
        connect.connect();  
	}

}

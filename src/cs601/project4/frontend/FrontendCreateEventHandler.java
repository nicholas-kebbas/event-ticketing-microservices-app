package cs601.project4.frontend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
 * Create an Event via the external facing API. This will hit the create event API on the event server. 
 * Takes as input the userId of the event author, the event name, and the number of tickets available for sale.
 * @author nkebbas
 *
 */
public class FrontendCreateEventHandler extends CS601Handler {

	public synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}

	public synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String getBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		
		/* Open connection to Events Server and send over */
		byte[] postData = getBody.getBytes( StandardCharsets.UTF_8 );
		URL url = new URL(Constants.HOST + Constants.EVENTS_URL + "/create");
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect = ConnectionHelper.tryPostConnection(connect, postData);
		connect.connect();  
        
        /* Write response to frontend body */
        if (connect.getResponseCode() == 200) {
			/* Write to frontend response */
			BufferedReader input = new BufferedReader(new InputStreamReader(connect.getInputStream()));
			String inputLine;
			StringBuffer responseString = new StringBuffer();
			while ((inputLine = input.readLine()) != null) {
				responseString.append(inputLine);
			}
			input.close();
			response.getWriter().print(responseString.toString());
	        connect.connect();
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
     }
}

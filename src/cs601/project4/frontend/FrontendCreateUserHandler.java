package cs601.project4.frontend;

import java.io.BufferedReader;
import java.io.DataOutputStream;
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

public class FrontendCreateUserHandler extends CS601Handler {

	public synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}

	public synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String getBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		
		/* Open connection to Events Server and send over */
		byte[] postData = getBody.getBytes( StandardCharsets.UTF_8 );
		URL url = new URL(Constants.HOST + Constants.USERS_URL + "/create");
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json");
		connect.setRequestProperty("charset", "utf-8");
		connect.setRequestProperty("Content-Length", Integer.toString( postData.length));
		/* Then get response and write that */
		try(DataOutputStream wr = new DataOutputStream(connect.getOutputStream())) {
			wr.write(postData);
		}
		
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

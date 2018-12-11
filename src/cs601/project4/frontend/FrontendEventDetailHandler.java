package cs601.project4.frontend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs601.project4.server.CS601Handler;
import cs601.project4.server.Constants;
import cs601.project4.utility.ConnectionHelper;

public class FrontendEventDetailHandler extends CS601Handler {

	@Override
	public synchronized void doGet(HttpServletRequest request, HttpServletResponse response) {
		String[] parameters = request.getPathInfo().split("/");
		try {
	       	URL url = new URL (Constants.HOST + Constants.EVENTS_URL + "/" + parameters[1]);
	        HttpURLConnection connect = (HttpURLConnection) url.openConnection();
			connect = ConnectionHelper.tryGetConnection(connect);
			/* Get response from User server */
			BufferedReader in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
			String inputLine;
			StringBuffer responseString = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				responseString.append(inputLine);
			}
			in.close();
			response.getWriter().print(responseString.toString());
		} catch (IOException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	@Override
	public synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}

}

package cs601.project4.frontend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs601.project4.server.CS601Handler;
import cs601.project4.server.Constants;

public class FrontendEventListHandler extends CS601Handler {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
       	URL url = new URL (Constants.HOST + Constants.EVENTS_URL + "/list");
        HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput( true );
		connect.setInstanceFollowRedirects( false );
        connect.setRequestMethod("GET");
		connect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		connect.setRequestProperty("charset", "utf-8");
		connect.setDoOutput(true);
		
		/* Get response from User server */
		BufferedReader in = new BufferedReader(
	             new InputStreamReader(connect.getInputStream()));
	     String inputLine;
	     StringBuffer responseString = new StringBuffer();
	     while ((inputLine = in.readLine()) != null) {
	     	responseString.append(inputLine);
	     }
	     in.close();
	     //print in String
	     response.setStatus(HttpServletResponse.SC_OK);
	     System.out.println(responseString.toString());
	     response.getWriter().print(responseString.toString());
		
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		
	}

}

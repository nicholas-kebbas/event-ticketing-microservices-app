package cs601.project4.frontend;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cs601.project4.server.CS601Handler;
import cs601.project4.server.Constants;

public class FrontendUserDetailHandler extends CS601Handler {

	@Override
	public synchronized void doGet(HttpServletRequest request, HttpServletResponse response) {
		String[] parameters = request.getPathInfo().split("/");
		try {
	       	URL url = new URL (Constants.HOST + Constants.USERS_URL + "/" + parameters[1]);
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
		     System.out.println(responseString.toString());
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

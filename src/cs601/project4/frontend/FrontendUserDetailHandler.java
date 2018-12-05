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
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String[] parameters = request.getPathInfo().split("/");
		int userId = Integer.parseInt(parameters[1]);
		System.out.println("ID " + userId);
       	URL url = new URL (Constants.HOST + Constants.USERS_URL + "/" + parameters[1]);
        HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput( true );
		connect.setInstanceFollowRedirects( false );
        connect.setRequestMethod("GET");
		connect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		connect.setRequestProperty("charset", "utf-8");
		connect.setDoOutput(true);
		int responseCode = connect.getResponseCode();
		System.out.println(responseCode);
		
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
		
		/* Now need to get response from the User Detail Handler, and write that to frontend response */
		
		
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}

}

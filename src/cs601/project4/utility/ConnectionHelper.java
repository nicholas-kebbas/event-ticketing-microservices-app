package cs601.project4.utility;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Helper class with static methods that take connections as input and make server requests
 * @author nkebbas
 *
 */
public class ConnectionHelper {

	public static HttpURLConnection tryGetConnection(HttpURLConnection connect) throws IOException {
		connect.setDoOutput( true );
        connect.setRequestMethod("GET");
		connect.setRequestProperty("Content-Type", "application/json"); 
		connect.setRequestProperty("charset", "utf-8");
		return connect;
	}
	
	public static HttpURLConnection tryPostConnection(HttpURLConnection connect, byte[] postData) throws IOException {
		connect.setDoOutput( true );
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json");
		connect.setRequestProperty("charset", "utf-8");
		connect.setRequestProperty("Content-Length", Integer.toString( postData.length ));
		try( DataOutputStream wr = new DataOutputStream( connect.getOutputStream())) {
			wr.write(postData);
		}
		return connect;
	}
	
	
}

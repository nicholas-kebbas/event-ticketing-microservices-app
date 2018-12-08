package cs601.project4.server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;



public class CS601ConnectionWrapper extends HttpURLConnection {
	private String method;
	
	public CS601ConnectionWrapper(URL url) {
		super(url);
		try {
			HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void disconnect() {
		
	}

	@Override
	public boolean usingProxy() {
		return false;
	}

	@Override
	public void connect() throws IOException {
		
	}
	
	public String getMethod() {
		return this.method;
	}
	
	public void setMethod(String method) {
		this.method = method;
	}

}

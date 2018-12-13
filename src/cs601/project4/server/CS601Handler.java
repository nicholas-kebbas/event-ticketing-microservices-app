package cs601.project4.server;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class CS601Handler extends HttpServlet {
	public abstract void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException;
	public abstract void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException;
}

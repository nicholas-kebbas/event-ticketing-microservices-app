package cs601.project4.server;
import javax.servlet.http.HttpServlet;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;


public class JettyServer {
    private Server server;
    private ServletHandler servletHandler;
    
    public JettyServer(int port) {
        int maxThreads = 10;
        int minThreads = 1;
        int idleTimeout = 120;
        QueuedThreadPool threadPool = new QueuedThreadPool(maxThreads, minThreads, idleTimeout);
        server = new Server(threadPool);
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        server.setConnectors(new Connector[] { connector });
		this.servletHandler = new ServletHandler();
        server.setHandler(servletHandler);
        
    }
 
    public void start() throws Exception {
        server.start();
    }
    
    public void addServlet(Class<? extends HttpServlet> servlet, String path) {
    		this.servletHandler.addServletWithMapping(servlet, path);
    }
}

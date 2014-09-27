package edu.iastate.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyServer {

	public static void main(String[] args) throws Exception {
		Server server = new Server(8081);
		
		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath("/");
		webapp.setWar("./target/cysquare-web-1.0.0-SNAPSHOT.war");
		server.setHandler(webapp);
		
		server.join();
		server.start();

	}

}

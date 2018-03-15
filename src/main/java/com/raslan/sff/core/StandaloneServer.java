package com.raslan.sff.core;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.raslan.sff.core.util.Logger;
import com.raslan.sff.web.HomePageServlet;

/**
 * Primary execution class
 *
 */
public class StandaloneServer 
{
    public static void main( String[] args ) 
    {
    	Logger logger = Logger.getInstance();
    	Server server = new Server(Config.SERVER_PORT);
    	
    	ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    	ServletHolder HomePageHolder = new ServletHolder("homePage",HomePageServlet.class);
    	
    	context.addServlet(HomePageHolder, "/");
    	
    	server.setHandler(context);

    	try{
    		logger.info("StandaloneServer", "Starting Server");
    		server.start();
    		server.join();
    	}catch(Exception ex){
    		logger.error("StandaloneServer", ex.toString());
    	}
    }
}

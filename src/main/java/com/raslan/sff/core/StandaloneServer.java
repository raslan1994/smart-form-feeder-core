package com.raslan.sff.core;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.MultipartConfigElement;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import com.raslan.sff.core.data.forms.FormLayoutManager;
import com.raslan.sff.core.util.Logger;
import com.raslan.sff.web.FormFeederService;
import com.raslan.sff.web.FormLayoutWebService;
import com.raslan.sff.web.HomePageServlet;
import com.raslan.sff.web.LoginServlet;
import com.raslan.sff.web.TesterServlet;

/**
 * Primary execution class
 *
 */
public class StandaloneServer 
{
	
    public static void main( String[] args ) 
    {
    	FormLayoutManager.getInstance().loadConfigs();;
    	Logger logger = Logger.getInstance();
    	Server server = new Server(Config.SERVER_PORT);
    	
    	ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    	ServletHolder homePageHolder = new ServletHolder("homePage",HomePageServlet.class);
    	ServletHolder formLayoutService = new ServletHolder("formLayoutService", FormLayoutWebService.class);
    	ServletHolder formFeederService = new ServletHolder("formFeederService",FormFeederService.class);
    	
    	ServletHolder loginPageHolder = new ServletHolder("loginPage",LoginServlet.class);
    	ServletHolder testPageHolder = new ServletHolder("testPage", TesterServlet.class);
    	
    	FilterHolder holder = new FilterHolder(new CrossOriginFilter());
    	holder.setInitParameter("allowedMethods", "GET,POST,HEAD,OPTIONS");
    	
    	//set multi-part handler
    	formFeederService.getRegistration().setMultipartConfig(new MultipartConfigElement("data/tmp"));
    	
    	context.addServlet(homePageHolder, "/");
    	context.addServlet(formFeederService, "/feed");
    	context.addServlet(formLayoutService, "/formLayouts");
    	context.addServlet(loginPageHolder, "/login");
    	context.addServlet(testPageHolder, "/test");
    	context.addFilter(holder, Config.CLIENT_CROSS_ORIGIN, EnumSet.of(DispatcherType.REQUEST));
    	
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

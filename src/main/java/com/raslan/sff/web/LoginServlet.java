package com.raslan.sff.web;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.raslan.sff.core.Config;

public class LoginServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		WebHelper.writeLocalHtmlHeader(resp);
		WebHelper.writeLoginUI(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		if(username == null || password == null || username.trim().equals("") || password.trim().equals("")){
			resp.sendRedirect("/login?ise=true");
			return;
		}
		
		boolean isPassed = true;
		
		if(isPassed){
			String token = UUID.randomUUID().toString();
			req.getSession().setAttribute("token", token);
			req.getSession().setAttribute("uid", "1");
			Cookie userAuthCookie = new Cookie("uauthtkn", token);
	        userAuthCookie.setPath("/");
	        
	        resp.addCookie(userAuthCookie);
			resp.sendRedirect(Config.CLIENT_HOST);
			return;
		}
		
		/*Cookie userAuthCookie = new Cookie("test", "Hello");
        userAuthCookie.setPath("/");
       
		resp.addCookie(userAuthCookie);
		resp.sendRedirect(Config.CLIENT_HOST+"/test.html");*/
	}
}

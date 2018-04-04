package com.raslan.sff.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.raslan.sff.core.Config;

public class LogoutServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//clear session
		req.getSession().setAttribute("token", null);
		req.getSession().setAttribute("id", null);
		
		//clear cookies
		for(Cookie c : req.getCookies()){
			if(c.getName().equals("uauthtkn")){
				c.setValue("");
			}else if(c.getName().equals("user")){
				c.setValue("");
			}
		}
		
		//redirect to home
		resp.sendRedirect(Config.CLIENT_HOST);
	}
}

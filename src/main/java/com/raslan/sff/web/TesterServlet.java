package com.raslan.sff.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TesterServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getCookies() != null){
			WebHelper.writeServiceHeader(resp);
			String out = "[";
			for(Cookie c : req.getCookies()){
				out += "\""+c.getValue() + "\",";
			}
			out+= "\"end\"]";
			
			resp.getWriter().print(out);
		}
	}
}

package com.raslan.sff.web;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomePageServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		WebHelper.writeHtmlHeader(resp);
		Writer out = resp.getWriter();
		out.write("<h3>Welcome :) </h3>");
		resp.flushBuffer();
	}
}

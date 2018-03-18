package com.raslan.sff.web;

import javax.servlet.http.HttpServletResponse;

public class WebHelper {
	public static void writeServiceHeader(HttpServletResponse resp){
		resp.setContentType("application/json");
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Request-Method", "GET");
	}
	
	public static void writeHtmlHeader(HttpServletResponse resp){
		resp.setContentType("text/html; charset=UTF-8");
		resp.setStatus(HttpServletResponse.SC_OK);
	}
}

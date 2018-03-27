package com.raslan.sff.web;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import com.raslan.sff.core.Config;

public class WebHelper {
	public static void writeRequiredHeadersForAuthentication(HttpServletResponse resp){
		resp.setHeader("Access-Control-Allow-Origin", Config.CLIENT_CROSS_ORIGIN);
		resp.setHeader("Access-Control-Allow-Headers", "origin, content-type");
		resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
	}
	
	public static void writeServiceHeader(HttpServletResponse resp){
		resp.setContentType("application/json");
		resp.setStatus(HttpServletResponse.SC_OK);
		
		writeRequiredHeadersForAuthentication(resp);
	}
	
	public static void writeHtmlHeader(HttpServletResponse resp){
		resp.setContentType("text/html; charset=UTF-8");
		resp.setStatus(HttpServletResponse.SC_OK);
	}
	
	public static void writeLocalHtmlHeader(HttpServletResponse resp){
		resp.setContentType("text/html; charset=UTF-8");
		resp.setStatus(HttpServletResponse.SC_OK);
	}
	
	public static void writeLoginUI(HttpServletResponse resp) throws IOException{
		Writer out = resp.getWriter();
		out.write("<link href='" + Config.CLIENT_HOST + "/src/css/bootstrap.css' rel='stylesheet'></link>");
		out.write("<div class='container' style='max-width: 500px'>");
		out.write("<form action='' method='post'>");
		
		out.write("<div class='form-group'>");
		out.write("<label>Username</label>");
		out.write("<input type='text' class='form-control' name='username'/>");
		out.write("</div>");
		
		out.write("<div class='form-group'>");
		out.write("<label>Password</label>");
		out.write("<input type='password' class='form-control' name='password'/>");
		out.write("</div>");
		
		out.write("<div class='form-group'>");
		out.write("<input type='submit' class='btn btn-sm btn-primary' name='submit' value='Login'/>");
		out.write("</div>");
		
		out.write("</form>");
		out.write("</div>");
	}
	
	public static void writeUnauthorizedAccessHeader(HttpServletResponse resp) throws IOException{
		resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		resp.getWriter().write("{\"status\":\"UNAUTHORIZED\"}");
		
		writeRequiredHeadersForAuthentication(resp);
	}
	
}

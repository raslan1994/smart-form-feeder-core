package com.raslan.sff.web;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
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
	
	private static void writeBootstrapLinks(Writer out) throws IOException{
		out.write("<link href='" + Config.CLIENT_HOST + "/src/css/bootstrap.css' rel='stylesheet'></link>");
	}
	
	private static void writeHeaderLogo(Writer out) throws IOException{
		out.write("<img src='" + Config.CLIENT_HOST + "/src/img/logo-small.png" +"' class='rounded bg-dark'"
				+ "style='padding: 6px; margin: 10px 0;'/>");
	}
	
	public static void writeWelcomeUI(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		Writer out = resp.getWriter();
		writeBootstrapLinks(out);
		
		out.write("<div class='container' style='max-width: 500px; margin: auto; padding-top: 50px; text-align: center;'>");
		out.write("<div class='card' style='width: 18rem; padding: 8px'>");
		out.write("<img class='card-img-top bg-dark rounded' src='" +Config.CLIENT_HOST + "/src/img/logo.png" + "' style='padding: 10px'>");
		out.write("<h5 class='card-title'>CORE SERVICES</h5>");
		out.write("<p class='card-text'>Smart form feeder <span class='badge badge-secondary'>Core</span> <span class='badge badge-success'>READY</span></p>");
		out.write("<p class='card-text' style='font-size: small'>DEVELOPER <strong>|</strong> M.R.M Raslan</p>");
		out.write("</div>");
		out.write("</div>");
	}
	
	public static void writeNewUserUI(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		/*
		 * 1 - required field missing
		 * 2 - invalid super administrator password
		 */
		String errorCode = req.getParameter("ec");
		Writer out = resp.getWriter();
		
		writeBootstrapLinks(out);
		
		out.write("<div class='container' style='max-width: 500px'>");
		
		writeHeaderLogo(out);
		
		if(errorCode != null && errorCode.equals("1")){
			out.write("<div class='alert alert-warning alert-dismissible fade show' role='alert'>"
					+ "<strong>All fields are required</strong> please input necessary data and try again."
					/*+ "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>"
					+ "<span aria-hidden='true'>&times;</span>"
					+ "</button>"*/
					+ "</div>");
		}else if(errorCode != null && errorCode.equals("2")){
			out.write("<div class='alert alert-warning alert-dismissible fade show' role='alert'>"
					+ "<strong>Invalid SA password</strong> Provided super administrator password is invalid please check and try again."
					/*+ "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>"
					+ "<span aria-hidden='true'>&times;</span>"
					+ "</button>"*/
					+ "</div>");
		}
		
		out.write("<form action='' method='post'>");
		
		out.write("<div class='form-group'>");
		out.write("<label>Super administrator Password</label>");
		out.write("<input type='password' class='form-control' name='sapass' required='required'/>");
		out.write("</div>");
		
		out.write("<div class='form-group'>");
		out.write("<label>First name</label>");
		out.write("<input type='text' class='form-control' name='fname' required='required'/>");
		out.write("</div>");
		
		out.write("<div class='form-group'>");
		out.write("<label>Last name</label>");
		out.write("<input type='text' class='form-control' name='lname' required='required'/>");
		out.write("</div>");
		
		out.write("<div class='form-group'>");
		out.write("<label>User name</label>");
		out.write("<input type='text' class='form-control' name='uname' required='required'/>");
		out.write("</div>");
		
		out.write("<div class='form-group'>");
		out.write("<label>Password</label>");
		out.write("<input type='password' class='form-control' name='pass' required='required'/>");
		out.write("</div>");
		
		out.write("<div class='form-group'>");
		out.write("<input type='submit' class='btn btn-warning' name='submit' value='Create'/>");
		out.write("</div>");
		
		out.write("</form>");
		out.write("</div>");
	}
	
	public static void writeLoginUI(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		String isErrorMessage = req.getParameter("ise");
		Writer out = resp.getWriter();
		
		writeBootstrapLinks(out);
		
		out.write("<div class='container' style='max-width: 500px'>");
		
		writeHeaderLogo(out);
		
		if(isErrorMessage != null){
			out.write("<div class='alert alert-warning alert-dismissible fade show' role='alert'>"
					+ "<strong>Invalid Credential</strong> Please check username, password and try again shortly."
					/*+ "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>"
					+ "<span aria-hidden='true'>&times;</span>"
					+ "</button>"*/
					+ "</div>");
		}
		
		out.write("<form action='' method='post'>");
		
		out.write("<div class='form-group'>");
		out.write("<label>Username</label>");
		out.write("<input type='text' class='form-control' name='username' required='required'/>");
		out.write("</div>");
		
		out.write("<div class='form-group'>");
		out.write("<label>Password</label>");
		out.write("<input type='password' class='form-control' name='password' required='required'/>");
		out.write("</div>");
		
		out.write("<div class='form-group'>");
		out.write("<input type='submit' class='btn btn-primary' name='submit' value='Login'/>");
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

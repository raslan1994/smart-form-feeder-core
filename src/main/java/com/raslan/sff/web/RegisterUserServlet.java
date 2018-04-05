package com.raslan.sff.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.raslan.sff.auth.User;
import com.raslan.sff.auth.UserAuthHelper;
import com.raslan.sff.core.Config;
import com.raslan.sff.core.util.HashGenerator;

public class RegisterUserServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		WebHelper.writeHtmlHeader(resp);
		WebHelper.writeNewUserUI(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String firstName = req.getParameter("fname");
		String lastName = req.getParameter("lname");
		String username = req.getParameter("uname");
		String password = req.getParameter("pass");
		String saPassword = req.getParameter("sapass");
		
		if(firstName == null || lastName == null || username == null || password == null || saPassword == null 
				|| firstName.trim().equals("") || lastName.trim().equals("") || username.trim().equals("") 
				|| password.trim().equals("") || saPassword.trim().equals("")){
			resp.sendRedirect("/register?ec=1");
			return;
		}
		
		//encryption
		password = HashGenerator.getSHA256(password);
		saPassword = HashGenerator.getSHA256(saPassword);
		
		if(!saPassword.toLowerCase().equals(Config.SA_PASSWORD.toLowerCase())){
			resp.sendRedirect("/register?ec=2");
			return;
		}
		
		User newUser = new User();
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setUserName(username);
		newUser.setPassword(password);
		
		UserAuthHelper.createNewUser(newUser);
		resp.sendRedirect("/login");
	}
}

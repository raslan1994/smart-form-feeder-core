package com.raslan.sff.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raslan.sff.auth.UserAuthHelper;
import com.raslan.sff.core.data.forms.FormLayoutManager;

public class FormLayoutWebService extends HttpServlet{
	static ObjectMapper mapper = new ObjectMapper();
	static FormLayoutManager formLayoutManager = FormLayoutManager.getInstance();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//check authentication
		if(!UserAuthHelper.isUserLoggedIn(req)){
			WebHelper.writeUnauthorizedAccessHeader(resp);
			return;
		}
		
		WebHelper.writeServiceHeader(resp);
		String index = req.getParameter("i");
		
		if(index != null && !index.equals("")){
			resp.getWriter().write(mapper.writeValueAsString(formLayoutManager.getAvailableLayouts().get(Integer.parseInt(index))));
		}else{
			resp.getWriter().write(mapper.writeValueAsString(formLayoutManager.getAvailableLayouts()));
		}
		
	}
}

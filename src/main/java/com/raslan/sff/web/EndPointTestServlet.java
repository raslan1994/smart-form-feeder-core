package com.raslan.sff.web;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EndPointTestServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Writer out = resp.getWriter();
		WebHelper.writeHtmlHeader(resp);
		WebHelper.writeBootstrapLinks(out);
		
		Map<String, String[]> params = req.getParameterMap();
		
		out.write("<div class='container' style='max-width: 600px;'>");
		out.write("<h2>Result <small>From End point</small></h2>");
		out.write("<table class='table table hover'>");
		
		for(Entry<String, String[]> entry: params.entrySet()){
			String row = "<tr>";
			String td = "<td>"+entry.getKey()+"</td>";
			td+= "<td>";
			
			for(String str : entry.getValue()){
				td += str;
			}
			
			td+= "</td>";
			row+= td;
			row += "</tr>";
			
			out.write(row);
		}
		
		
		out.write("</table>");
		out.write("</div>");
	}
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raslan.sff.auth;

import java.io.IOException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.raslan.sff.sql.SQLConnectionHelper;
import com.raslan.sff.sql.SQLQueryHelper;

/**
 *
 * @author Raslan Rauff_2
 */
public class UserAuthHelper {
	static SQLConnectionHelper connection = SQLConnectionHelper.getInstance();
    public static boolean isUserLoggedIn(HttpServletRequest request) 
            throws IOException{
    	boolean isUserLoggedIn = true;
    	HttpSession session = request.getSession();
        Cookie[] cookies = request.getCookies();
        String curCookieValue = "";
        String curSessionValue = session.getAttribute("token") != null ?  String.valueOf(session.getAttribute("token")) : "";
        
        if(cookies != null){
            for(Cookie curCookie : cookies){
                if(curCookie.getName().equals("uauthtkn")) curCookieValue = curCookie.getValue();
            }
        }
        
        curCookieValue = curCookieValue != null ? curCookieValue :"";
        
        if((!curCookieValue.equals(curSessionValue)) || curCookieValue.equals("") || curSessionValue.equals(""))
        {
            isUserLoggedIn = false;
        }
        
        return isUserLoggedIn;
      
    }
    
    public static void createNewUser(User user){
    	SQLQueryHelper queryHelper = new SQLQueryHelper();
    	String sql = "insert into `user` (`first_name`,`last_name`,`user_name`,`password`) values (?,?,?,unhex(?));";
    	Object[] parameter = new Object[]{
    			user.getFirstName(),
    			user.getLastName(),
    			user.getUserName(),
    			user.getPassword()
    	};
    	queryHelper.executeUpdate(sql, parameter);
    }
    
    public static User getUser(String username, String Password){
    	User user = null;
    	
    	return user;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raslan.sff.auth;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.raslan.sff.core.util.HashGenerator;
import com.raslan.sff.core.util.Logger;
import com.raslan.sff.sql.SQLConnectionHelper;
import com.raslan.sff.sql.SQLParameterFactory;
import com.raslan.sff.sql.SQLQueryHelper;

/**
 *
 * @author Raslan Rauff_2
 */
public class UserAuthHelper {
	static SQLConnectionHelper connection = SQLConnectionHelper.getInstance();
	static Logger logger = Logger.getInstance();
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
    
    public static User getUserById(int userId){
    	User user = null;
    	String sql = "select `id`,`first_name`,`last_name`,`user_name`,`password`,`last_logined` from `user` where `id` = ?";
    	Object[] parameter = new Object[]{
    			userId
    	};
    	Connection con = connection.getConnection();
    	try {
			PreparedStatement stmt = con.prepareStatement(sql);
			SQLParameterFactory.bindParameters(stmt, parameter);
			
			ResultSet rs = stmt.executeQuery();
			if(rs.first()){
				user = new User();
				user.fillResult(rs);
			}
		} catch (SQLException e) {
			logger.error("UserAuthHelper", e.toString());
		}
    	return user;
    }
    
    public static User getUser(String username, String password){
    	User user = null;
    	String passwordHash = HashGenerator.getSHA256(password);
    	String sql = "select `id`,`first_name`,`last_name`,`user_name`,`password`,`last_logined` from `user` where `user_name` = ? and `password` =unhex(?);";
    	Object[] parameter = new Object[]{
    			username, passwordHash
    	};
    	Connection con = connection.getConnection();
    	try {
			PreparedStatement stmt = con.prepareStatement(sql);
			SQLParameterFactory.bindParameters(stmt, parameter);
			
			ResultSet rs = stmt.executeQuery();
			if(rs.first()){
				user = new User();
				user.fillResult(rs);
			}
		} catch (SQLException e) {
			logger.error("UserAuthHelper", e.toString());
		}
    	return user;
    }
}

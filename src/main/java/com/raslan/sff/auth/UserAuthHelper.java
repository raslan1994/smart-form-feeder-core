/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raslan.sff.auth;

import java.io.IOException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Raslan Rauff_2
 */
public class UserAuthHelper {
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
}

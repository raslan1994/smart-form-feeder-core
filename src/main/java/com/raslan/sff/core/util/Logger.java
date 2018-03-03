package com.raslan.sff.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	private static final Logger singleton = new Logger();
	private SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy h:mm a");
	public static Logger getInstance(){
		return singleton;
	}
	
	private Logger(){
		
	}
	
	public void log(String title, String msg){
		System.out.println(String.format("(%s) %s --> %s", df.format(new Date()), title, msg));
	}
	
	public void error(String title, String msg){
		System.err.println(String.format("(%s) %s --> %s", df.format(new Date()), title, msg));
	}
	
	public void info(String title, String msg){
		System.out.println(String.format("[i %s] %s --> %s", df.format(new Date()), title, msg));
	}
}

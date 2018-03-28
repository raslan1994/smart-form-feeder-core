package com.raslan.sff.core.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator {
	public static String getSHA256(String text){
		//initialize
		String data = "";
	        
		try {
	            //process hash
	            MessageDigest md = MessageDigest.getInstance("SHA-256");
	            byte[] hash = md.digest(text.getBytes("UTF-8"));
	            //converting byte to Hexa 
	            data = byteToHex(hash);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
	            e.printStackTrace();
		}
	        
		//return
		return data;
	    }
	    
	    private static String byteToHex(byte[] bytes){
		//initialize
		StringBuffer data = new StringBuffer();
		//append
	        for(byte byt : bytes){
	            data.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
		}
		//return
		return data.toString();
	}
}

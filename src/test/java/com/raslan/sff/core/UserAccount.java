package com.raslan.sff.core;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.raslan.sff.auth.User;
import com.raslan.sff.auth.UserAuthHelper;

public class UserAccount {
	
	@Test
	public void testLogin() throws JsonProcessingException {
		String username = "raslan";
		String password = "Password12345";
		User user = UserAuthHelper.getUser(username, password);
		
		assertNotEquals(user, null);
	}
}

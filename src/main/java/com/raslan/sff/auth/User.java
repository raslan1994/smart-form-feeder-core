package com.raslan.sff.auth;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class User {
	private int id;
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private Date lastLogined;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getLastLogined() {
		return lastLogined;
	}
	public void setLastLogined(Date lastLogined) {
		this.lastLogined = lastLogined;
	}
	public void fillResult(ResultSet rs) throws SQLException{
		this.id = rs.getInt("id");
		this.firstName = rs.getString("first_name");
		this.lastName = rs.getString("last_name");
		this.userName = rs.getString("user_name");
		this.password = rs.getString("password");
		java.sql.Date td = rs.getDate("last_logined");
		if(td!= null){
			this.lastLogined = new Date(td.getTime());
		}
	}
}

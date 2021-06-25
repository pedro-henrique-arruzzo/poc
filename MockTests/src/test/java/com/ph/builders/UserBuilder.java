package com.ph.builders;


import com.ph.entities.User;

public class UserBuilder {

	private User user;
	
	private UserBuilder() {}
	
	public static UserBuilder aUser(){
		UserBuilder builder = new UserBuilder();
		builder.user = new User();
		builder.user.setName("User 1");
		return builder;
	}
	
	public User now(){
		return user;
	}
}
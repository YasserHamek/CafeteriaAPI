package onlinecafeteria.controller;

import onlinecafeteria.entity.User;

public class UserDto {

	public String userName;
	public long userId;

	public UserDto() {

	}
	
	public UserDto(User user) {
		 this.userName = user.getUserName();
		 this.userId = user.getUserId();
	}
	
	public UserDto(String userName, long userId) {
		this.userName = userName;
		this.userId = userId;
	}
}

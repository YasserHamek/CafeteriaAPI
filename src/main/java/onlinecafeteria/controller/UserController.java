package onlinecafeteria.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import onlinecafeteria.controller.exception.UserNotFoundException;
import onlinecafeteria.entity.User;
import onlinecafeteria.service.*;

@RestController
@RequestMapping(value = "cafeteria/user")
public class UserController {
	
	@Autowired
	private UserServices userServices;
	
	@GetMapping
	public ResponseEntity<Collection<UserDto>> getUsers() {
		if(userServices.getUsers().isEmpty()) {
			throw new UserNotFoundException("there is no user in database");
		}
		Collection<UserDto> usersDto = new ArrayList<UserDto>();
		for (User p: userServices.getUsers()) {
			usersDto.add(new UserDto(p));
		}
		return new ResponseEntity<>(Collections.unmodifiableCollection(usersDto), HttpStatus.OK);
	}
	
	@PostMapping
	public UserDto registerUsers(@RequestParam String name, @RequestParam String password, @RequestParam String role) {
		return new UserDto (userServices.registerUser(name,password,role));	
	}
	
	@DeleteMapping
	public ResponseEntity<HttpStatus> deleteAllUsers() {
		userServices.deleteAllUsers();
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value = "/{userId}")
	public ResponseEntity<UserDto> getUser(@PathVariable long userId) {
		if(userServices.getUsers().isEmpty()) {
			throw new UserNotFoundException("there is no user in database");
		}
		for (User p: userServices.getUsers()) {
			if(p.getUserId()==userId) {
				return new ResponseEntity<>(new UserDto(p), HttpStatus.OK);
			}
		}
		throw new UserNotFoundException("the user with id :"+userId+" do not exist in database");
	}
	
	@PutMapping(value = "/{userId}")
	public ResponseEntity<UserDto> modifyUser(@PathVariable long userId, @RequestParam String newName, @RequestParam String newPassword, @RequestParam String newRole) {
		if(userServices.getUsers().isEmpty()) {
			throw new UserNotFoundException("there is no user in database");
		}
		for (User p: userServices.getUsers()) {
			if(p.getUserId()==userId) {
				userServices.update(userId,newName, newPassword, newRole);
				return new ResponseEntity<>(new UserDto(p), HttpStatus.OK);
			}
		}
		throw new UserNotFoundException("the user with id :"+userId+" do not exist in database");
	}
	
	@DeleteMapping(value = "/{userId}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable long userId) {
		userServices.deleteUser(userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
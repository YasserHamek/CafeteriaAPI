package onlinecafeteria.service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import onlinecafeteria.entity.User;
import onlinecafeteria.repository.UserRepository;

@Service
@Scope("singleton")
public class UserServicesImpl implements UserServices {
	
	private Map<Long,User> connectedUser;
	
	public UserServicesImpl() {
		connectedUser = new HashMap<>();
	}

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User connectUser(String name, String password) {
		Iterator<User> itr = userRepository.findAll().iterator();
		while(itr.hasNext()) {
			User userToFind = itr.next();
			if(name.equals(userToFind.getUserName()) && password.equals(userToFind.getUserPassword())) {
				connectedUser.put(userToFind.getUserId(), userToFind);
				return userToFind;
			}
		}
		return null;
	}

	@Override
	public User registerUser(String userName, String userPassword, String userRole) {
		return userRepository.saveAndFlush((new User(userName,userPassword,userRole)));
	}

	@Override
	public Collection<User> getUsers() {
		return Collections.unmodifiableCollection(userRepository.findAll());
	}

	@Override
	public void deleteAllUsers() {
		userRepository.deleteAll();
		userRepository.flush();
	}

	@Override
	public void update(long userId, String newName, String newPassword, String newRole) {
		userRepository.findById(userId).get().update(newName, newPassword, newRole);
		userRepository.flush();
	}

	@Override
	public void deleteUser(long userId) {
		userRepository.delete(userRepository.findById(userId).get());
		userRepository.flush();
	}
}

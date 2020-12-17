package onlinecafeteria.service;

import java.util.Collection;

import onlinecafeteria.entity.User;

public interface UserServices {

	public User connectUser(String name, String password);

	public User registerUser(String userName, String userPassword, String userRole);

	public Collection<User> getUsers();

	public void deleteAllUsers();

	public void update(long userId, String newName, String newPassword, String newRole);

	public void deleteUser(long userId);
}

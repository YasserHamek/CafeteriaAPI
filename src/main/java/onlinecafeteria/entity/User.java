package onlinecafeteria.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class User {

    private long userId;
    private Basket userBasket;
	private String userName;
    private String userPassword;
	private String userRole;
	
	public User() {

	}

	public User(String userName,String userPassword, String userRole) {
		this.userName = userName;
		this.userPassword = userPassword;
     	this.userRole = userRole;
     	this.userBasket = new Basket();
     	userBasket.setUser(this);
	}
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="basketId")
	public Basket getUserBasket() {
		return userBasket;
	}
	
	public void setUserBasket(Basket userBasket) {
		this.userBasket = userBasket;
	}
	
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long getUserId() {
		return this.userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getUserRole() {
		return this.userRole;
	}
	
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public void setUserRole(long newUserRole) {
		this.userId = newUserRole;
	}
	
	public String getUserPassword() {
			return userPassword;
		}

	public void setUserPassword(String userPassword) {
			this.userPassword = userPassword;
		}
	
	public void update(String newName, String newPassword, String newRole) {
		this.userName=newName;
		this.userPassword=newPassword;
		this.userRole=newRole;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userBasket=" + userBasket + ", userName=" + userName + ", userPassword="
				+ userPassword + ", userRole=" + userRole + "]";
	}
}
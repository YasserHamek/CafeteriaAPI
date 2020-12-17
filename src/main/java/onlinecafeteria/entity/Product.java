package onlinecafeteria.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long productId;
	private String productName;
	private double price;
	
	@ManyToOne(cascade= {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.DETACH})
	@JoinColumn(name="basketId")
	private Basket basket;
	

	public Product() {
	}

	public Product(String productName,double price) {
		this.productName=productName;
		this.price=price;
	}
	
	public String getProductName() {
		return productName;
	}

	public long getProductId() {
		return productId;
	}

	public double getPrice() {
		return price;
	}
	
	public Basket getBasket() {
		return basket;
	}

	public void setBasket(Basket basket) {
		this.basket = basket;
	}

	public Product update(String newName, double newPrice) {
		this.productName=newName;
		this.price=newPrice;
		return this;
	}
}

package onlinecafeteria.entity;
import java.util.*;
import java.util.Map.Entry;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.springframework.stereotype.Component;

@Component
@Entity
public class Basket {
	
	private long basketId;
	private double totalPrice;
	private Map<Long,Product> products;
	private User user;
	
	public Basket() {
		products = new HashMap<Long, Product>();
	}
	
	@OneToOne(mappedBy="userBasket",cascade= {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.DETACH})
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long getBasketId() {
		return basketId;
	}
	
	public void setBasketId(long basketId) {
		this.basketId = basketId;
	}
	
	@OneToMany(mappedBy="basket", cascade= {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.DETACH})
	public Map<Long, Product> getProducts() {	
		return Collections.unmodifiableMap(products);
	}
	
	public void setProducts(Map<Long, Product> products) {
		this.products = products;
	}
	
	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Product addProduct(Product product) {
		products.put(product.getProductId(),product);
		totalPrice = totalPrice + product.getPrice();
		product.setBasket(this);
		return product;
	}

	public Product getProductById(Long productId) {
		return products.get(productId);
	}

	public Product removeProduct(Product product) {
		totalPrice=totalPrice-product.getPrice();
		product.setBasket(null);
		products.remove(product.getProductId());
		return product;
	}
	
	public void removeAllProduct() {
		for(Entry<Long, Product> p : products.entrySet()) {
			p.getValue().setBasket(null);
		}
		products.clear();
		totalPrice=0;
	}
}


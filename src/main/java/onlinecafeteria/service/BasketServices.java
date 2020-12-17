package onlinecafeteria.service;

import java.util.Map;

import onlinecafeteria.entity.Basket;
import onlinecafeteria.entity.Product;

public interface BasketServices {
	
	public Basket getBasket(long userId);

	public Map<Long, Product> getBasketProducts(long userId);
	
	public void deleteProducts(long userId);

	public Product addProductToBasket(long userId,long productId);
	
	public Product updateProduct(String newName, long productId, double newPrice, long userId);

	public Product deleteProduct(long userId,long productId);
}

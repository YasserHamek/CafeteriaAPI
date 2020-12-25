package onlinecafeteria.service;

import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import onlinecafeteria.entity.Basket;
import onlinecafeteria.entity.Product;
import onlinecafeteria.repository.ProductRepository;
import onlinecafeteria.repository.UserRepository;

@Service
@Scope("singleton")
public class BasketServicesImpl implements BasketServices {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private UserRepository userRepository;
	@Override
	public Basket getBasket(long userId) {
		return userRepository.getOne(userId).getUserBasket();
	}

	@Override
	public Map<Long, Product> getBasketProducts(long userId) {
		return Collections.unmodifiableMap(getBasket(userId).getProducts());
	}
	
	@Override
	public void deleteProducts(long userId) {
		userRepository.getOne(userId).getUserBasket().removeAllProduct();
		userRepository.flush();
	}
	
	@Override
	public Product addProductToBasket(long userId,long productId) {
		Product productAdded = userRepository.getOne(userId).getUserBasket().addProduct(productRepository.getOne(productId));
		userRepository.flush();
		return productAdded;
	}
	
	@Override
	public Product updateProduct(String newName, long productId ,double newPrice, long userId) {
		Product p = (getBasketProducts(userId).get(productId).update(newName, newPrice));
		productRepository.flush();
		return p;
	}

	@Override
	public Product deleteProduct(long userId,long productId) {
		Product productdeleted = userRepository.getOne(userId).getUserBasket().removeProduct(productRepository.getOne(productId));
		userRepository.flush();
		return productdeleted;
		}
}

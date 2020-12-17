package onlinecafeteria.service;

import java.util.Collection;
import java.util.Optional;

import onlinecafeteria.entity.Product;

public interface ProductServices {

	public Collection<Product> getCatalogueProduct();
	
	public void deleteAll();

	public Product addProductToCatalogue(String productName, double productPrice);
	
	public Optional<Product> findProductById(long productId);

	public Product updateProduct(String productName, long productId, double productPrice);

	public Product deleteProduct(long productId);
	
	public Optional<Product> findProductByName(String name);
	
}

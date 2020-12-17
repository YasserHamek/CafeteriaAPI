package onlinecafeteria.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import onlinecafeteria.entity.Product;
import onlinecafeteria.repository.ProductRepository;

@Service
@Scope("singleton")
public class ProductServicesImpl implements ProductServices {

	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public Collection<Product> getCatalogueProduct() {
		return Collections.unmodifiableCollection(productRepository.findAll());
	}
	
	@Override
	public void deleteAll() {
		productRepository.deleteAll();
	}

	@Override
	public Product addProductToCatalogue(String productName, double productPrice) {
		return productRepository.saveAndFlush(new Product(productName,productPrice));
	}
	
	@Override
	public Optional<Product> findProductById(long productId) {
		return productRepository.findById(productId);
	}

	@Override
	public Product updateProduct(String newName, long productId ,double newPrice) {
		Product p = (productRepository.getOne(productId)).update(newName, newPrice);
		productRepository.flush();
		return p;
	}

	@Override
	public Product deleteProduct(long productId) {
		Product p = productRepository.getOne(productId);
		Product productSaved = new Product(p.getProductName(),p.getPrice());
		productRepository.deleteById(productId);
		return productSaved;
	}

	@Override
	public Optional<Product> findProductByName(String name) {
		return productRepository.findByProductName(name);
	}

}

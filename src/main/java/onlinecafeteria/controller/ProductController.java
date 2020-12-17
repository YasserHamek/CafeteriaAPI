package onlinecafeteria.controller;

import java.util.*;

import javax.persistence.EntityNotFoundException;

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

import onlinecafeteria.controller.exception.ProductNotFoundException;
import onlinecafeteria.entity.Product;
import onlinecafeteria.service.*;

@RestController
@RequestMapping(value = "cafeteria/products")
public class ProductController {
	
	@Autowired
	private ProductServices productServices;
	
	//Get all products
	@GetMapping()
	public ResponseEntity<Collection<ProductDto>> getCatalogueProducts(){
		if(productServices.getCatalogueProduct().isEmpty()) {
			throw new ProductNotFoundException("Database contains no product");
		}
		Collection<ProductDto> productsDto = new ArrayList<ProductDto>();
		for (Product p: productServices.getCatalogueProduct()) {
			productsDto.add(new ProductDto(p));
		}	
		return new ResponseEntity<>(Collections.unmodifiableCollection(productsDto),HttpStatus.FOUND);
	}
	
	//Delete all products
	@DeleteMapping
	public ResponseEntity<Object> deleteProducts() {
		productServices.deleteAll();
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//Add a product
	@PostMapping
	public ProductDto addProductToCatalogue(@RequestParam String name,@RequestParam double price) {
		return new ProductDto(productServices.addProductToCatalogue(name,price));
	}
	
	//Get a product by id
	@GetMapping(value = "/{productId}")
	public ResponseEntity<ProductDto> getCatalogueProduct(@PathVariable long productId){
		if(productServices.getCatalogueProduct().isEmpty()) {
			throw new ProductNotFoundException("Database contains no product");
		}else if(productServices.findProductById(productId).isEmpty()){
			throw new ProductNotFoundException("No product with id : "+productId+" exist in database"); 
		};
		return new ResponseEntity<>(new ProductDto(productServices.findProductById(productId).get()),HttpStatus.FOUND);
	}
	
	//Delete a product by id
	@DeleteMapping(value = "/{productId}")
	public ProductDto deleteProduct(@PathVariable long productId) {
		return new ProductDto(productServices.deleteProduct(productId));
	}
	
	//Update a product by id
	@PutMapping(value = "/{productId}")
	public ProductDto updateProduct(@RequestParam String newName, @PathVariable long productId, @RequestParam double newPrice) {
		try {
		return new ProductDto(productServices.updateProduct(newName, productId, newPrice));
		} catch(EntityNotFoundException ex) {throw new ProductNotFoundException("Product of id "+productId+" was not found in database",ex);}
	}
	
}
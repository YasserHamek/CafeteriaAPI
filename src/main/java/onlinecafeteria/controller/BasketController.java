package onlinecafeteria.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map.Entry;
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
import onlinecafeteria.controller.exception.EmptyBasketExcpetion;
import onlinecafeteria.controller.exception.ProductNotFoundException;
import onlinecafeteria.controller.exception.UserNotFoundException;
import onlinecafeteria.entity.Product;
import onlinecafeteria.service.*;

@RestController
@RequestMapping("cafeteria/{userId}/basket")
public class BasketController {
	
	@Autowired
	private BasketServices basketServices;
	
	//
	@GetMapping
	public ResponseEntity<Collection<ProductDto>> getBasketProducts(@PathVariable long userId){
		if(basketServices.getBasket(userId) == null) {
			throw new UserNotFoundException("no user with this id : "+userId+" was found in database");
		}
		if((basketServices.getBasketProducts(userId)).isEmpty()) {
			throw new EmptyBasketExcpetion("no product was found in this basket");
		}
		Collection<ProductDto> productsDto = new ArrayList<ProductDto>();
		for(Entry<Long,Product> p : (basketServices.getBasketProducts(userId)).entrySet()) {
			productsDto.add(new ProductDto(p.getValue()));
		}
		return new ResponseEntity<>(Collections.unmodifiableCollection(productsDto),HttpStatus.FOUND);
	}
	
	@DeleteMapping
	public ResponseEntity<HttpStatus> deleteProducts(@PathVariable long userId) {
		if(basketServices.getBasket(userId) == null) {
			throw new UserNotFoundException("no user with this id : "+userId+" was found in database");
		}
		basketServices.deleteProducts(userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Collection<ProductDto>> addProductsToBasket(@PathVariable long userId,@RequestParam long[] productIdArray) {
		if(basketServices.getBasket(userId) == null) {
			throw new UserNotFoundException("no user with this id : "+userId+" was found in database");
		}
		Collection<ProductDto> productsDto = new ArrayList<ProductDto>();
		for(long productId : productIdArray) {
			productsDto.add(new ProductDto(basketServices.addProductToBasket(userId,productId)));
		}
		return new ResponseEntity<>(Collections.unmodifiableCollection(productsDto),HttpStatus.ACCEPTED);
	}
	
	@GetMapping(value = "/{productId}")
	public ResponseEntity<ProductDto> getBasketProduct(@PathVariable long userId, @PathVariable long productId){
		
		if(basketServices.getBasket(userId) == null) {
			throw new UserNotFoundException("no user with this id : "+userId+" was found in database");
		}
		if((basketServices.getBasketProducts(userId)).isEmpty()) {
			throw new EmptyBasketExcpetion("no product was found in this basket");
		}
		
		if(basketServices.getBasketProducts(userId).containsKey(productId)){
			return new ResponseEntity<>(new ProductDto(basketServices.getBasketProducts(userId).get(productId)),HttpStatus.FOUND);
		}else {
			throw new ProductNotFoundException("the product with id :"+productId+" is not yet in your basket");
		}
	}
	
	@PostMapping(value = "/{productId}")
	public ResponseEntity<ProductDto> addProductToBasket(@PathVariable long userId, @PathVariable long productId) {
		return new ResponseEntity<>(new ProductDto(basketServices.addProductToBasket(userId,productId)),HttpStatus.OK);
	}
	
	@PutMapping(value = "/{productId}")
	public ResponseEntity<ProductDto> updateBasketProduct(@PathVariable long userId,
		@PathVariable long productId, @RequestParam String newName, @RequestParam double newPrice) 
	{
		return new ResponseEntity<>
		(new ProductDto(basketServices.updateProduct(newName, productId, newPrice, userId)),HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{productId}")
	public ResponseEntity<ProductDto> deleteProduct(@PathVariable long userId,@PathVariable long productId) {
		return new ResponseEntity<>(new ProductDto(basketServices.deleteProduct(userId,productId)),HttpStatus.OK);
	}
}


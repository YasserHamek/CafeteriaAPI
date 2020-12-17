package onlinecafeteria.controller;

import onlinecafeteria.entity.Product;

public class ProductDto {
	
	public String productName;
	public long productId;
	public double price;

	public ProductDto() {
		
	}
	
	public ProductDto(String productName, long productId, double price) {
		this.productName = productName;
		this.productId = productId;
		this.price = price;
	}

	public ProductDto(Product p) {
		this.productName = p.getProductName();
		this.productId = p.getProductId();
		this.price = p.getPrice();
	}
}
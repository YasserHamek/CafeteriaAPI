/**
 * 
 */
package onlinecafeteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import onlinecafeteria.controller.ProductDto;
import onlinecafeteria.entity.Product;
import reactor.core.publisher.Mono;

@SpringBootTest
class BasketControllerTest {

	private WebClient webClient;

	private Product croissant;
	private Product petitPain;
	private Product cappuccino;
	private Product jusDeFruits;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

	}

	@BeforeEach
	void setUp() throws Exception {
		webClient = WebClient.create("http://localhost:8080/cafeteria/{userId}/basket");

		croissant = new Product("croissant", 0.80);
		petitPain = new Product("petitPain", 0.80);
		cappuccino = new Product("cappuccino", 3.39);
		jusDeFruits = new Product("jusDeFruits", 2.5);

	}

	@AfterEach
	void tearDown() throws Exception {

	}

	@Test
	@DisplayName("Add product to basket")
	void testAddProductToCatalogue() {
		/*
		 * ajout d'un produit dans le catalogue
		 */
		ResponseEntity<ProductDto> addProductToCatalogueResponse = webClient.post()
				.uri(uriBuilder -> uriBuilder.replacePath("/cafeteria/products")
						.queryParam("name", croissant.getProductName()).queryParam("price", croissant.getPrice())
						.build())
				.retrieve().toEntity(ProductDto.class).block();

		/*
		 * ajout du produits dans le panier de l'utilisateur d'identifiant 1
		 */
		ResponseEntity<ProductDto> addProductToBasketResponse = webClient
				.post().uri(uriBuilder -> uriBuilder
						.queryParam("productId", addProductToCatalogueResponse.getBody().productId).build(1))
				.retrieve().toEntity(ProductDto.class).block();

		assertThat(addProductToBasketResponse.getStatusCodeValue()).isEqualTo(200);

		/*
		 * Vérification de la présence du produit dans le panier de l'utilisateur d'identifiant 1
		 */
		ResponseEntity<List<ProductDto>> getBasketProductResponse = webClient
				.get().uri(uriBuilder -> uriBuilder.build(1))
				.retrieve().toEntityList(ProductDto.class).block();

		assertThat(getBasketProductResponse.getBody()).anySatisfy(p -> assertThat(p.productName).isEqualTo(croissant.getProductName()));
	}

	@Test
	@DisplayName("Get basket products")
	void productControllerTest() {

		/*
		 * ajout des produit dans le catalogue
		 */
		ProductDto addProduct1 = webClient.post()
				.uri(uriBuilder -> uriBuilder.replacePath("/cafeteria/products")
						.queryParam("name", croissant.getProductName()).queryParam("price", croissant.getPrice())
						.build())
				.retrieve().bodyToMono(ProductDto.class).block();

		ProductDto addProduct2 = webClient.post()
				.uri(uriBuilder -> uriBuilder.replacePath("/cafeteria/products").queryParam("name", petitPain.getProductName())
						.queryParam("price", petitPain.getPrice()).build())
				.retrieve().bodyToMono(ProductDto.class).block();

		ProductDto addProduct3 = webClient.post()
				.uri(uriBuilder -> uriBuilder.replacePath("/cafeteria/products").queryParam("name", cappuccino.getProductName())
						.queryParam("price", cappuccino.getPrice()).build())
				.retrieve().bodyToMono(ProductDto.class).block();

		ProductDto addProduct4 = webClient.post()
				.uri(uriBuilder -> uriBuilder.replacePath("/cafeteria/products").queryParam("name", jusDeFruits.getProductName())
						.queryParam("price", jusDeFruits.getPrice()).build())
				.retrieve().bodyToMono(ProductDto.class).block();

		/*
		 * ajout des produits dans le panier de l'utilisateur d'identifiant 1
		 */
		webClient.post().uri(uriBuilder -> uriBuilder.queryParam("productId", addProduct1.productId).build(1))
				.retrieve().toEntity(ProductDto.class).block();

		webClient.post().uri(uriBuilder -> uriBuilder.queryParam("productId", addProduct2.productId).build(1))
		.retrieve().toEntity(ProductDto.class).block();
		
		webClient.post().uri(uriBuilder -> uriBuilder.queryParam("productId", addProduct3.productId).build(1))
		.retrieve().toEntity(ProductDto.class).block();
		
		webClient.post().uri(uriBuilder -> uriBuilder.queryParam("productId", addProduct4.productId).build(1))
		.retrieve().toEntity(ProductDto.class).block();
		
		
		/*
		 * Get les produits du panier de l'utilisateur d'identifiant 1 
		 */
		ResponseEntity<List<ProductDto>> getBasketProductResponse = webClient
				.get().uri(uriBuilder -> uriBuilder.build(1))
				.retrieve().toEntityList(ProductDto.class).block();
		
		assertThat(getBasketProductResponse.getStatusCodeValue()).isEqualTo(200);
		
		assertThat(getBasketProductResponse.getBody()).anySatisfy(p -> assertThat(p.productName).isEqualTo(croissant.getProductName()));
		assertThat(getBasketProductResponse.getBody()).anySatisfy(p -> assertThat(p.productName).isEqualTo(petitPain.getProductName()));
		assertThat(getBasketProductResponse.getBody()).anySatisfy(p -> assertThat(p.productName).isEqualTo(cappuccino.getProductName()));
		assertThat(getBasketProductResponse.getBody()).anySatisfy(p -> assertThat(p.productName).isEqualTo(jusDeFruits.getProductName()));
	}

	@Test
	@DisplayName("Delete a basket product")
	void testDeleteProduct() {

		/*
		 * ajout d'un produit dans le catalogue
		 */
		ResponseEntity<ProductDto> addProductToCatalogueResponse = webClient.post()
				.uri(uriBuilder -> uriBuilder.replacePath("/cafeteria/products")
						.queryParam("name", petitPain.getProductName()).queryParam("price", petitPain.getPrice())
						.build())
				.retrieve().toEntity(ProductDto.class).block();

		/*
		 * ajout du produits dans le panier de l'utilisateur d'identifiant 2
		 */
		ResponseEntity<ProductDto> addProductToBasketResponse = webClient
				.post().uri(uriBuilder -> uriBuilder
						.queryParam("productId", addProductToCatalogueResponse.getBody().productId).build(2))
				.retrieve().toEntity(ProductDto.class).block();

		/*
		 * Vérification de la présence du produit dans le panier de l'utilisateur d'identifiant 2
		 */
		Mono<ResponseEntity<List<ProductDto>>> getBasketProductResponse = webClient
				.get().uri(uriBuilder -> uriBuilder.build(2))
				.retrieve().toEntityList(ProductDto.class);

		assertThat(getBasketProductResponse.block().getBody()).anySatisfy(p -> assertThat(p.productName).isEqualTo(petitPain.getProductName()));
		
		/*
		 * Delete le produit du panier de l'utilisateur d'identifiant 2
		 */
		ResponseEntity<ProductDto> deleteProductFromBasketResponse = webClient
				.delete().uri(uriBuilder -> uriBuilder
						.queryParam("productId", addProductToCatalogueResponse.getBody().productId).build(2))
				.retrieve().toEntity(ProductDto.class).block();
		
		assertThat(deleteProductFromBasketResponse.getStatusCodeValue()).isEqualTo(200);
		
		/*
		 * Vérification de la non présence du produit dans le panier de l'utilisateur d'identifiant 2
		 */
		assertThat(getBasketProductResponse.block().getBody()).noneSatisfy(p -> assertThat(p.productName).isEqualTo(petitPain.getProductName()));
	}

}

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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import onlinecafeteria.controller.ProductDto;
import onlinecafeteria.entity.Product;
import reactor.core.publisher.Mono;

@SpringBootTest
class ProductControllerTest {

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
		webClient = WebClient.create("http://localhost:8080/cafeteria");

		croissant = new Product("croissant", 0.80);
		petitPain = new Product("petitPain", 0.80);
		cappuccino = new Product("cappuccino", 3.39);
		jusDeFruits = new Product("jusDeFruits", 2.5);

	}

	@AfterEach
	void tearDown() throws Exception {

	}

	@Test
	@DisplayName("Add product to catalogue")
	void testAddProductToCatalogue() {
		ResponseEntity<ProductDto> response = webClient.post().uri(uriBuilder -> uriBuilder.path("/products")
				.queryParam("name", croissant.getProductName()).queryParam("price", croissant.getPrice()).build())
				.retrieve().toEntity(ProductDto.class).block();

		assertThat(response.getStatusCodeValue()).isEqualTo(200);
		assertThat(response.getBody().productName).isEqualTo("croissant");
		assertThat(response.getBody().price).isEqualTo(0.80);
	}

	@Test
	@DisplayName("Get catalogue products")
	void productControllerTest() {

		webClient.post()
				.uri(uriBuilder -> uriBuilder.path("/products").queryParam("name", croissant.getProductName())
						.queryParam("price", croissant.getPrice()).build())
				.retrieve().bodyToMono(ProductDto.class).block();

		webClient.post()
				.uri(uriBuilder -> uriBuilder.path("/products").queryParam("name", petitPain.getProductName())
						.queryParam("price", petitPain.getPrice()).build())
				.retrieve().bodyToMono(ProductDto.class).block();

		webClient.post()
				.uri(uriBuilder -> uriBuilder.path("/products").queryParam("name", cappuccino.getProductName())
						.queryParam("price", cappuccino.getPrice()).build())
				.retrieve().bodyToMono(ProductDto.class).block();

		webClient.post()
				.uri(uriBuilder -> uriBuilder.path("/products").queryParam("name", jusDeFruits.getProductName())
						.queryParam("price", jusDeFruits.getPrice()).build())
				.retrieve().bodyToMono(ProductDto.class).block();

		ResponseEntity<List<ProductDto>> response = webClient.get().uri("/products").retrieve()
				.toEntityList(new ParameterizedTypeReference<ProductDto>() {
				}).block();

		assertThat(response.getStatusCodeValue()).isEqualTo(200);

		assertThat(response.getBody()).anySatisfy(p -> assertThat(p.productName).isEqualTo(croissant.getProductName()));
		assertThat(response.getBody()).anySatisfy(p -> assertThat(p.productName).isEqualTo(petitPain.getProductName()));
		assertThat(response.getBody())
				.anySatisfy(p -> assertThat(p.productName).isEqualTo(cappuccino.getProductName()));
		assertThat(response.getBody())
				.anySatisfy(p -> assertThat(p.productName).isEqualTo(jusDeFruits.getProductName()));
	}

	@Test
	@DisplayName("Delete a catalogue product")
	void testDeleteProduct() {

		ResponseEntity<ProductDto> response = webClient.post().uri(uriBuilder -> uriBuilder.path("/products")
				.queryParam("name", "pomme").queryParam("price", 0.80).build()).retrieve().toEntity(ProductDto.class)
				.block();

		ResponseEntity<ProductDto> deleteResponse = webClient
				.delete().uri(uriBuilder -> uriBuilder.path("/products")
						.queryParam("productId", response.getBody().productId).build())
				.retrieve().toEntity(ProductDto.class).block();

		assertThat(deleteResponse.getStatusCodeValue()).isEqualTo(200);

		assertThat(
				webClient.get().uri("/products").retrieve().toEntityList(new ParameterizedTypeReference<ProductDto>() {
				}).block().getBody()).noneSatisfy(p -> assertThat(p.productName).isEqualTo("pomme"));
	}

	@Test
	@DisplayName("Update a catalogue product")
	void testpdateProduct() {

		ResponseEntity<ProductDto> postResponse = webClient.post().uri(
				uriBuilder -> uriBuilder.path("/products").queryParam("name", "orange").queryParam("price", 1).build())
				.retrieve().toEntity(ProductDto.class).block();
		System.out.println(postResponse.getBody().productName);
		System.out.println(postResponse.getBody().price);
		System.out.println(postResponse.getBody().productId);
		
		ResponseEntity<ProductDto> updateResponse = webClient.put()
				.uri(uriBuilder -> uriBuilder.path("/products").queryParam("newName", "orange2")
						.queryParam("newPrice", 1.5).queryParam("productId", postResponse.getBody().productId).build())
				.retrieve().toEntity(ProductDto.class).block();

		assertThat(updateResponse.getStatusCodeValue()).isEqualTo(200);
		System.out.println(updateResponse.getBody().productName);
		System.out.println(updateResponse.getBody().price);
		System.out.println(updateResponse.getBody().productId);
		
		assertThat(
				webClient.get().uri("/products").retrieve().toEntityList(new ParameterizedTypeReference<ProductDto>() {
				}).block().getBody()).anySatisfy(p -> assertThat(p.productName).isEqualTo("orange2"));
		
		assertThat(
				webClient.get().uri("/products").retrieve().toEntityList(new ParameterizedTypeReference<ProductDto>() {
				}).block().getBody()).noneSatisfy(p -> assertThat(p.productName).isEqualTo("orange"));
	}
}

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import onlinecafeteria.controller.ProductDto;
import onlinecafeteria.controller.UserDto;
import onlinecafeteria.entity.Product;
import onlinecafeteria.repository.UserRepository;
import onlinecafeteria.service.UserServices;
import reactor.core.publisher.Mono;

@SpringBootTest
class UserControllerTest {
	
	private WebClient webClient;


	@BeforeAll
	static void setUpBeforeClass() throws Exception {

	}

	@BeforeEach
	void setUp() throws Exception {
		webClient = WebClient.create("http://localhost:8080/cafeteria/user");

	}

	@AfterEach
	void tearDown() throws Exception {

	}

	@Test
	@DisplayName("Registre a user")
	void testAddProductToCatalogue() {

		/*
		 * Add a user
		 */
		ResponseEntity<ProductDto> addUser = webClient.post().uri(uriBuilder -> uriBuilder.queryParam("name", "user1")
				.queryParam("password", "123456").queryParam("role", "User").build()).retrieve()
				.toEntity(ProductDto.class).block();

		assertThat(addUser.getStatusCodeValue()).isEqualTo(200);

		/*
		 * checking the existence of the added user
		 */
		ResponseEntity<List<UserDto>> getUsersResponse = webClient.get().retrieve().toEntityList(UserDto.class).block();
		
		assertThat(getUsersResponse.getBody()).anySatisfy(u -> assertThat(u.userName).isEqualTo("user1"));

	}



}

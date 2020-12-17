package onlinecafeteria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import onlinecafeteria.entity.Product;
import onlinecafeteria.entity.User;
import onlinecafeteria.repository.ProductRepository;
import onlinecafeteria.repository.UserRepository;

@Component
public class CafeteriaCommandLineRunner implements CommandLineRunner {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public void run(String... args) throws Exception
	{
		User yasser = new User("Yasser","1234","Admin");
		User personne1 = new User("Personne1","5648","User");
		User personne2 = new User("Personne2","65123","Admin");
		User personne3 = new User("Personne3","dfg132","User");
		
		userRepository.save(yasser);
		userRepository.save(personne1);
		userRepository.save(personne2);
		userRepository.save(personne3);
		
		productRepository.save(new Product("eau", 0.80));
		productRepository.save(new Product("Pizza 4 Fromages supplément champignon",12.45));
		productRepository.save(new Product("Caffè corretto",  2.30));
		productRepository.save(new Product("Sandwich au homard", 35.10));
	}
}

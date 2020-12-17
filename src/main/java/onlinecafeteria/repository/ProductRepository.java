package onlinecafeteria.repository;

import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import onlinecafeteria.entity.Product;

@Repository
@Scope("singleton")
@Transactional
public interface ProductRepository extends JpaRepository<Product,Long> {
	
	public Optional<Product> findByProductName(String name);
}

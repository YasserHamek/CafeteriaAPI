package onlinecafeteria.repository;

import javax.transaction.Transactional;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import onlinecafeteria.entity.Basket;

@Repository
@Scope("singleton")
@Transactional
public interface BasketRepository extends JpaRepository<Basket,Long> {

}

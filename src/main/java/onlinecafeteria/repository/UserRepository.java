package onlinecafeteria.repository;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import onlinecafeteria.entity.User;

@Repository
@Scope("singleton")
@Transactional
public interface UserRepository extends JpaRepository<User,Long> {
	
}

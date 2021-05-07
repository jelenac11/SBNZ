package better.me.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import better.me.model.RegisteredUser;

@Repository
public interface IRegisteredUser extends JpaRepository<RegisteredUser, Long> {

	RegisteredUser findByEmail(String email);

	RegisteredUser findByUsername(String username);

}

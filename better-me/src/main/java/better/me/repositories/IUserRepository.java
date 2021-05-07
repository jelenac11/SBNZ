package better.me.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import better.me.model.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

	User findByEmail(String email);

}

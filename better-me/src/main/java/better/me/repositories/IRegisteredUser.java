package better.me.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import better.me.modelDB.RegisteredUserDB;

@Repository
public interface IRegisteredUser extends JpaRepository<RegisteredUserDB, Long> {

	RegisteredUserDB findByEmail(String email);

	RegisteredUserDB findByUsername(String username);

}

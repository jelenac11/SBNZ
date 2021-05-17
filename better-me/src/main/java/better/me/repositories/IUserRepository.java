package better.me.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import better.me.modelDB.UserDB;

@Repository
public interface IUserRepository extends JpaRepository<UserDB, Long> {

	UserDB findByUsername(String username);

	UserDB findByEmail(String email);

}

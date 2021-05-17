package better.me.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import better.me.modelDB.AuthorityDB;

@Repository
public interface IAuthorityRepository extends JpaRepository<AuthorityDB, Long> {

	AuthorityDB findByName(String name);

}

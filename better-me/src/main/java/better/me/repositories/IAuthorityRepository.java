package better.me.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import better.me.model.Authority;

@Repository
public interface IAuthorityRepository extends JpaRepository<Authority, Long> {

	Authority findByName(String name);

}

package better.me.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import better.me.modelDB.AllergenDB;

@Repository
public interface IAllergenRepository extends JpaRepository<AllergenDB, Long> {

}

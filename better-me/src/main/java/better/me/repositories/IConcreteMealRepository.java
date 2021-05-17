package better.me.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import better.me.modelDB.ConcreteMealDB;

@Repository
public interface IConcreteMealRepository extends JpaRepository<ConcreteMealDB, Long>{

}

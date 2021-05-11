package better.me.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import better.me.model.ConcreteMeal;

@Repository
public interface IConcreteMealRepository extends JpaRepository<ConcreteMeal, Long>{

}

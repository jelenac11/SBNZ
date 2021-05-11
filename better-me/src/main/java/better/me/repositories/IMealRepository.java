package better.me.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import better.me.model.Meal;

@Repository
public interface IMealRepository extends JpaRepository<Meal, Long>{

	Meal findByName(String name);

}

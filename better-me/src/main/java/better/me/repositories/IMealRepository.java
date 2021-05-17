package better.me.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import better.me.modelDB.MealDB;

@Repository
public interface IMealRepository extends JpaRepository<MealDB, Long>{

	MealDB findByName(String name);

}

package better.me.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import better.me.modelDB.GroceryDB;

@Repository
public interface IGroceryRepository extends JpaRepository<GroceryDB, Long>{

	GroceryDB findByName(String name);

}

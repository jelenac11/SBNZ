package better.me.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import better.me.modelDB.GradeDB;

@Repository
public interface IGradeRepository extends JpaRepository<GradeDB, Long> {

	@Query(value = "SELECT * FROM grades g WHERE g.user_id = ?1 AND g.meal_id = ?2", nativeQuery = true)
	GradeDB findByGraderAndMealId(Long userId, Long mealId);

}

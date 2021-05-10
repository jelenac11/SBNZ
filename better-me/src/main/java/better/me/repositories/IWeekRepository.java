package better.me.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import better.me.model.Week;

@Repository
public interface IWeekRepository extends JpaRepository<Week, Long> {

}

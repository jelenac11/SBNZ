package better.me.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import better.me.modelDB.DayDB;

@Repository
public interface IDayRepository extends JpaRepository<DayDB, Long>{

}

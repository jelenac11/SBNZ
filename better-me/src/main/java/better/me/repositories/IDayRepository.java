package better.me.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import better.me.model.Day;

@Repository
public interface IDayRepository extends JpaRepository<Day, Long>{

}

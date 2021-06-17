package better.me.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import better.me.modelDB.AlarmDB;

@Repository
public interface IAlarmRepository  extends JpaRepository<AlarmDB, Long> {

	@Query(value = "SELECT * FROM alarms a WHERE a.user_id = ?1", nativeQuery = true)
	AlarmDB findByUserId(Long userId);
	
}

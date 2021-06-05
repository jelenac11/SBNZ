package better.me.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import better.me.modelDB.AlarmDB;

@Repository
public interface IAlarmRepository  extends JpaRepository<AlarmDB, Long> {

}

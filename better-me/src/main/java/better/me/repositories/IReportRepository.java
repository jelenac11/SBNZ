package better.me.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import better.me.modelDB.AdminReportDB;

@Repository
public interface IReportRepository extends JpaRepository<AdminReportDB, Long> {

	List<AdminReportDB> findAllByOrderByDateDesc();

}

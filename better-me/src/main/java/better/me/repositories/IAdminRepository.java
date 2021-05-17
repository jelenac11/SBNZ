package better.me.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import better.me.modelDB.AdminDB;

@Repository
public interface IAdminRepository extends JpaRepository<AdminDB, Long> {

}

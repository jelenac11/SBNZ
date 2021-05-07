package better.me.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import better.me.model.Admin;

@Repository
public interface IAdminRepository extends JpaRepository<Admin, Long> {

}

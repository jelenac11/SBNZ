package better.me.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import better.me.modelDB.AdminDB;
import better.me.repositories.IAdminRepository;

@Service
public class AdminService {

	@Autowired
	private IAdminRepository adminRepository;

	public AdminDB getById(Long id) {
		Optional<AdminDB> a = adminRepository.findById(id);
		if (!a.isPresent()) {
			return null;
		}
		return a.get();
	}

}

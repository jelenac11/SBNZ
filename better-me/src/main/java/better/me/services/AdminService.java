package better.me.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import better.me.model.Admin;
import better.me.repositories.IAdminRepository;

@Service
public class AdminService {

	@Autowired
	private IAdminRepository adminRepository;

	public Admin getById(Long id) {
		Optional<Admin> a = adminRepository.findById(id);
		if (!a.isPresent()) {
			return null;
		}
		return a.get();
	}

}

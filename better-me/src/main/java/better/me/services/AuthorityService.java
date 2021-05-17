package better.me.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import better.me.modelDB.AuthorityDB;
import better.me.repositories.IAuthorityRepository;

@Service
public class AuthorityService {

	@Autowired
	private IAuthorityRepository authorityRepository;

	public List<AuthorityDB> findById(Long id) {
		Optional<AuthorityDB> auth = this.authorityRepository.findById(id);
		List<AuthorityDB> auths = new ArrayList<>();
		if (auth.isPresent()) {
			auths.add(auth.get());
		}
		return auths;
	}

	public List<AuthorityDB> findByName(String name) {
		AuthorityDB auth = this.authorityRepository.findByName(name);
		List<AuthorityDB> auths = new ArrayList<>();
		if (auth != null) {
			auths.add(auth);
		}
		return auths;
	}

}

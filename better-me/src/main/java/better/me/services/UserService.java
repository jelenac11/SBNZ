package better.me.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import better.me.modelDB.UserDB;
import better.me.repositories.IUserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private IUserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserDB user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with email '%s'.", email));
		} else {
			return user;
		}
	}

	public Iterable<UserDB> getAll() {
		return null;
	}

	public UserDB getById(Long id) {
		return null;
	}

	public UserDB create(UserDB entity) throws Exception {
		return null;
	}

	public boolean delete(Long id) throws Exception {
		return false;
	}

	public UserDB update(Long id, UserDB entity) throws Exception {
		return null;
	}

	public UserDB findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public UserDB findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

}

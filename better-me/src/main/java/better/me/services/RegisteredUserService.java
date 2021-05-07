package better.me.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import better.me.model.Authority;
import better.me.model.RegisteredUser;
import better.me.model.User;
import better.me.repositories.IRegisteredUser;
import better.me.repositories.IUserRepository;

@Service
public class RegisteredUserService {

	@Autowired
	private IRegisteredUser registeredUserRepository;

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private AuthorityService authService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public RegisteredUser getById(Long id) {
		Optional<RegisteredUser> user = registeredUserRepository.findById(id);
		if (!user.isPresent()) {
			return null;
		}
		return user.get();
	}

	public RegisteredUser create(RegisteredUser entity) throws Exception {
		User usernameUser = userRepository.findByUsername(entity.getUsername());
		if (usernameUser != null) {
			throw new IllegalArgumentException("User with this username already exists.");
		}
		User emailUser = userRepository.findByEmail(entity.getEmail());
		if (emailUser != null) {
			throw new IllegalArgumentException("User with this email already exists.");
		}
		List<Authority> auth = authService.findByName("ROLE_REGISTERED_USER");
		entity.setAuthorities(auth);
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		entity.setLastPasswordResetDate(new Date().getTime());
		return registeredUserRepository.save(entity);
	}

	public RegisteredUser findByEmail(String email) {
		return registeredUserRepository.findByEmail(email);
	}

	public RegisteredUser findByUsername(String username) {
		return registeredUserRepository.findByUsername(username);
	}

}

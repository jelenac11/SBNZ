package better.me.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import better.me.dto.RegisteredUserDTO;
import better.me.dto.WeekDTO;
import better.me.exceptions.NotLoggedInException;
import better.me.exceptions.RequestException;
import better.me.model.Authority;
import better.me.model.RegisteredUser;
import better.me.model.User;
import better.me.model.Week;
import better.me.repositories.IRegisteredUser;
import better.me.repositories.IUserRepository;
import better.me.repositories.IWeekRepository;
import better.me.util.MyLogger;

@Service
public class RegisteredUserService {

	@Autowired
	private IRegisteredUser registeredUserRepository;

	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IWeekRepository weekRepository;

	@Autowired
	private AuthorityService authService;
	
	@Autowired
	private KieContainer kieContainer;

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
	
	public String determineBmi() throws NotLoggedInException, RequestException {
		User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (current == null) throw new NotLoggedInException("You must login first. No logged in user found!");
		
		RegisteredUser rUser = registeredUserRepository.findByEmail(current.getEmail());
		if (rUser == null) throw new NotLoggedInException("Registered user must be logged in!");
		
		KieSession kieSession = kieContainer.newKieSession("session");
		kieSession.getAgenda().getAgendaGroup("bmi").setFocus();

		kieSession.setGlobal("myLogger", new MyLogger());
	
		RegisteredUserDTO userDto = new RegisteredUserDTO(rUser);
		WeekDTO weekDto = new WeekDTO();

		kieSession.insert(userDto);
		kieSession.insert(weekDto);
		
		kieSession.fireAllRules();
		kieSession.dispose();
		
		rUser.setBmi(userDto.getBmi());
		registeredUserRepository.save(rUser);
		
		Week week = new Week(weekDto, rUser);
		weekRepository.save(week);
		
		return userDto.getBmi() + "";
	}

}

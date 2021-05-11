package better.me.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import better.me.dto.RegisteredUserDTO;
import better.me.enums.ActivityLevel;
import better.me.enums.BodyType;
import better.me.enums.Diet;
import better.me.enums.Sex;
import better.me.exceptions.NotLoggedInException;
import better.me.exceptions.RequestException;
import better.me.facts.RegisteredUserFact;
import better.me.facts.WeekFact;
import better.me.model.Allergen;
import better.me.model.Authority;
import better.me.model.Day;
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
	
	public WeekFact determineBmi() throws RequestException {
		User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		RegisteredUser rUser = registeredUserRepository.findByEmail(current.getEmail());
		
		KieSession kieSession = kieContainer.newKieSession("session");
		kieSession.getAgenda().getAgendaGroup("bmi").setFocus();

		kieSession.setGlobal("myLogger", new MyLogger());
	
		RegisteredUserFact userFact = new RegisteredUserFact(rUser);
		WeekFact weekFact = new WeekFact();

		kieSession.insert(userFact);
		kieSession.insert(weekFact);
		
		kieSession.fireAllRules();
		kieSession.dispose();
		
		rUser.setBmi(userFact.getBmi());
		registeredUserRepository.save(rUser);
		
		return weekFact;
	}
	
	public WeekFact determineNutrition(WeekFact weekFact) throws RequestException {
		User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		RegisteredUser rUser = registeredUserRepository.findByEmail(current.getEmail());

		KieSession kieSession = kieContainer.newKieSession("session");
		kieSession.getAgenda().getAgendaGroup("nutrition").setFocus();

		kieSession.setGlobal("myLogger", new MyLogger());

		RegisteredUserFact userFact = new RegisteredUserFact(rUser);

		kieSession.insert(userFact);
		kieSession.insert(weekFact);

		kieSession.fireAllRules();
		kieSession.dispose();
		
		rUser.setActivityCount(userFact.getActivityCount());
		registeredUserRepository.save(rUser);
		
		return weekFact;
	}
	
	public RegisteredUserDTO fillInfo(RegisteredUserDTO user) throws NotLoggedInException, RequestException {
		User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (current == null) throw new NotLoggedInException("You must login first. No logged in user found!");
		
		RegisteredUser rUser = registeredUserRepository.findByEmail(current.getEmail());
		if (rUser == null) throw new NotLoggedInException("Registered user must be logged in!");
		
		rUser.setAge(user.getAge());
		rUser.setSex(Sex.valueOf(user.getSex()));
		rUser.setHeight(user.getHeight());
		rUser.setWeight(user.getWeight());
		rUser.setBodyType(BodyType.valueOf(user.getBodyType()));
		rUser.setActivityLevel(ActivityLevel.valueOf(user.getActivityLevel()));
		rUser.setDiet(Diet.valueOf(user.getDiet()));
		rUser.setAllergens(new HashSet<Allergen>(user.getAllergens()));
		registeredUserRepository.save(rUser);
		
		WeekFact weekFact = determineBmi();
		WeekFact weekNutritionFact = determineNutrition(weekFact);
		Week week = new Week(weekNutritionFact, rUser);
		ArrayList<Day> days = new ArrayList<Day>();
		for (int i = 0; i < 7; i++) {
			days.add(new Day());
		}
		week.setDays(new HashSet<Day>(days));
		weekRepository.save(week);
		
		return new RegisteredUserDTO(rUser);
	}

}

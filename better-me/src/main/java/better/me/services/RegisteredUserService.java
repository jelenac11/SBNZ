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
import better.me.model.RegisteredUser;
import better.me.model.Week;
import better.me.modelDB.AllergenDB;
import better.me.modelDB.AuthorityDB;
import better.me.modelDB.DayDB;
import better.me.modelDB.RegisteredUserDB;
import better.me.modelDB.UserDB;
import better.me.modelDB.WeekDB;
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

	public RegisteredUserDB getById(Long id) {
		Optional<RegisteredUserDB> user = registeredUserRepository.findById(id);
		if (!user.isPresent()) {
			return null;
		}
		return user.get();
	}

	public RegisteredUserDB create(RegisteredUserDB entity) throws Exception {
		UserDB usernameUser = userRepository.findByUsername(entity.getUsername());
		if (usernameUser != null) {
			throw new IllegalArgumentException("User with this username already exists.");
		}
		UserDB emailUser = userRepository.findByEmail(entity.getEmail());
		if (emailUser != null) {
			throw new IllegalArgumentException("User with this email already exists.");
		}
		List<AuthorityDB> auth = authService.findByName("ROLE_REGISTERED_USER");
		entity.setAuthorities(auth);
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		entity.setLastPasswordResetDate(new Date().getTime());
		return registeredUserRepository.save(entity);
	}

	public RegisteredUserDB findByEmail(String email) {
		return registeredUserRepository.findByEmail(email);
	}

	public RegisteredUserDB findByUsername(String username) {
		return registeredUserRepository.findByUsername(username);
	}
	
	public Week determineBmi() throws RequestException {
		UserDB current = (UserDB) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		RegisteredUserDB rUser = registeredUserRepository.findByEmail(current.getEmail());
		
		KieSession kieSession = kieContainer.newKieSession("session");
		kieSession.getAgenda().getAgendaGroup("bmi").setFocus();

		kieSession.setGlobal("myLogger", new MyLogger());
	
		RegisteredUser userFact = new RegisteredUser(rUser);
		Week weekFact = new Week();

		kieSession.insert(userFact);
		kieSession.insert(weekFact);
		
		kieSession.fireAllRules();
		kieSession.dispose();
		
		rUser.setBmi(userFact.getBmi());
		registeredUserRepository.save(rUser);
		
		return weekFact;
	}
	
	public Week determineNutrition(Week weekFact) throws RequestException {
		UserDB current = (UserDB) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		RegisteredUserDB rUser = registeredUserRepository.findByEmail(current.getEmail());

		KieSession kieSession = kieContainer.newKieSession("session");
		kieSession.getAgenda().getAgendaGroup("nutrition").setFocus();

		kieSession.setGlobal("myLogger", new MyLogger());

		RegisteredUser userFact = new RegisteredUser(rUser);

		kieSession.insert(userFact);
		kieSession.insert(weekFact);

		kieSession.fireAllRules();
		kieSession.dispose();
		
		rUser.setActivityCount(userFact.getActivityCount());
		registeredUserRepository.save(rUser);
		
		return weekFact;
	}
	
	public RegisteredUserDTO fillInfo(RegisteredUserDTO user) throws NotLoggedInException, RequestException {
		UserDB current = (UserDB) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (current == null) throw new NotLoggedInException("You must login first. No logged in user found!");
		
		RegisteredUserDB rUser = registeredUserRepository.findByEmail(current.getEmail());
		if (rUser == null) throw new NotLoggedInException("Registered user must be logged in!");
		
		rUser.setAge(user.getAge());
		rUser.setSex(Sex.valueOf(user.getSex()));
		rUser.setHeight(user.getHeight());
		rUser.setWeight(user.getWeight());
		rUser.setBodyType(BodyType.valueOf(user.getBodyType()));
		rUser.setActivityLevel(ActivityLevel.valueOf(user.getActivityLevel()));
		rUser.setDiet(Diet.valueOf(user.getDiet()));
		rUser.setAllergens(new HashSet<AllergenDB>(user.getAllergens()));
		registeredUserRepository.save(rUser);
		
		Week weekFact = determineBmi();
		Week weekNutritionFact = determineNutrition(weekFact);
		WeekDB week = new WeekDB(weekNutritionFact, rUser);
		ArrayList<DayDB> days = new ArrayList<DayDB>();
		for (int i = 0; i < 7; i++) {
			days.add(new DayDB());
		}
		week.setDays(new HashSet<DayDB>(days));
		weekRepository.save(week);
		
		return new RegisteredUserDTO(rUser);
	}

}

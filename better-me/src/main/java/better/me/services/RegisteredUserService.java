package better.me.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import better.me.dto.RegisteredUserDTO;
import better.me.dto.WeekDTO;
import better.me.enums.ActivityLevel;
import better.me.enums.BodyType;
import better.me.enums.Category;
import better.me.enums.Diet;
import better.me.enums.Sex;
import better.me.exceptions.NotLoggedInException;
import better.me.exceptions.RequestException;
import better.me.model.Day;
import better.me.model.RegisteredUser;
import better.me.model.Week;
import better.me.modelDB.AllergenDB;
import better.me.modelDB.AuthorityDB;
import better.me.modelDB.DayDB;
import better.me.modelDB.RegisteredUserDB;
import better.me.modelDB.UserDB;
import better.me.modelDB.WeekDB;
import better.me.repositories.IAllergenRepository;
import better.me.repositories.IRegisteredUser;
import better.me.repositories.IUserRepository;
import better.me.repositories.IWeekRepository;

@Service
public class RegisteredUserService {

	@Autowired
	private IRegisteredUser registeredUserRepository;
	
	@Autowired
	private IAllergenRepository allergenRepository;

	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IWeekRepository weekRepository;

	@Autowired
	private AuthorityService authService;
	
	@Autowired
	private KieSession kieSession;

	@Autowired
	private PasswordEncoder passwordEncoder;

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
		ArrayList<AllergenDB> allergs = new ArrayList<AllergenDB>();
		for (AllergenDB allergen : user.getAllergens()) {
			AllergenDB a = allergenRepository.findByName(allergen.getName()).get();
			allergs.add(a);
		}
		rUser.setAllergens(new HashSet<AllergenDB>(allergs));
		registeredUserRepository.save(rUser);
		
		RegisteredUserDB modifiedUser = determineBmiAndNutrition(rUser);
		registeredUserRepository.save(modifiedUser);
		return new RegisteredUserDTO(rUser);
	}
	
	public RegisteredUserDB determineBmiAndNutrition(RegisteredUserDB rUser) throws RequestException {
		kieSession.getAgenda().getAgendaGroup("bmi").setFocus();
		RegisteredUser userFact = new RegisteredUser(rUser);
		Week weekFact = new Week();

		kieSession.insert(userFact);
		kieSession.insert(weekFact);
		
		kieSession.fireAllRules();
		
		kieSession.dispose();
		
		rUser.setBmi(userFact.getBmi());
		rUser.setActivityCount(userFact.getActivityCount());
		WeekDB weekDB = new WeekDB(weekFact, rUser);
		rUser.getWeeks().add(weekDB);
		
		registeredUserRepository.save(rUser);
		weekRepository.save(weekDB);
		return rUser;
	}
	
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

	public RegisteredUserDB save(RegisteredUser user) {
		RegisteredUserDB rUser = findByEmail(user.getEmail());
		rUser.setCategory(Category.valueOf(user.getCategory()));
		rUser.setPreviousCategory(Category.valueOf(user.getPreviousCategory()));
		rUser.setScore(user.getScore());
		if (user.getWeeks().size() != rUser.getWeeks().size()) {
			rUser.getWeeks().add(new WeekDB(user.getWeeks().get(user.getWeeks().size() - 1), rUser));
		}
		return registeredUserRepository.save(rUser);
	}

	
	public List<RegisteredUser> findAll() {
		return registeredUserRepository.findAll().stream().map(RegisteredUser::new).collect(Collectors.toList());
	}
	public RegisteredUserDB findByUsername(String username) {
		return registeredUserRepository.findByUsername(username);
	}

	public String getWeekAndDayNumber() {
		UserDB current = (UserDB) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		RegisteredUserDB rUser = registeredUserRepository.findByEmail(current.getEmail());
		int dayNum = 8;
		for (WeekDB w : rUser.getWeeks()) {
			for (DayDB day : w.getDays()) {
				if (!day.isSubmitted()) dayNum--;
			}
		}
		return rUser.getWeeks().size() + " " + dayNum;
	}

	public WeekDTO getWeek() {
		UserDB current = (UserDB) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		RegisteredUserDB rUser = registeredUserRepository.findByEmail(current.getEmail());
		WeekDTO forReturn = null;
		for (WeekDB w : rUser.getWeeks()) {
			for (DayDB day : w.getDays()) {
				if (!day.isSubmitted()) forReturn = new WeekDTO(w);
			}
		}
		return forReturn;
	}

	public Day getDay() {
		UserDB current = (UserDB) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		RegisteredUserDB rUser = registeredUserRepository.findByEmail(current.getEmail());
		Day forReturn = null;
		for (WeekDB w : rUser.getWeeks()) {
			ArrayList<DayDB> days = new ArrayList<DayDB>(w.getDays());
			Collections.sort(days, new Comparator<DayDB>(){
			    public int compare(DayDB d1, DayDB d2) {
			        return d1.getId().compareTo(d2.getId());
			    }
			});
			for (DayDB day : days) {
				if (!day.isSubmitted()) {
					forReturn = new Day(day);
					break;
				}
			}
		}
		return forReturn;
	}

	public void save(RegisteredUserDB rdb) {
		registeredUserRepository.save(rdb);
	}

}

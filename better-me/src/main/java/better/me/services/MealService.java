package better.me.services;

import java.util.List;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import better.me.dto.EatenMealDTO;
import better.me.exceptions.NotLoggedInException;
import better.me.exceptions.RequestException;
import better.me.model.DailyNutrition;
import better.me.model.Day;
import better.me.model.RegisteredUser;
import better.me.model.Week;
import better.me.modelDB.ConcreteMealDB;
import better.me.modelDB.DayDB;
import better.me.modelDB.MealDB;
import better.me.modelDB.RegisteredUserDB;
import better.me.modelDB.UserDB;
import better.me.modelDB.WeekDB;
import better.me.repositories.IConcreteMealRepository;
import better.me.repositories.IDayRepository;
import better.me.repositories.IMealRepository;
import better.me.repositories.IRegisteredUser;
import better.me.repositories.IWeekRepository;
import better.me.util.MyLogger;

@Service
public class MealService {

	@Autowired
	private KieContainer kieContainer;

	@Autowired
	private IRegisteredUser registeredUserRepository;
	
	@Autowired
	private IWeekRepository weekRepository;
	
	@Autowired
	private IMealRepository mealRepository;
	
	@Autowired
	private IConcreteMealRepository concreteMealRepository;
	
	@Autowired
	private IDayRepository dayRepository;
	
	public DailyNutrition calculateNutritions(EatenMealDTO dto) throws NotLoggedInException, RequestException {
		UserDB current = (UserDB) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (current == null) throw new NotLoggedInException("You must login first. No logged in user found!");
		
		RegisteredUserDB rUser = registeredUserRepository.findByEmail(current.getEmail());
		if (rUser == null) throw new NotLoggedInException("Registered user must be logged in!");
		
		WeekDB week = getWeek(rUser);
		int day = getCurrentDay(rUser, week);
		DayDB[] array = week.getDays().toArray( new DayDB[7] );
		DayDB dayObject = array[day];
		
		MealDB meal = getMeal(dto);
		if (meal != null) {
			concreteMealRepository.save(new ConcreteMealDB(null, dto.getGrams(), false, meal, dayObject, null));
		}
		DailyNutrition nutrition = new DailyNutrition();
		nutrition.setDay(day);
		KieSession kieSession = getKieSession();
		kieSession.setGlobal("myLogger", new MyLogger());
		kieSession.insert(new Day(dayRepository.findById(dayObject.getId()).get()));
		kieSession.insert(nutrition);
		
		kieSession.fireAllRules();
		kieSession.dispose();
		return nutrition;
	}

	private MealDB getMeal(EatenMealDTO dto) throws RequestException {
		MealDB meal = mealRepository.findByName(dto.getName());
		if (meal == null) throw new RequestException("Meal doesn't exist");
		
		return meal;
	}

	private WeekDB getWeek(RegisteredUserDB rUser) throws RequestException {
		KieSession kieSession = getKieSession();
		kieSession.insert(rUser);
		
		List<WeekDB> weeks = weekRepository.findAll();
		for (WeekDB w: weeks) {
			kieSession.insert(w);
		}
		WeekDB week = null;
		
		QueryResults result = kieSession.getQueryResults("Fetch week by user", rUser);
		for (QueryResultsRow row : result) {
			week = (WeekDB) row.get("$week");
		}
		if (week == null) throw new RequestException("Week doesn't exist");
		
		kieSession.dispose();
		return week;
	}
	
	private int getCurrentDay(RegisteredUserDB rUser, WeekDB week) throws RequestException {
		DailyNutrition nutrition = new DailyNutrition();
		
		KieSession kieSession = getKieSession();
		kieSession.insert(new RegisteredUser(rUser));
		kieSession.getAgenda().getAgendaGroup("daily-nutrition").setFocus();
		kieSession.setGlobal("myLogger", new MyLogger());
		kieSession.insert(new Week(week));
		kieSession.insert(nutrition);
		kieSession.fireAllRules();
		kieSession.dispose();
        
		return (int) nutrition.getDay();
	}

	private KieSession getKieSession() {
        return kieContainer.newKieSession("session");
    }
}

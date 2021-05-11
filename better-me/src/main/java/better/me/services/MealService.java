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
import better.me.facts.DailyNutritionFact;
import better.me.facts.DayFact;
import better.me.facts.RegisteredUserFact;
import better.me.facts.WeekFact;
import better.me.model.ConcreteMeal;
import better.me.model.Day;
import better.me.model.Meal;
import better.me.model.RegisteredUser;
import better.me.model.User;
import better.me.model.Week;
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
	
	public DailyNutritionFact calculateNutritions(EatenMealDTO dto) throws NotLoggedInException, RequestException {
		User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (current == null) throw new NotLoggedInException("You must login first. No logged in user found!");
		
		RegisteredUser rUser = registeredUserRepository.findByEmail(current.getEmail());
		if (rUser == null) throw new NotLoggedInException("Registered user must be logged in!");
		
		Week week = getWeek(rUser);
		int day = getCurrentDay(rUser, week);
		Day[] array = week.getDays().toArray( new Day[7] );
		Day dayObject = array[day];
		
		Meal meal = getMeal(dto);
		if (meal != null) {
			concreteMealRepository.save(new ConcreteMeal(null, dto.getGrams(), false, meal, dayObject, null));
		}
		DailyNutritionFact nutrition = new DailyNutritionFact();
		nutrition.setDay(day);
		KieSession kieSession = getKieSession();
		kieSession.setGlobal("myLogger", new MyLogger());
		kieSession.insert(new DayFact(dayRepository.findById(dayObject.getId()).get()));
		kieSession.insert(nutrition);
		
		kieSession.fireAllRules();
		kieSession.dispose();
		return nutrition;
	}

	private Meal getMeal(EatenMealDTO dto) throws RequestException {
		Meal meal = mealRepository.findByName(dto.getName());
		if (meal == null) throw new RequestException("Meal doesn't exist");
		
		return meal;
	}

	private Week getWeek(RegisteredUser rUser) throws RequestException {
		KieSession kieSession = getKieSession();
		kieSession.insert(rUser);
		
		List<Week> weeks = weekRepository.findAll();
		for (Week w: weeks) {
			kieSession.insert(w);
		}
		Week week = null;
		
		QueryResults result = kieSession.getQueryResults("Fetch week by user", rUser);
		for (QueryResultsRow row : result) {
			week = (Week) row.get("$week");
		}
		if (week == null) throw new RequestException("Week doesn't exist");
		
		kieSession.dispose();
		return week;
	}
	
	private int getCurrentDay(RegisteredUser rUser, Week week) throws RequestException {
		DailyNutritionFact nutrition = new DailyNutritionFact();
		
		KieSession kieSession = getKieSession();
		kieSession.insert(new RegisteredUserFact(rUser));
		kieSession.getAgenda().getAgendaGroup("daily-nutrition").setFocus();
		kieSession.setGlobal("myLogger", new MyLogger());
		kieSession.insert(new WeekFact(week));
		kieSession.insert(nutrition);
		kieSession.fireAllRules();
		kieSession.dispose();
        
		return (int) nutrition.getDay();
	}

	private KieSession getKieSession() {
        return kieContainer.newKieSession("session");
    }
}

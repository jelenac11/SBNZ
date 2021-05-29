package better.me.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import better.me.dto.EatenMealDTO;
import better.me.dto.NewMealDTO;
import better.me.exceptions.NotLoggedInException;
import better.me.exceptions.RequestException;
import better.me.model.DailyNutrition;
import better.me.model.Day;
import better.me.model.Ingredient;
import better.me.model.Meal;
import better.me.model.Notification;
import better.me.model.Week;
import better.me.modelDB.ConcreteMealDB;
import better.me.modelDB.DayDB;
import better.me.modelDB.GradeDB;
import better.me.modelDB.GroceryDB;
import better.me.modelDB.IngredientDB;
import better.me.modelDB.MealDB;
import better.me.modelDB.RegisteredUserDB;
import better.me.modelDB.UserDB;
import better.me.modelDB.WeekDB;
import better.me.repositories.IConcreteMealRepository;
import better.me.repositories.IDayRepository;
import better.me.repositories.IMealRepository;
import better.me.repositories.IRegisteredUser;

@Service
public class MealService {

	@Autowired
	private KieSession kieSession;

	@Autowired
	private IRegisteredUser registeredUserRepository;
	
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
		
		WeekDB weekDB = (new ArrayList<WeekDB>(rUser.getWeeks())).get(rUser.getWeeks().size() - 1);
		Week week = new Week(weekDB);
		
		int daysSubmitted = 0;
		QueryResults results = kieSession.getQueryResults("Get number of days submitted", week);
		for (QueryResultsRow result: results) {
			daysSubmitted = (int) result.get("$num");
		}
		
		DayDB dayDB = (new ArrayList<DayDB>(weekDB.getDays())).get(daysSubmitted);
		MealDB meal = getMeal(dto);
		if (meal != null) {
			concreteMealRepository.save(new ConcreteMealDB(null, dto.getGrams(), false, meal, dayDB, null));
		}
		
		DailyNutrition nutrition = new DailyNutrition();
		Day day = new Day(dayDB);
		Notification notification = new Notification();
		
		kieSession.getAgenda().getAgendaGroup("daily-nutrition").setFocus();
		kieSession.insert(day);
		kieSession.insert(nutrition);
		kieSession.insert(week);
		kieSession.insert(notification);
		
		dayDB.setCalories(day.getCalories());
		dayDB.setFats(day.getFats());
		dayDB.setCarbs(day.getCarbs());
		dayDB.setProteins(day.getProteins());
		dayDB.setExceed(day.isExceed());
		
		dayRepository.save(dayDB);
		kieSession.fireAllRules();
		kieSession.dispose();
		
		nutrition.setNotification(notification);
		return nutrition;
	}

	private MealDB getMeal(EatenMealDTO dto) throws RequestException {
		MealDB meal = mealRepository.findByName(dto.getName());
		if (meal == null) throw new RequestException("Meal doesn't exist");
		
		return meal;
	}

	public String create(NewMealDTO dto) {
		Meal m = new Meal();
		m.setName(dto.getName());
		m.setTime(dto.getTime());
		m.setIngredients(dto.getIngredients());
		m.setDescription(dto.getDescription());
		
		kieSession.getAgenda().getAgendaGroup("new-meal").setFocus();

		kieSession.insert(m);
		kieSession.fireAllRules();
		kieSession.dispose();
		
		Set<IngredientDB> ingredients = new HashSet<IngredientDB>();
		for (Ingredient i: dto.getIngredients()) {
			ingredients.add(new IngredientDB(null, i.getGrams(), new GroceryDB(i.getGrocery())));
		}
		mealRepository.save(new MealDB(null, dto.getName(), m.getCalories(), m.getCarbs(), m.getProteins(), m.getFats(), m.getTime(), m.getDescription(), ingredients, new HashSet<ConcreteMealDB>(), new HashSet<GradeDB>(), 0));
		return "Meal " + dto.getName() + " added!";
	}
}

package better.me.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import better.me.dto.EatenMealDTO;
import better.me.dto.GradeDTO;
import better.me.dto.NewMealDTO;
import better.me.dto.ResponseMealDTO;
import better.me.events.MealEatenEvent;
import better.me.events.MealRatedEvent;
import better.me.events.NotAllowedToEatEvent;
import better.me.events.UserAteMealEvent;
import better.me.exceptions.NotLoggedInException;
import better.me.exceptions.RequestException;
import better.me.model.ConcreteMeal;
import better.me.model.DailyNutrition;
import better.me.model.Day;
import better.me.model.Grade;
import better.me.model.Ingredient;
import better.me.model.Meal;
import better.me.model.Notification;
import better.me.model.RegisteredUser;
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
import better.me.repositories.IDayRepository;
import better.me.repositories.IGradeRepository;
import better.me.repositories.IGroceryRepository;
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
	private IGradeRepository gradeRepository;
	
	@Autowired
	private IGroceryRepository groceryRepository;

	@Autowired
	private IDayRepository dayRepository;

	@Autowired
	@Qualifier(value = "cepSession")
	private KieSession cepSession;

	public DailyNutrition calculateNutritions(EatenMealDTO dto) throws NotLoggedInException, RequestException {
		UserDB current = (UserDB) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (current == null)
			throw new NotLoggedInException("You must login first. No logged in user found!");

		RegisteredUserDB rUser = registeredUserRepository.findByEmail(current.getEmail());
		if (rUser == null)
			throw new NotLoggedInException("Registered user must be logged in!");

		ArrayList<WeekDB> weeks = new ArrayList<WeekDB>(rUser.getWeeks());
		Collections.sort(weeks, new Comparator<WeekDB>(){
		    public int compare(WeekDB w1, WeekDB w2) {
		        return w1.getId().compareTo(w2.getId());
		    }
		});
		
		WeekDB weekDB = weeks.get(rUser.getWeeks().size() - 1);
		Week week = new Week(weekDB);

		long daysSubmitted = 0;
		QueryResults results = kieSession.getQueryResults("Get number of days submitted", week);
		for (QueryResultsRow result : results) {
			daysSubmitted = (long) result.get("$num");
		}
		
		ArrayList<DayDB> days = new ArrayList<DayDB>(weekDB.getDays());
		Collections.sort(days, new Comparator<DayDB>(){
		    public int compare(DayDB d1, DayDB d2) {
		        return d1.getId().compareTo(d2.getId());
		    }
		});

		DayDB dayDB = days.get((int) daysSubmitted);
		
		MealDB meal = getMeal(dto);
		if (meal != null) {
			dayDB.getConcreteMeals().add(new ConcreteMealDB(null, dto.getGrams(), false, meal, dayDB, new HashSet<IngredientDB>()));
			dayRepository.save(dayDB);
		}

		DailyNutrition nutrition = new DailyNutrition();
		Day day = new Day(dayDB);
		Notification notification = new Notification();

		kieSession.getAgenda().getAgendaGroup("daily-nutrition").setFocus();
		kieSession.insert(day);
		kieSession.insert(nutrition);
		kieSession.insert(week);
		kieSession.insert(notification);

		kieSession.fireAllRules();
		
		dayDB.setCalories(Math.round(day.getCalories()*10.0)/10.0);
		dayDB.setFats(Math.round(day.getFats()*10.0)/10.0);
		dayDB.setCarbs(Math.round(day.getCarbs()*10.0)/10.0);
		dayDB.setProteins(Math.round(day.getProteins()*10.0)/10.0);
		dayDB.setExceed(day.isExceed());

		dayRepository.save(dayDB);
		
		kieSession.dispose();
		
		cepSession.insert(new MealEatenEvent(new Meal(meal)));
		cepSession.insert(new UserAteMealEvent(new RegisteredUser(rUser)));
		cepSession.fireAllRules();
		
		QueryResults res = cepSession.getQueryResults( "getNotAllowedToEatEvent" ); 
		for ( QueryResultsRow row : res ) {
			NotAllowedToEatEvent event = ( NotAllowedToEatEvent ) row.get( "$result" );
			if (event.getUser().getId() == rUser.getId()) {
				rUser.setAllowedToEat(false);
				this.registeredUserRepository.save(rUser);
			}
		}
		
		nutrition.setNotification(notification);
		nutrition.setCalories(Math.round(day.getCalories()*10.0)/10.0);
		nutrition.setFats(Math.round(day.getFats()*10.0)/10.0);
		nutrition.setCarbs(Math.round(day.getCarbs()*10.0)/10.0);
		nutrition.setProteins(Math.round(day.getProteins()*10.0)/10.0);
		return nutrition;
	}

	private MealDB getMeal(EatenMealDTO dto) throws RequestException {
		MealDB meal = mealRepository.findByName(dto.getName());
		if (meal == null)
			throw new RequestException("Meal doesn't exist");

		return meal;
	}

	public String create(NewMealDTO dto) throws RequestException {
		MealDB existName = mealRepository.findByName(dto.getName());
		if (existName != null) {
			throw new RequestException("Meal with given name already exists");
		}
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
		for (Ingredient i : dto.getIngredients()) {
			GroceryDB groc = this.groceryRepository.findByName(i.getGrocery().getName());
			ingredients.add(new IngredientDB(null, i.getGrams(), groc));
		}
		mealRepository.save(new MealDB(null, dto.getName(), Math.round(m.getCalories()*10.0)/10.0, Math.round(m.getCarbs()*10.0)/10.0, Math.round(m.getProteins()*10.0)/10.0, Math.round(m.getFats()*10.0)/10.0,
				m.getTime(), m.getDescription(), ingredients, new HashSet<ConcreteMealDB>(), new HashSet<GradeDB>(),
				0));
		return "Meal " + dto.getName() + " added!";
	}

	public double rate(String name, Integer value) throws NotLoggedInException, RequestException {
		UserDB current = (UserDB) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (current == null)
			throw new NotLoggedInException("You must login first. No logged in user found!");

		RegisteredUserDB rUser = registeredUserRepository.findByEmail(current.getEmail());
		if (rUser == null)
			throw new NotLoggedInException("Registered user must be logged in!");

		if (value < 1 || value > 5)
			throw new RequestException("Rate is invalid.");

		MealDB mealDB = this.mealRepository.findByName(name);
		
		
		GradeDB gradeDB = this.gradeRepository.findByGraderAndMealId(rUser.getId(), mealDB.getId());
		if (gradeDB == null) {
			gradeDB = new GradeDB(null, value, mealDB, rUser);
			rUser.getGrades().add(gradeDB);
			mealDB.getGrades().add(gradeDB);
		} else {
			gradeDB.setValue(value);
			for (GradeDB gr : mealDB.getGrades()) {
				if (gr.getId() == gradeDB.getId()) {
					gr.setValue(value);
				}
			}
		}
		
		Meal meal = new Meal(mealDB);
		Grade grade = new Grade(value, meal, new RegisteredUser(rUser));

		kieSession.getAgenda().getAgendaGroup("rate-meal").setFocus();

		kieSession.insert(meal);
		kieSession.insert(grade);
		kieSession.fireAllRules();
		kieSession.dispose();
		
		mealDB.setAverageGrade(meal.getAverageGrade());
		
		this.mealRepository.save(mealDB);
		this.registeredUserRepository.save(rUser);
		this.gradeRepository.save(gradeDB);
		
		cepSession.insert(new MealRatedEvent(meal, value));

		return meal.getAverageGrade();
	}
	
	public GradeDTO getRate(String name) throws NotLoggedInException, RequestException {
		UserDB current = (UserDB) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (current == null)
			throw new NotLoggedInException("You must login first. No logged in user found!");

		RegisteredUserDB rUser = registeredUserRepository.findByEmail(current.getEmail());
		if (rUser == null)
			throw new NotLoggedInException("Registered user must be logged in!");

		MealDB mealDB = this.mealRepository.findByName(name);
		
		GradeDB gradeDB = this.gradeRepository.findByGraderAndMealId(rUser.getId(), mealDB.getId());
		if (gradeDB != null) {
			return new GradeDTO(gradeDB);
		}
		return new GradeDTO(0);
	}
	
	public List<ResponseMealDTO> findAll() {
		List<ResponseMealDTO> forReturn = new ArrayList<ResponseMealDTO>();
		List<MealDB> meals = mealRepository.findAll();
		for (MealDB mealDB : meals) {
			forReturn.add(new ResponseMealDTO(mealDB));
		}
		return forReturn;
	}

	public DailyNutrition eatCustomMeal(ConcreteMeal dto) throws NotLoggedInException {
		UserDB current = (UserDB) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (current == null)
			throw new NotLoggedInException("You must login first. No logged in user found!");

		RegisteredUserDB rUser = registeredUserRepository.findByEmail(current.getEmail());
		if (rUser == null)
			throw new NotLoggedInException("Registered user must be logged in!");

		ArrayList<WeekDB> weeks = new ArrayList<WeekDB>(rUser.getWeeks());
		Collections.sort(weeks, new Comparator<WeekDB>(){
		    public int compare(WeekDB w1, WeekDB w2) {
		        return w1.getId().compareTo(w2.getId());
		    }
		});
		
		WeekDB weekDB = weeks.get(rUser.getWeeks().size() - 1);
		Week week = new Week(weekDB);

		long daysSubmitted = 0;
		
		System.out.println(rUser.getWeeks().size() - 1);
		System.out.println(weekDB.getDays().size());
		
		QueryResults results = kieSession.getQueryResults("Get number of days submitted", week);
		for (QueryResultsRow result : results) {
			daysSubmitted = (long) result.get("$num");
		}
		
		ArrayList<DayDB> days = new ArrayList<DayDB>(weekDB.getDays());
		Collections.sort(days, new Comparator<DayDB>(){
		    public int compare(DayDB d1, DayDB d2) {
		        return d1.getId().compareTo(d2.getId());
		    }
		});

		DayDB dayDB = days.get((int) daysSubmitted);
		
		Set<IngredientDB> ingredients = new HashSet<IngredientDB>();
		for (Ingredient i : dto.getIngredients()) {
			GroceryDB groc = this.groceryRepository.findByName(i.getGrocery().getName());
			ingredients.add(new IngredientDB(null, i.getGrams(), groc));
		}
		
		dayDB.getConcreteMeals().add(new ConcreteMealDB(null, 0, true, null, dayDB, ingredients));
		dayRepository.save(dayDB);

		DailyNutrition nutrition = new DailyNutrition();
		Day day = new Day(dayDB);
		Notification notification = new Notification();

		kieSession.getAgenda().getAgendaGroup("daily-nutrition").setFocus();
		kieSession.insert(day);
		kieSession.insert(nutrition);
		kieSession.insert(week);
		kieSession.insert(notification);

		kieSession.fireAllRules();
		
		dayDB.setCalories(Math.round(day.getCalories()*10.0)/10.0);
		dayDB.setFats(Math.round(day.getFats()*10.0)/10.0);
		dayDB.setCarbs(Math.round(day.getCarbs()*10.0)/10.0);
		dayDB.setProteins(Math.round(day.getProteins()*10.0)/10.0);
		dayDB.setExceed(day.isExceed());

		dayRepository.save(dayDB);
		
		kieSession.dispose();
		
		cepSession.insert(new UserAteMealEvent(new RegisteredUser(rUser)));
		cepSession.fireAllRules();
		
		QueryResults res = cepSession.getQueryResults( "getNotAllowedToEatEvent" ); 
		for ( QueryResultsRow row : res ) {
			NotAllowedToEatEvent event = ( NotAllowedToEatEvent ) row.get( "$result" );
			if (event.getUser().getId() == rUser.getId()) {
				rUser.setAllowedToEat(false);
				this.registeredUserRepository.save(rUser);
			}
		}
		
		nutrition.setNotification(notification);
		nutrition.setCalories(Math.round(day.getCalories()*10.0)/10.0);
		nutrition.setFats(Math.round(day.getFats()*10.0)/10.0);
		nutrition.setCarbs(Math.round(day.getCarbs()*10.0)/10.0);
		nutrition.setProteins(Math.round(day.getProteins()*10.0)/10.0);
		return nutrition;
	}
}

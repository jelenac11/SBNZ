package better.me.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import better.me.dto.GroceryDTO;
import better.me.dto.RecommendedMealDTO;
import better.me.dto.RegisteredUserDTO;
import better.me.enums.Category;
import better.me.exceptions.NotLoggedInException;
import better.me.model.Grocery;
import better.me.model.Meal;
import better.me.model.RecommendedMeal;
import better.me.model.RegisteredUser;
import better.me.model.Report;
import better.me.model.Week;
import better.me.modelDB.DayDB;
import better.me.modelDB.GroceryDB;
import better.me.modelDB.MealDB;
import better.me.modelDB.RegisteredUserDB;
import better.me.modelDB.UserDB;
import better.me.modelDB.WeekDB;
import better.me.repositories.IAllergenRepository;
import better.me.repositories.IGroceryRepository;
import better.me.repositories.IMealRepository;
import better.me.repositories.IRegisteredUser;

@Service
public class NutritionService {

	@Autowired
	private IRegisteredUser registeredUserRepository;
	
	@Autowired
	private IAllergenRepository allergenRepository;
	
	@Autowired
	private IGroceryRepository groceryRepository;
	
	@Autowired
	private IMealRepository mealRepository;
	
	@Autowired
	private KieSession kieSession;

	public RegisteredUserDTO submitDay() throws NotLoggedInException {
		UserDB current = (UserDB) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (current == null) throw new NotLoggedInException("You must login first. No logged in user found!");
		
		RegisteredUserDB rUser = registeredUserRepository.findByEmail(current.getEmail());
		if (rUser == null) throw new NotLoggedInException("Registered user must be logged in!");
		RegisteredUser user = new RegisteredUser(rUser);
		
		for (DayDB dayDB : new ArrayList<WeekDB>(rUser.getWeeks()).get(rUser.getWeeks().size() - 1).getDays()) {
			if (!dayDB.isSubmitted()) {
				dayDB.setSubmitted(true);
				break;
			}
		}
		
		kieSession.getAgenda().getAgendaGroup("score").setFocus();
		kieSession.insert(user);
		kieSession.insert(new Week(new ArrayList<WeekDB>(rUser.getWeeks()).get(rUser.getWeeks().size() - 1)));
		kieSession.fireAllRules();
		kieSession.dispose();
		
		rUser.setScore(user.getScore());
		rUser.setCategory(Category.valueOf(user.getCategory()));
		rUser.setPreviousCategory(Category.valueOf(user.getPreviousCategory()));
		registeredUserRepository.save(rUser);
		return new RegisteredUserDTO(user);
	}

	public Report getReport() throws NotLoggedInException {
		UserDB current = (UserDB) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (current == null) throw new NotLoggedInException("You must login first. No logged in user found!");
		
		RegisteredUserDB rUser = registeredUserRepository.findByEmail(current.getEmail());
		if (rUser == null) throw new NotLoggedInException("Registered user must be logged in!");
		RegisteredUser user = new RegisteredUser(rUser);
		Report report = new Report();
		
		kieSession.getAgenda().getAgendaGroup("user-report").setFocus();
		kieSession.insert(user);
		kieSession.insert(report);
		kieSession.fireAllRules();
		kieSession.dispose();
		
		return report;
	}

	public Object getAllAllergens() {
		return allergenRepository.findAll();
	}
	
	public List<GroceryDTO> getAllGroceries() {
		List<GroceryDTO> forReturn = new ArrayList<GroceryDTO>();
		List<GroceryDB> groceries = groceryRepository.findAll();
		for (GroceryDB groceryDB : groceries) {
			forReturn.add(new GroceryDTO(groceryDB));
		}
		return forReturn;
	}

	public ResponseEntity<?> createGrocery(GroceryDTO dto) {
		GroceryDB existName = groceryRepository.findByName(dto.getName());
		if (existName != null) {
			return new ResponseEntity<>("Grocery with name " + dto.getName() + " already exists.", HttpStatus.CONFLICT);
		}
		groceryRepository.save(new GroceryDB(dto));
		return new ResponseEntity<>("Grocery " + dto.getName() + " added.", HttpStatus.OK);
	}

	public List<RecommendedMealDTO> getRecommended() {
		UserDB current = (UserDB) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		RegisteredUserDB rUser = registeredUserRepository.findByEmail(current.getEmail());
		kieSession.insert(new RegisteredUser(rUser));
		
		List<MealDB> allMealsDB = mealRepository.findAll();
		List<Meal> allMeals = new ArrayList<Meal>();
		allMealsDB.forEach(meal -> allMeals.add(new Meal(meal)));
		kieSession.insert(allMeals);
		
		List<GroceryDB> allGroceries = groceryRepository.findAll();
		allGroceries.forEach(grocery -> kieSession.insert(new Grocery(grocery)));
		
		kieSession.getAgenda().getAgendaGroup("prepare-for-recommendation").setFocus();
        kieSession.fireAllRules();
        kieSession.getAgenda().getAgendaGroup("meal-recommendation").setFocus();
        kieSession.fireAllRules();
		List<RecommendedMealDTO> forReturn = new ArrayList<RecommendedMealDTO>();
		QueryResults results = kieSession.getQueryResults( "getRecommendedMeal" ); 
		for ( QueryResultsRow row : results ) {
			RecommendedMeal rm = ( RecommendedMeal ) row.get( "$result" );
			forReturn.add(new RecommendedMealDTO(rm));
		}
		
		Collections.sort(forReturn, new Comparator<RecommendedMealDTO>(){
		    public int compare(RecommendedMealDTO r1, RecommendedMealDTO r2) {
		        return new Double(r1.getPoints()).compareTo(new Double(r2.getPoints()));
		    }
		});
		
        kieSession.dispose();
        
		return forReturn;
	}
	
}

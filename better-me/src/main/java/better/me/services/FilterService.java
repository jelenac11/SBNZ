package better.me.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import better.me.dto.FilterDTO;
import better.me.dto.ResponseGroceryDTO;
import better.me.dto.ResponseMealDTO;
import better.me.dto.SortedGroceriesDTO;
import better.me.dto.SortedMealsDTO;
import better.me.exceptions.NotLoggedInException;
import better.me.model.Grocery;
import better.me.model.Meal;
import better.me.model.RegisteredUser;
import better.me.model.SortedGroceries;
import better.me.model.SortedMeals;
import better.me.modelDB.GroceryDB;
import better.me.modelDB.MealDB;
import better.me.modelDB.RegisteredUserDB;
import better.me.modelDB.UserDB;
import better.me.repositories.IGroceryRepository;
import better.me.repositories.IMealRepository;
import better.me.repositories.IRegisteredUser;

@Service
public class FilterService {

	@Autowired
	private KieSession kieSession;

	@Autowired
	private IRegisteredUser registeredUserRepository;
	
	@Autowired
	private IMealRepository mealRepository;

	@Autowired
	private IGroceryRepository groceryRepository;

	public SortedMealsDTO filterMeals(FilterDTO filter) throws NotLoggedInException {
		UserDB current = (UserDB) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (current == null) throw new NotLoggedInException("You must login first. No logged in user found!");
		
		RegisteredUserDB rUser = registeredUserRepository.findByEmail(current.getEmail());
		if (rUser == null) throw new NotLoggedInException("Registered user must be logged in!");
		
		List<MealDB> allMeals = mealRepository.findAll();
		List<Meal> meals = allMeals.stream().map(Meal::new).collect(Collectors.toList());
		List<Meal> withAllergens = new ArrayList<Meal>();
		SortedMeals sorted = new SortedMeals();
	
		kieSession.getAgenda().getAgendaGroup("filter").setFocus();
		kieSession.insert(filter);
		kieSession.insert(meals);
		for (Meal m: meals) {
			kieSession.insert(m);
		}
		kieSession.insert(withAllergens);
		kieSession.insert(new RegisteredUser(rUser));
		kieSession.insert(sorted);
		kieSession.fireAllRules();
		kieSession.dispose();
		
		SortedMealsDTO retval = new SortedMealsDTO();
		for (Meal m: sorted.getSortedList()) {
			if (withAllergens.contains(m)) {
				retval.getSorted().add(new ResponseMealDTO(m, true));
			} else {
				retval.getSorted().add(new ResponseMealDTO(m, false));				
			}
		}
		return retval;
	}
	
	public SortedGroceriesDTO filterGroceries(FilterDTO filter) throws NotLoggedInException {
		UserDB current = (UserDB) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (current == null) throw new NotLoggedInException("You must login first. No logged in user found!");
		
		RegisteredUserDB rUser = registeredUserRepository.findByEmail(current.getEmail());
		if (rUser == null) throw new NotLoggedInException("Registered user must be logged in!");
		
		List<GroceryDB> allGroceries = groceryRepository.findAll();
		List<Grocery> groceries = allGroceries.stream().map(Grocery::new).collect(Collectors.toList());
		List<Grocery> withAllergens = new ArrayList<Grocery>();
		SortedGroceries sorted = new SortedGroceries();
		
		kieSession.getAgenda().getAgendaGroup("groceries").setFocus();
		kieSession.insert(filter);
		kieSession.insert(groceries);
		for (Grocery g: groceries) {
			kieSession.insert(g);
		}
		kieSession.insert(withAllergens);
		kieSession.insert(new RegisteredUser(rUser));
		kieSession.insert(sorted);
		kieSession.fireAllRules();
		kieSession.dispose();
		
		SortedGroceriesDTO retval = new SortedGroceriesDTO();
		for (Grocery g: sorted.getSortedList()) {
			if (withAllergens.contains(g)) {
				retval.getSorted().add(new ResponseGroceryDTO(g, true));
			}
			retval.getSorted().add(new ResponseGroceryDTO(g, false));
		}
		return retval;
	}

	public Object getAllGroceries() {
		List<GroceryDB> allGroceries = groceryRepository.findAll();
		List<Grocery> groceries = allGroceries.stream().map(Grocery::new).collect(Collectors.toList());
		List<ResponseGroceryDTO> retval = new ArrayList<ResponseGroceryDTO>();
		for (Grocery g: groceries) {
			retval.add(new ResponseGroceryDTO(g, false));
		}
		return retval;
	}
	
}

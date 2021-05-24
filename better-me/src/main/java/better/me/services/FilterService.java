package better.me.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import better.me.dto.FilterDTO;
import better.me.dto.ResponseMealDTO;
import better.me.dto.SortedMealsDTO;
import better.me.exceptions.NotLoggedInException;
import better.me.model.Meal;
import better.me.model.RegisteredUser;
import better.me.model.SortedMeals;
import better.me.modelDB.MealDB;
import better.me.modelDB.RegisteredUserDB;
import better.me.modelDB.UserDB;
import better.me.repositories.IMealRepository;
import better.me.repositories.IRegisteredUser;

public class FilterService {

	@Autowired
	private KieSession kieSession;

	@Autowired
	private IRegisteredUser registeredUserRepository;
	
	@Autowired
	private IMealRepository mealRepository;

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
			}
			retval.getSorted().add(new ResponseMealDTO(m, false));
		}
		return retval;
	}
}

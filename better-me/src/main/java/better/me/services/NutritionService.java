package better.me.services;

import java.util.ArrayList;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import better.me.dto.RegisteredUserDTO;
import better.me.enums.Category;
import better.me.exceptions.NotLoggedInException;
import better.me.model.RegisteredUser;
import better.me.model.Week;
import better.me.modelDB.DayDB;
import better.me.modelDB.RegisteredUserDB;
import better.me.modelDB.UserDB;
import better.me.modelDB.WeekDB;
import better.me.repositories.IRegisteredUser;

@Service
public class NutritionService {

	@Autowired
	private IRegisteredUser registeredUserRepository;
	
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

	
}

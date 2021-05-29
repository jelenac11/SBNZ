package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import better.me.enums.Diet;
import better.me.model.ConcreteMeal;
import better.me.model.Day;
import better.me.model.Grade;
import better.me.model.Grocery;
import better.me.model.Ingredient;
import better.me.model.Meal;
import better.me.model.RegisteredUser;
import better.me.model.Week;
import better.me.util.MyLogger;

public class RecommendationTest {

	private final MyLogger myLogger = new MyLogger();

	protected final String ksessionName = "session";

	private KieSession kieSession;

	@Before
	public void setUp() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks
				.newKieContainer(ks.newReleaseId("sbnz.integracija", "drools-spring-kjar", "0.0.1-SNAPSHOT"));
		kieSession = kContainer.newKieSession("session");
		kieSession.getAgenda().getAgendaGroup("grocery-flags").setFocus();
		kieSession.setGlobal("myLogger", myLogger);
	}
	
//	@Test
//	public void groceryFactRule_allValuesGiven_shouldSetGroceryFacts() {
//		Grocery pepper = new Grocery(1L, "pepper", Diet.OMNIVORE, 150, 9, 7, 1);
//		Grocery banana = new Grocery(2L, "banana", Diet.OMNIVORE, 100, 10, 1, 1);
//		Grocery peanut = new Grocery(3L, "peanut", Diet.OMNIVORE, 120, 20, 4, 2);
//		Grocery milk = new Grocery(4L, "milk", Diet.OMNIVORE, 70, 11, 2, 5);
//		Grocery coconut = new Grocery(5L, "coconut", Diet.OMNIVORE, 35, 14, 2, 5);
//		Grocery egg = new Grocery(6L, "egg", Diet.OMNIVORE, 60, 22, 5, 3);
//		
//		kieSession.insert(pepper);
//		kieSession.insert(banana);
//		kieSession.insert(peanut);
//		kieSession.insert(milk);
//		kieSession.insert(coconut);
//		kieSession.insert(egg);
//		
//		List<Ingredient> ingredients1 = new ArrayList<Ingredient>();
//		ingredients1.add(new Ingredient(1L, 100, pepper));
//		ingredients1.add(new Ingredient(2L, 200, banana));
//		
//		List<Ingredient> ingredients2 = new ArrayList<Ingredient>();
//		ingredients2.add(new Ingredient(3L, 100, peanut));
//		ingredients2.add(new Ingredient(4L, 200, milk));
//		
//		List<Ingredient> ingredients3 = new ArrayList<Ingredient>();
//		ingredients3.add(new Ingredient(5L, 100, coconut));
//		ingredients3.add(new Ingredient(6L, 200, egg));
//		
//		Meal meal1 = new Meal(1L, "meal1", 350, 29, 9, 3, 10, "", ingredients1, null, 1);
//		Meal meal2 = new Meal(2L, "meal2", 400, 29, 9, 3, 10, "", ingredients2, null, 3);
//		Meal meal3 = new Meal(3L, "meal3", 450, 29, 9, 3, 10, "", ingredients3, null, 5);
//		
//		ConcreteMeal cm1 = new ConcreteMeal(1L, 100, false, meal1, 1L, null);
//		ConcreteMeal cm2 = new ConcreteMeal(2L, 100, false, meal2, 1L, null);
//		ConcreteMeal cm3 = new ConcreteMeal(3L, 100, false, meal3, 2L, null);
//		ConcreteMeal cm4 = new ConcreteMeal(4L, 100, false, meal1, 2L, null);
//		ConcreteMeal cm5 = new ConcreteMeal(5L, 100, false, meal2, 3L, null);
//		ConcreteMeal cm6 = new ConcreteMeal(6L, 100, false, meal3, 3L, null);
//		
//		ConcreteMeal cm7 = new ConcreteMeal(7L, 100, true, null, 1L, ingredients1);
//		ConcreteMeal cm8 = new ConcreteMeal(8L, 100, true, null, 1L, ingredients2);
//		ConcreteMeal cm9 = new ConcreteMeal(9L, 100, true, null, 2L, ingredients3);
//		ConcreteMeal cm10 = new ConcreteMeal(10L, 100, true, null, 2L, ingredients1);
//		ConcreteMeal cm11 = new ConcreteMeal(11L, 100, true, null, 3L, ingredients2);
//		ConcreteMeal cm12 = new ConcreteMeal(12L, 100, true, null, 3L, ingredients3);
//		
//		RegisteredUser r1 = new RegisteredUser();
//		
//		Grade g1 = new Grade(2, meal1, r1);
//		Grade g2 = new Grade(4, meal2, r1);
//		Grade g3 = new Grade(5, meal3, r1);
//		ArrayList<Grade> grades1 = new ArrayList<Grade>();
//		grades1.add(g1);
//		grades1.add(g2);
//		grades1.add(g3);
//		
//		ArrayList<Grade> gradesForMeal1 = new ArrayList<Grade>();
//		ArrayList<Grade> gradesForMeal2 = new ArrayList<Grade>();
//		ArrayList<Grade> gradesForMeal3 = new ArrayList<Grade>();
//		gradesForMeal1.add(g1);
//		gradesForMeal2.add(g2);
//		gradesForMeal3.add(g3);
//		meal1.setGrades(gradesForMeal1);
//		meal2.setGrades(gradesForMeal2);
//		meal3.setGrades(gradesForMeal3);
//		
//		Week w1 = new Week();
//		ArrayList<ConcreteMeal> cmeals1 = new ArrayList<ConcreteMeal>();
//		cmeals1.add(cm1);
//		cmeals1.add(cm2);
//		cmeals1.add(cm7);
//		cmeals1.add(cm8);
//		ArrayList<ConcreteMeal> cmeals2 = new ArrayList<ConcreteMeal>();
//		cmeals2.add(cm3);
//		cmeals2.add(cm4);
//		cmeals2.add(cm9);
//		cmeals2.add(cm10);
//		w1.getDays().set(0, new Day(1L, true, 2000, 200, 150, 100, false, cmeals1));
//		w1.getDays().set(1, new Day(2L, true, 2000, 200, 150, 100, false, cmeals2));
//		ArrayList<Week> weeks1 = new ArrayList<Week>();
//		weeks1.add(w1);
//		
//		r1.setGrades(grades1);		
//		r1.setWeeks(weeks1);
//		r1.setAgeCategory("ADULT");
//		r1.setDiet("OMNIVORE");
//		
//		kieSession.insert(r1);
//    	
//        int firedRules = kieSession.fireAllRules();
//        assertEquals(6, firedRules);
//       
//    	kieSession.dispose();
//	}
	
	@Test
	public void mealRecommendationRule_allValuesGiven_shouldRecommendMeals() {
		Grocery pepper = new Grocery(1L, "pepper", Diet.OMNIVORE, 150, 9, 7, 1);
		Grocery banana = new Grocery(2L, "banana", Diet.OMNIVORE, 100, 10, 1, 1);
		Grocery peanut = new Grocery(3L, "peanut", Diet.OMNIVORE, 120, 20, 4, 2);
		Grocery milk = new Grocery(4L, "milk", Diet.OMNIVORE, 70, 11, 2, 5);
		Grocery coconut = new Grocery(5L, "coconut", Diet.OMNIVORE, 35, 14, 2, 5);
		Grocery egg = new Grocery(6L, "egg", Diet.OMNIVORE, 60, 22, 5, 3);
		
		kieSession.insert(pepper);
		kieSession.insert(banana);
		kieSession.insert(peanut);
		kieSession.insert(milk);
		kieSession.insert(coconut);
		kieSession.insert(egg);
		
		List<Ingredient> ingredients1 = new ArrayList<Ingredient>();
		ingredients1.add(new Ingredient(1L, 100, pepper));
		ingredients1.add(new Ingredient(2L, 200, banana));
		
		List<Ingredient> ingredients2 = new ArrayList<Ingredient>();
		ingredients2.add(new Ingredient(3L, 100, peanut));
		ingredients2.add(new Ingredient(4L, 200, milk));
		
		List<Ingredient> ingredients3 = new ArrayList<Ingredient>();
		ingredients3.add(new Ingredient(5L, 100, coconut));
		ingredients3.add(new Ingredient(6L, 200, egg));
		
		Meal meal1 = new Meal(1L, "meal1", 350, 29, 9, 3, 10, "", ingredients1, null, 1);
		Meal meal2 = new Meal(2L, "meal2", 400, 29, 9, 3, 10, "", ingredients2, null, 3);
		Meal meal3 = new Meal(3L, "meal3", 450, 29, 9, 3, 10, "", ingredients3, null, 5);
		Meal meal4 = new Meal(4L, "meal4", 450, 29, 9, 3, 10, "", ingredients2, null, 4.4);
		Meal meal5 = new Meal(5L, "meal5", 450, 29, 9, 3, 10, "", ingredients3, null, 4.5);
		Meal meal6 = new Meal(6L, "meal6", 450, 29, 9, 3, 10, "", ingredients3, null, 4.6);
		//Meal meal7 = new Meal(3L, "meal7", 450, 29, 9, 3, 10, "", ingredients3, null, 4.7);
		//Meal meal8 = new Meal(3L, "meal8", 450, 29, 9, 3, 10, "", ingredients3, null, 4.8);
		List<Meal> allMeals = new ArrayList<Meal>();
		allMeals.add(meal1);
		allMeals.add(meal2);
		allMeals.add(meal3);
		allMeals.add(meal4);
		allMeals.add(meal5);
		allMeals.add(meal6);
		//allMeals.add(meal7);
		//allMeals.add(meal8);
		
		ConcreteMeal cm1 = new ConcreteMeal(1L, 100, false, meal1, 1L, null);
		ConcreteMeal cm2 = new ConcreteMeal(2L, 100, false, meal2, 1L, null);
		ConcreteMeal cm3 = new ConcreteMeal(3L, 100, false, meal3, 2L, null);
		ConcreteMeal cm4 = new ConcreteMeal(4L, 100, false, meal1, 2L, null);
		ConcreteMeal cm5 = new ConcreteMeal(5L, 100, false, meal2, 3L, null);
		ConcreteMeal cm6 = new ConcreteMeal(6L, 100, false, meal3, 3L, null);
		
		ConcreteMeal cm7 = new ConcreteMeal(7L, 100, true, null, 1L, ingredients1);
		ConcreteMeal cm8 = new ConcreteMeal(8L, 100, true, null, 1L, ingredients2);
		ConcreteMeal cm9 = new ConcreteMeal(9L, 100, true, null, 2L, ingredients3);
		ConcreteMeal cm10 = new ConcreteMeal(10L, 100, true, null, 2L, ingredients1);
		ConcreteMeal cm11 = new ConcreteMeal(11L, 100, true, null, 3L, ingredients2);
		ConcreteMeal cm12 = new ConcreteMeal(12L, 100, true, null, 3L, ingredients3);
		
		RegisteredUser r1 = new RegisteredUser();
		
		Grade g1 = new Grade(2, meal1, r1);
		Grade g2 = new Grade(4, meal2, r1);
		Grade g3 = new Grade(2, meal3, r1);
		ArrayList<Grade> grades1 = new ArrayList<Grade>();
		grades1.add(g1);
		grades1.add(g2);
		grades1.add(g3);
		
		ArrayList<Grade> gradesForMeal1 = new ArrayList<Grade>();
		ArrayList<Grade> gradesForMeal2 = new ArrayList<Grade>();
		ArrayList<Grade> gradesForMeal3 = new ArrayList<Grade>();
		gradesForMeal1.add(g1);
		gradesForMeal2.add(g2);
		gradesForMeal3.add(g3);
		meal1.setGrades(gradesForMeal1);
		meal2.setGrades(gradesForMeal2);
		meal3.setGrades(gradesForMeal3);
		
		Week w1 = new Week();
		ArrayList<ConcreteMeal> cmeals1 = new ArrayList<ConcreteMeal>();
		cmeals1.add(cm1);
		cmeals1.add(cm2);
		cmeals1.add(cm7);
		cmeals1.add(cm8);
		ArrayList<ConcreteMeal> cmeals2 = new ArrayList<ConcreteMeal>();
		cmeals2.add(cm3);
		cmeals2.add(cm4);
		cmeals2.add(cm9);
		cmeals2.add(cm10);
		w1.getDays().set(0, new Day(1L, true, 2000, 200, 150, 100, false, cmeals1));
		w1.getDays().set(1, new Day(2L, true, 2000, 200, 150, 100, false, cmeals2));
		ArrayList<Week> weeks1 = new ArrayList<Week>();
		weeks1.add(w1);
		
		r1.setGrades(grades1);		
		r1.setWeeks(weeks1);
		r1.setAgeCategory("ADULT");
		r1.setDiet("OMNIVORE");
		
		kieSession.insert(r1);
		kieSession.insert(allMeals);
    	
        int firedRules = kieSession.fireAllRules();
        kieSession.getAgenda().getAgendaGroup("meal-recommendation").setFocus();
        int firedRules2 = kieSession.fireAllRules();
        
//        @SuppressWarnings("unchecked")
//		Collection<AdminAlarm> raisedAlarms = (Collection<AdminAlarm>) kieSession.getObjects(new ClassObjectFilter(AdminAlarm.class));
//        for (AdminAlarm alarm: raisedAlarms) {
//            save(alarm);
//        }
        assertEquals(6, firedRules);
        assertEquals(7, firedRules2);
       
    	kieSession.dispose();
	}
	
}

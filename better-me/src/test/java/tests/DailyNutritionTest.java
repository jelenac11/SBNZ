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
import better.me.model.DailyNutrition;
import better.me.model.Day;
import better.me.model.Grocery;
import better.me.model.Ingredient;
import better.me.model.Meal;
import better.me.model.Notification;
import better.me.model.RegisteredUser;
import better.me.model.Week;
import better.me.util.MyLogger;

public class DailyNutritionTest {

	private final MyLogger myLogger = new MyLogger();
	    
    protected final String ksessionName = "session";
    
    private KieSession kieSession;
    
    @Before
	public void setUp() {
    	KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("sbnz.integracija", "drools-spring-kjar", "0.0.1-SNAPSHOT"));
        kieSession = kContainer.newKieSession("session");
		kieSession.getAgenda().getAgendaGroup("daily-nutrition").setFocus();
		kieSession.setGlobal("myLogger", myLogger);
		
		Week week = new Week();
    	week.setId(1L);
    	week.getDays().get(0).setSubmitted(true);
    	week.getDays().get(1).setSubmitted(true);
    	week.setGoalCalories(1500);
    	RegisteredUser user = new RegisteredUser();
    	user.setId(1L);
    	
		kieSession.insert(week);
		kieSession.insert(user);
	}
    
    @Test
    public void numberOfSubmittedDaysRule_twoSubmittedDaysGiven_shouldReturnTwo() {
    	DailyNutrition nutrition = new DailyNutrition();
    	kieSession.insert(nutrition);
        int firedRules = kieSession.fireAllRules();
        assertEquals(1, firedRules);
        assertEquals(2, (int) nutrition.getDay());
    }
    
    @Test
    public void calculateDailyNutrition_allMealsGiven_shouldReturnDailyNutrition() {
    	DailyNutrition nutrition = new DailyNutrition();
    	List<ConcreteMeal> concreteMeals = new ArrayList<ConcreteMeal>();
    	Grocery pepper = new Grocery(1L, "pepper", Diet.OMNIVORE, 150, 9, 7, 1);
    	Grocery banana = new Grocery(2L, "banana", Diet.OMNIVORE, 100, 10, 1, 1);
    	List<Ingredient> ingredients = new ArrayList<Ingredient>();
    	ingredients.add(new Ingredient(1L, 100, pepper));
    	ingredients.add(new Ingredient(2L, 200, banana));
    	Meal meal = new Meal(1L, "meal1", 350, 29, 9, 3, 10, "", ingredients, null, 0);
    	concreteMeals.add(new ConcreteMeal(1L, 200, false, meal, 1L, null));
    	concreteMeals.add(new ConcreteMeal(2L, 0, true, null, 1L, ingredients));
    	Day day = new Day(1L, false, 0, 0, 0, 0, false, concreteMeals);
    	
		kieSession.insert(day);
		kieSession.insert(nutrition);
		
		int firedRules = kieSession.fireAllRules();
        assertEquals(5, firedRules);
        assertEquals(1050, nutrition.getCalories(), 0.0);
        assertEquals(87, nutrition.getCarbs(), 0.0);
        assertEquals(27, nutrition.getProteins(), 0.0);
        assertEquals(9, nutrition.getFats(), 0.0);
    }
    
    @Test
    public void calculateDailyNutrition_customMealGiven_shouldReturnDailyNutrition() {
    	DailyNutrition nutrition = new DailyNutrition();
    	List<ConcreteMeal> concreteMeals = new ArrayList<ConcreteMeal>();
    	Grocery pepper = new Grocery(1L, "pepper", Diet.OMNIVORE, 150, 9, 7, 1);
    	Grocery banana = new Grocery(2L, "banana", Diet.OMNIVORE, 100, 10, 1, 1);
    	List<Ingredient> ingredients = new ArrayList<Ingredient>();
    	ingredients.add(new Ingredient(1L, 100, pepper));
    	ingredients.add(new Ingredient(2L, 200, banana));
    	concreteMeals.add(new ConcreteMeal(2L, 0, true, null, 1L, ingredients));
    	Day day = new Day(1L, false, 0, 0, 0, 0, false, concreteMeals);
    	
		kieSession.insert(day);
		kieSession.insert(nutrition);
		
		int firedRules = kieSession.fireAllRules();
        assertEquals(5, firedRules);
        assertEquals(350, nutrition.getCalories(), 0.0);
        assertEquals(29, nutrition.getCarbs(), 0.0);
        assertEquals(9, nutrition.getProteins(), 0.0);
        assertEquals(3, nutrition.getFats(), 0.0);
    }
    
    @Test
    public void calculateDailyNutrition_notCustomMealGiven_shouldReturnDailyNutrition() {
    	DailyNutrition nutrition = new DailyNutrition();
    	List<ConcreteMeal> concreteMeals = new ArrayList<ConcreteMeal>();
    	Grocery pepper = new Grocery(1L, "pepper", Diet.OMNIVORE, 150, 9, 7, 1);
    	Grocery banana = new Grocery(2L, "banana", Diet.OMNIVORE, 100, 10, 1, 1);
    	List<Ingredient> ingredients = new ArrayList<Ingredient>();
    	ingredients.add(new Ingredient(1L, 100, pepper));
    	ingredients.add(new Ingredient(2L, 200, banana));
    	Meal meal = new Meal(1L, "meal1", 350, 29, 9, 3, 10, "", ingredients, null, 0);
    	concreteMeals.add(new ConcreteMeal(1L, 200, false, meal, 1L, null));
    	Day day = new Day(1L, false, 0, 0, 0, 0, false, concreteMeals);
    	
		kieSession.insert(day);
		kieSession.insert(nutrition);
		
		int firedRules = kieSession.fireAllRules();
        assertEquals(5, firedRules);
        assertEquals(700, nutrition.getCalories(), 0.0);
        assertEquals(58, nutrition.getCarbs(), 0.0);
        assertEquals(18, nutrition.getProteins(), 0.0);
        assertEquals(6, nutrition.getFats(), 0.0);
    }
    
    @Test
    public void sendNotificationWarning_goalCaloriesExceeded_shouldSendNotification() {
    	DailyNutrition nutrition = new DailyNutrition();
    	List<ConcreteMeal> concreteMeals = new ArrayList<ConcreteMeal>();
    	Grocery pepper = new Grocery(1L, "pepper", Diet.OMNIVORE, 150, 9, 7, 1);
    	Grocery banana = new Grocery(2L, "banana", Diet.OMNIVORE, 100, 10, 1, 1);
    	List<Ingredient> ingredients = new ArrayList<Ingredient>();
    	ingredients.add(new Ingredient(1L, 100, pepper));
    	ingredients.add(new Ingredient(2L, 200, banana));
    	Meal meal = new Meal(1L, "meal1", 350, 29, 9, 3, 10, "", ingredients, null, 0);
    	concreteMeals.add(new ConcreteMeal(1L, 600, false, meal, 1L, null));
    	Day day = new Day(1L, false, 0, 0, 0, 0, false, concreteMeals);
    	Notification n = new Notification();
    	
		kieSession.insert(day);
		kieSession.insert(nutrition);
		kieSession.insert(n);

		int firedRules = kieSession.fireAllRules();
        assertEquals(6, firedRules);
        assertEquals(2100, nutrition.getCalories(), 0.0);
        assertEquals(174, nutrition.getCarbs(), 0.0);
        assertEquals(54, nutrition.getProteins(), 0.0);
        assertEquals(18, nutrition.getFats(), 0.0);
        assertEquals("You exceeded goal calories for today!", n.getMessage());
    }
    
    @Test
    public void sendNotificationOnCaloriesLeft_goalCaloriesNotExceeded_shouldSendNotification() {
    	DailyNutrition nutrition = new DailyNutrition();
    	List<ConcreteMeal> concreteMeals = new ArrayList<ConcreteMeal>();
    	Grocery pepper = new Grocery(1L, "pepper", Diet.OMNIVORE, 150, 9, 7, 1);
    	Grocery banana = new Grocery(2L, "banana", Diet.OMNIVORE, 100, 10, 1, 1);
    	List<Ingredient> ingredients = new ArrayList<Ingredient>();
    	ingredients.add(new Ingredient(1L, 100, pepper));
    	ingredients.add(new Ingredient(2L, 200, banana));
    	Meal meal = new Meal(1L, "meal1", 350, 29, 9, 3, 10, "", ingredients, null, 0);
    	concreteMeals.add(new ConcreteMeal(1L, 200, false, meal, 1L, null));
    	Day day = new Day(1L, false, 0, 0, 0, 0, false, concreteMeals);
    	Notification n = new Notification();
    	
		kieSession.insert(day);
		kieSession.insert(nutrition);
		kieSession.insert(n);

		int firedRules = kieSession.fireAllRules();
        assertEquals(6, firedRules);
        assertEquals(700, nutrition.getCalories(), 0.0);
        assertEquals("Calories left: 800.0", n.getMessage());
    }
    
}

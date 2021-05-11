package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import better.me.facts.ConcreteMealFact;
import better.me.facts.DailyNutritionFact;
import better.me.facts.DayFact;
import better.me.facts.GroceryFact;
import better.me.facts.IngredientFact;
import better.me.facts.MealFact;
import better.me.facts.RegisteredUserFact;
import better.me.facts.WeekFact;
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
	}
    
    @Test
    public void numberOfSubmittedDaysRule_twoSubmittedDaysGiven_shouldReturnTwo() {
    	WeekFact week = new WeekFact();
    	week.setId(1L);
    	week.setUserId(1L);
    	week.getDays().get(0).setSubmitted(true);
    	week.getDays().get(1).setSubmitted(true);
    	RegisteredUserFact user = new RegisteredUserFact();
    	user.setId(1L);
    	DailyNutritionFact nutrition = new DailyNutritionFact();
    	
		kieSession.insert(week);
		kieSession.insert(nutrition);
		kieSession.insert(user);
		
        int firedRules = kieSession.fireAllRules();
        assertEquals(1, firedRules);
        assertEquals(2, (int) nutrition.getDay());
    }
    
    @Test
    public void calculateDailyNutrition_allMealsGiven_shouldReturnDailyNutrition() {
    	DailyNutritionFact nutrition = new DailyNutritionFact();
    	List<ConcreteMealFact> concreteMeals = new ArrayList<ConcreteMealFact>();
    	GroceryFact pepper = new GroceryFact(1L, "pepper", 150, 9, 7, 1);
    	GroceryFact banana = new GroceryFact(2L, "banana", 100, 10, 1, 1);
    	List<IngredientFact> ingredients = new ArrayList<IngredientFact>();
    	ingredients.add(new IngredientFact(1L, 100, pepper));
    	ingredients.add(new IngredientFact(2L, 200, banana));
    	MealFact meal = new MealFact(1L, "meal1", 350, 29, 9, 3, 10, "", ingredients);
    	concreteMeals.add(new ConcreteMealFact(1L, 200, false, meal, 1L, null));
    	concreteMeals.add(new ConcreteMealFact(2L, 0, true, null, 1L, ingredients));
    	DayFact day = new DayFact(1L, false, 0, 0, 0, 0, false, concreteMeals);
    	
		kieSession.insert(day);
		kieSession.insert(nutrition);
		
		int firedRules = kieSession.fireAllRules();
        assertEquals(4, firedRules);
        assertEquals(1050, nutrition.getCalories(), 0.0);
        assertEquals(87, nutrition.getCarbs(), 0.0);
        assertEquals(27, nutrition.getProteins(), 0.0);
        assertEquals(9, nutrition.getFats(), 0.0);
    }
    
    @Test
    public void calculateDailyNutrition_customMealGiven_shouldReturnDailyNutrition() {
    	DailyNutritionFact nutrition = new DailyNutritionFact();
    	List<ConcreteMealFact> concreteMeals = new ArrayList<ConcreteMealFact>();
    	GroceryFact pepper = new GroceryFact(1L, "pepper", 150, 9, 7, 1);
    	GroceryFact banana = new GroceryFact(2L, "banana", 100, 10, 1, 1);
    	List<IngredientFact> ingredients = new ArrayList<IngredientFact>();
    	ingredients.add(new IngredientFact(1L, 100, pepper));
    	ingredients.add(new IngredientFact(2L, 200, banana));
    	concreteMeals.add(new ConcreteMealFact(2L, 0, true, null, 1L, ingredients));
    	DayFact day = new DayFact(1L, false, 0, 0, 0, 0, false, concreteMeals);
    	
		kieSession.insert(day);
		kieSession.insert(nutrition);
		
		int firedRules = kieSession.fireAllRules();
        assertEquals(4, firedRules);
        assertEquals(350, nutrition.getCalories(), 0.0);
        assertEquals(29, nutrition.getCarbs(), 0.0);
        assertEquals(9, nutrition.getProteins(), 0.0);
        assertEquals(3, nutrition.getFats(), 0.0);
    }
    
    @Test
    public void calculateDailyNutrition_notCustomMealGiven_shouldReturnDailyNutrition() {
    	DailyNutritionFact nutrition = new DailyNutritionFact();
    	List<ConcreteMealFact> concreteMeals = new ArrayList<ConcreteMealFact>();
    	GroceryFact pepper = new GroceryFact(1L, "pepper", 150, 9, 7, 1);
    	GroceryFact banana = new GroceryFact(2L, "banana", 100, 10, 1, 1);
    	List<IngredientFact> ingredients = new ArrayList<IngredientFact>();
    	ingredients.add(new IngredientFact(1L, 100, pepper));
    	ingredients.add(new IngredientFact(2L, 200, banana));
    	MealFact meal = new MealFact(1L, "meal1", 350, 29, 9, 3, 10, "", ingredients);
    	concreteMeals.add(new ConcreteMealFact(1L, 200, false, meal, 1L, null));
    	DayFact day = new DayFact(1L, false, 0, 0, 0, 0, false, concreteMeals);
    	
		kieSession.insert(day);
		kieSession.insert(nutrition);
		
		int firedRules = kieSession.fireAllRules();
        assertEquals(4, firedRules);
        assertEquals(700, nutrition.getCalories(), 0.0);
        assertEquals(58, nutrition.getCarbs(), 0.0);
        assertEquals(18, nutrition.getProteins(), 0.0);
        assertEquals(6, nutrition.getFats(), 0.0);
    }
    
}

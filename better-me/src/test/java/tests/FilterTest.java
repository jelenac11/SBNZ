package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import better.me.dto.FilterDTO;
import better.me.enums.Diet;
import better.me.model.Allergen;
import better.me.model.Grocery;
import better.me.model.Ingredient;
import better.me.model.Meal;
import better.me.model.RegisteredUser;
import better.me.model.SortedMeals;
import better.me.util.MyLogger;

public class FilterTest {

	private final MyLogger myLogger = new MyLogger();
    
    protected final String ksessionName = "session";
    
    private KieSession kieSession;
    
    private ArrayList<Meal> allMeals;
    
    @Before
    @BeforeEach
	public void setUp() {
    	KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("sbnz.integracija", "drools-spring-kjar", "0.0.1-SNAPSHOT"));
        kieSession = kContainer.newKieSession("session");
		kieSession.getAgenda().getAgendaGroup("filter").setFocus();
		kieSession.setGlobal("myLogger", myLogger);
		
		Grocery pepper = new Grocery(1L, "pepper", Diet.VEGAN, 150, 9, 7, 1);
    	Grocery banana = new Grocery(2L, "banana", Diet.VEGAN, 100, 10, 1, 1);
    	List<Ingredient> ingredients = new ArrayList<Ingredient>();
    	ingredients.add(new Ingredient(1L, 100, pepper));
    	ingredients.add(new Ingredient(2L, 200, banana));
    	Meal meal1 = new Meal(1L, "meal1", 350, 29, 9, 3, 10, "", ingredients, null, 0);
    	
    	Grocery peanut = new Grocery(3L, "peanut", Diet.OMNIVORE, 100, 10, 10, 10);
    	Grocery pineapple = new Grocery(4L, "pineapple", Diet.VEGAN, 100, 10, 1, 1);
    	List<Ingredient> ingredients2 = new ArrayList<Ingredient>();
    	ingredients2.add(new Ingredient(1L, 100, peanut));
    	ingredients2.add(new Ingredient(2L, 100, pineapple));
    	Meal meal2 = new Meal(2L, "meal2", 200, 20, 11, 11, 40, "", ingredients2, null, 0);
    	
    	Grocery milk = new Grocery(5L, "milk", Diet.OMNIVORE, 100, 10, 10, 10);
    	Grocery apple = new Grocery(6L, "apple", Diet.VEGETARIAN, 100, 10, 1, 1);
    	List<Ingredient> ingredients3 = new ArrayList<Ingredient>();
    	ingredients3.add(new Ingredient(1L, 100, milk));
    	ingredients3.add(new Ingredient(2L, 200, apple));
    	Meal meal3 = new Meal(3L, "meal3", 300, 30, 12, 12, 20, "", ingredients3, null, 0);
    	
    	allMeals = new ArrayList<Meal>();
    	allMeals.add(meal1);
    	allMeals.add(meal2);
    	allMeals.add(meal3);
    	
    	kieSession.insert(allMeals);
    	kieSession.insert(meal1);
    	kieSession.insert(meal2);
    	kieSession.insert(meal3);
	}
    
 
	@Test
    public void filterMealByAllergensRule_NoAllergensGiven_shouldReturnAllMeals() {
    	RegisteredUser user = new RegisteredUser();
    	user.setId(1L);
    	user.setFirstName("Jelena");
    	user.setLastName("Cupac");
    	user.setDiet("OMNIVORE");
    	user.setAllergens(null);
    	List<Meal> meals = new ArrayList<Meal>();
    	
    	kieSession.insert(meals);
    	kieSession.insert(user);
    	
        int firedRules = kieSession.fireAllRules();
        assertEquals(1, firedRules);
       
    	assertEquals(0, meals.size());
    	kieSession.dispose();
    }
    
    @Test
    public void filterMealByAllergensRule_OneAllergenGiven_shouldNotReturnAllMeals()  {
    	RegisteredUser user = new RegisteredUser();
    	user.setId(1L);
    	user.setFirstName("Jelena");
    	user.setLastName("Cupac");
    	user.setDiet("OMNIVORE");
    	ArrayList<Allergen> a = new ArrayList<Allergen>();
    	a.add(new Allergen(1L, "peanut"));
    	a.add(new Allergen(2L, "apple"));
    	user.setAllergens(a);
    	List<Meal> meals = new ArrayList<Meal>();
    	
    	kieSession.insert(meals);
    	kieSession.insert(user);
    	
        int firedRules = kieSession.fireAllRules();
        assertEquals(2, firedRules);
       
    	assertEquals(2, meals.size());
    	
        assertEquals("meal2", meals.get(0).getName());
        assertEquals("meal3", meals.get(1).getName());
        kieSession.dispose();
    }
    
    @ParameterizedTest
	@CsvSource({ 
		"'', 0, 0, 3, 3, false, meal2, meal3, meal1", 
		"'', 5, 8, 1, 0, false, , ,", 
		"'', 10, 20, 3, 2, false, meal3, meal1,", 
		"meal, 10, 20, 4, 2, true, meal1, meal3,",
		"meal, 0, 0, 4, 3, true, meal1, meal3, meal2", 
		"meal, 5, 8, 2, 0, false, , ,",
		"jelena, 10, 20, 2, 0, false, , ,",
	})
    public void filterMealsRule(String name, int from, int to, int rules, int size, boolean descending, String meal1, String meal2, String meal3) {
    	FilterDTO filter = new FilterDTO(name, from, to, descending);
    	SortedMeals sorted = new SortedMeals();
    	
    	kieSession.insert(sorted);
    	kieSession.insert(filter);
    	
        int firedRules = kieSession.fireAllRules();
        kieSession.dispose();
        assertEquals(rules, firedRules);
        assertEquals(size, sorted.getSortedList().size());
        if (sorted.getSortedList().size() > 0) {
        	
        	 if (meal1 != null) {
             	assertEquals(meal1, sorted.getSortedList().get(0).getName());
             }
             if (meal2 != null) {
             	assertEquals(meal2, sorted.getSortedList().get(1).getName());
             }
             if (meal3 != null) {
             	assertEquals(meal3, sorted.getSortedList().get(2).getName());
             }
        }
    }
    
    @ParameterizedTest
	@CsvSource({ 
		"VEGAN, 1, 1", 
		"VEGETARIAN, 1, 1", 
		"OMNIVORE, 1, 3", 
	})
    public void filterMealsByDietRule(String diet, int rules, int size) {
    	RegisteredUser user = new RegisteredUser();
    	user.setId(1L);
    	user.setFirstName("Jelena");
    	user.setLastName("Cupac");
    	user.setDiet(diet);
    	user.setAllergens(null);
    	
    	kieSession.insert(user);
    	
        int firedRules = kieSession.fireAllRules();
        kieSession.dispose();
        assertEquals(rules, firedRules);
        assertEquals(size, allMeals.size());
     
    }
    
 
	@Test
    public void testAll_NoAlergiesGiven() {
    	RegisteredUser user = new RegisteredUser();
    	user.setId(1L);
    	user.setFirstName("Jelena");
    	user.setLastName("Cupac");
    	user.setDiet("VEGETARIAN");
    	user.setAllergens(null);
    	SortedMeals sorted = new SortedMeals();
    	
    	kieSession.insert(sorted);
    	FilterDTO filter = new FilterDTO("", 10, 60, true);
    	kieSession.insert(user);
    	kieSession.insert(filter);
    	List<Meal> meals = new ArrayList<Meal>();
    	
    	kieSession.insert(meals);
    	int firedRules = kieSession.fireAllRules();
    	
        assertEquals(4, firedRules);
        assertEquals(0, meals.size());
        assertEquals(1, allMeals.size());
        assertEquals(1, sorted.getSortedList().size());
        assertEquals("meal1", sorted.getSortedList().get(0).getName());
        kieSession.dispose();
    }
    
 
	@Test
    public void testAll_AlergiesGiven(){
    	RegisteredUser user = new RegisteredUser();
    	user.setId(1L);
    	user.setFirstName("Jelena");
    	user.setLastName("Cupac");
    	user.setDiet("OMNIVORE");
    	ArrayList<Allergen> a = new ArrayList<Allergen>();
    	a.add(new Allergen(1L, "peanut"));
    	user.setAllergens(a);
    	FilterDTO filter = new FilterDTO("meal", 10, 60, false);
    	SortedMeals sorted = new SortedMeals();
    	List<Meal> meals = new ArrayList<Meal>();
    	
    	kieSession.insert(meals);
    	kieSession.insert(sorted);
    	kieSession.insert(user);
    	kieSession.insert(filter);
    	
    	int firedRules = kieSession.fireAllRules();
        kieSession.dispose();
        assertEquals(9, firedRules);
        assertEquals(1, meals.size());
        assertEquals(3, allMeals.size());
        assertEquals("meal2", sorted.getSortedList().get(0).getName());
        assertEquals("meal3", sorted.getSortedList().get(1).getName());
        assertEquals("meal1", sorted.getSortedList().get(2).getName());
     
    }
}

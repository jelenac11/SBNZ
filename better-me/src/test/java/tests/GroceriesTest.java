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
import better.me.model.RegisteredUser;
import better.me.model.SortedGroceries;
import better.me.util.MyLogger;

public class GroceriesTest {

	private final MyLogger myLogger = new MyLogger();
    
    protected final String ksessionName = "session";
    
    private KieSession kieSession;
    
    private ArrayList<Grocery> allGroceries;
    
    @Before
    @BeforeEach
	public void setUp() {
    	KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("sbnz.integracija", "drools-spring-kjar", "0.0.1-SNAPSHOT"));
        kieSession = kContainer.newKieSession("session");
		kieSession.getAgenda().getAgendaGroup("groceries").setFocus();
		kieSession.setGlobal("myLogger", myLogger);
		
		Grocery pepper = new Grocery(1L, "pepper", Diet.VEGAN, 150, 9, 7, 1);
    	Grocery banana = new Grocery(2L, "banana", Diet.VEGAN, 140, 10, 1, 1);
    	Grocery peanut = new Grocery(3L, "peanut", Diet.OMNIVORE, 130, 10, 10, 10);
    	Grocery pineapple = new Grocery(4L, "pineapple", Diet.VEGAN, 200, 10, 1, 1);
    	Grocery milk = new Grocery(5L, "milk", Diet.OMNIVORE, 90, 10, 10, 10);
    	Grocery apple = new Grocery(6L, "apple", Diet.VEGETARIAN, 80, 10, 1, 1);
    	allGroceries = new ArrayList<Grocery>();
    	allGroceries.add(pepper);
    	allGroceries.add(banana);
    	allGroceries.add(peanut);
    	allGroceries.add(pineapple);
    	allGroceries.add(milk);
    	allGroceries.add(apple);
    	
    	kieSession.insert(allGroceries);
    	kieSession.insert(pepper);
    	kieSession.insert(banana);
    	kieSession.insert(peanut);
    	kieSession.insert(pineapple);
    	kieSession.insert(milk);
    	kieSession.insert(apple);
	}
 
	@Test
    public void filterGroceriesByAllergensRule_NoAllergensGiven_shouldReturnAllGroceries() {
    	RegisteredUser user = new RegisteredUser();
    	user.setId(1L);
    	user.setFirstName("Jelena");
    	user.setLastName("Cupac");
    	user.setDiet("OMNIVORE");
    	user.setAllergens(null);
    	List<Grocery> groceries = new ArrayList<Grocery>();
    	
    	kieSession.insert(groceries);
    	kieSession.insert(user);
    	
        int firedRules = kieSession.fireAllRules();
        assertEquals(1, firedRules);
       
    	assertEquals(0, groceries.size());
    	kieSession.dispose();
    }
    
    @Test
    public void filterGroceriesByAllergensRule_OneAllergenGiven_shouldNotReturnAllGroceries()  {
    	RegisteredUser user = new RegisteredUser();
    	user.setId(1L);
    	user.setFirstName("Jelena");
    	user.setLastName("Cupac");
    	user.setDiet("OMNIVORE");
    	ArrayList<Allergen> a = new ArrayList<Allergen>();
    	a.add(new Allergen(1L, "peanut"));
    	a.add(new Allergen(2L, "apple"));
    	user.setAllergens(a);
    	List<Grocery> groceries = new ArrayList<Grocery>();
    	
    	kieSession.insert(groceries);
    	kieSession.insert(user);
    	
        int firedRules = kieSession.fireAllRules();
        assertEquals(2, firedRules);
       
    	assertEquals(2, groceries.size());
    	
        assertEquals("peanut", groceries.get(0).getName());
        assertEquals("apple", groceries.get(1).getName());
        kieSession.dispose();
    }
    
    @ParameterizedTest
	@CsvSource({ 
		"'', 6, 6, false, apple, milk, peanut",
		"'', 6, 6, true, pineapple, pepper, banana",
		"'peanut', 2, 1, true, peanut, ,",
		"'pineapple', 2, 1, false, pineapple, ,",
		"'aleksa', 1, 0, false, , ,",
	})
    public void filterGroceriesRule(String name, int rules, int size, boolean descending, String grocery1, String grocery2, String grocery3) {
    	FilterDTO filter = new FilterDTO(name, 0, 0, descending);
    	SortedGroceries sorted = new SortedGroceries();
    	
    	kieSession.insert(sorted);
    	kieSession.insert(filter);
    	
        int firedRules = kieSession.fireAllRules();
        kieSession.dispose();
        assertEquals(rules, firedRules);
        assertEquals(size, sorted.getSortedList().size());
        if (sorted.getSortedList().size() > 0) {
			if (grocery1 != null) {
				assertEquals(grocery1, sorted.getSortedList().get(0).getName());
			}
			if (grocery2 != null) {
				assertEquals(grocery2, sorted.getSortedList().get(1).getName());
			}
			if (grocery3 != null) {
				assertEquals(grocery3, sorted.getSortedList().get(2).getName());
			}
        }
    }
    
    @ParameterizedTest
	@CsvSource({ 
		"VEGAN, 1, 3", 
		"VEGETARIAN, 1, 4", 
		"OMNIVORE, 1, 6", 
	})
    public void filterGroceriesByDietRule(String diet, int rules, int size) {
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
        assertEquals(size, allGroceries.size());
    }
 
	@Test
    public void testAll_NoAlergiesGiven() {
    	RegisteredUser user = new RegisteredUser();
    	user.setId(1L);
    	user.setFirstName("Jelena");
    	user.setLastName("Cupac");
    	user.setDiet("VEGAN");
    	user.setAllergens(null);
    	SortedGroceries sorted = new SortedGroceries();
    	
    	kieSession.insert(sorted);
    	FilterDTO filter = new FilterDTO("", 0, 0, true);
    	kieSession.insert(user);
    	kieSession.insert(filter);
    	List<Grocery> groceriesWithAllergens = new ArrayList<Grocery>();
    	
    	kieSession.insert(groceriesWithAllergens);
    	int firedRules = kieSession.fireAllRules();
    	
        assertEquals(4, firedRules);
        assertEquals(0, groceriesWithAllergens.size());
        assertEquals(3, allGroceries.size());
        assertEquals(3, sorted.getSortedList().size());
        assertEquals("pineapple", sorted.getSortedList().get(0).getName());
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
    	FilterDTO filter = new FilterDTO("p", 0, 0, false);
    	SortedGroceries sorted = new SortedGroceries();
    	List<Grocery> groceriesWithAllergens = new ArrayList<Grocery>();
    	
    	kieSession.insert(groceriesWithAllergens);
    	kieSession.insert(sorted);
    	kieSession.insert(user);
    	kieSession.insert(filter);
    	
    	int firedRules = kieSession.fireAllRules();

    	kieSession.dispose();
        assertEquals(8, firedRules);
        assertEquals(1, groceriesWithAllergens.size());
        assertEquals("peanut", groceriesWithAllergens.get(0).getName());
        assertEquals(4, allGroceries.size());
        assertEquals(4, sorted.getSortedList().size());
        assertEquals("apple", sorted.getSortedList().get(0).getName());
        assertEquals("peanut", sorted.getSortedList().get(1).getName());
        assertEquals("pepper", sorted.getSortedList().get(2).getName());
        assertEquals("pineapple", sorted.getSortedList().get(3).getName());
     
    }
}

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
import better.me.model.Grocery;
import better.me.model.Ingredient;
import better.me.model.Meal;
import better.me.util.MyLogger;

public class NewMealTest {

private final MyLogger myLogger = new MyLogger();
    
    protected final String ksessionName = "session";
    
    private KieSession kieSession;
    
    @Before
	public void setUp() {
    	KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("sbnz.integracija", "drools-spring-kjar", "0.0.1-SNAPSHOT"));
        kieSession = kContainer.newKieSession("session");
		kieSession.getAgenda().getAgendaGroup("new-meal").setFocus();
		kieSession.setGlobal("myLogger", myLogger);
	}
    
    @Test
    public void newMealCreatedRules() {
    	Grocery pepper = new Grocery(1L, "pepper", Diet.VEGAN, 150, 9, 7, 1);
    	Grocery banana = new Grocery(2L, "banana", Diet.VEGAN, 100, 10, 1, 1);
    	List<Ingredient> ingredients = new ArrayList<Ingredient>();
    	ingredients.add(new Ingredient(1L, 100, pepper));
    	ingredients.add(new Ingredient(2L, 200, banana));
    	Meal meal = new Meal();
    	meal.setName("meal1");
    	meal.setDescription("Tasty new meal with bananas");
    	meal.setTime(20);
    	meal.setIngredients(ingredients);
    	
    	kieSession.insert(meal);
    	
        int firedRules = kieSession.fireAllRules();
        assertEquals(4, firedRules);
        assertEquals(350, meal.getCalories(), 0.0);
        assertEquals(29, meal.getCarbs(), 0.0);
        assertEquals(9, meal.getProteins(), 0.0);
        assertEquals(3, meal.getFats(), 0.0);
    	kieSession.dispose();
    }
}

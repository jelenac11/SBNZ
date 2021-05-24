package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import better.me.model.Day;
import better.me.model.RegisteredUser;
import better.me.model.Week;
import better.me.util.MyLogger;

public class ScoreTest {

	private final MyLogger myLogger = new MyLogger();
    
    protected final String ksessionName = "session";
    
    private KieSession kieSession;
    
    @Before
    @BeforeEach
	public void setUp() {
    	KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("sbnz.integracija", "drools-spring-kjar", "0.0.1-SNAPSHOT"));
        kieSession = kContainer.newKieSession("session");
		kieSession.getAgenda().getAgendaGroup("score").setFocus();
		kieSession.setGlobal("myLogger", myLogger);
	}
 
    @Test
    public void calculateScoreRule_NotAllDaysSubmitted_shouldNotCalculateScore() {
    	RegisteredUser user = new RegisteredUser();
    	user.setId(1L);
    	user.setFirstName("Aleksa");
    	user.setLastName("Goljovic");
    	user.setDiet("OMNIVORE");
    	user.setAllergens(null);
    	user.setPreviousCategory("BEGINNER");
    	user.setCategory("BEGINNER");
    	user.setScore(100);
    	
    	Week week = new Week();
    	week.setId(1L);
    	week.setGoalCalories(2000);
    	week.setGoalProteins(150);
    	week.setGoalCarbs(200);
    	week.setGoalFats(100);
    	
    	week.getDays().set(0, new Day(1L, true, 2000, 200, 150, 100, false, null));
    	week.getDays().set(1, new Day(2L, true, 2000, 200, 150, 100, false, null));
    	week.getDays().set(2, new Day(3L, true, 2000, 200, 150, 100, false, null));
    	week.getDays().set(3, new Day(4L, true, 2000, 200, 150, 100, false, null));
    	week.getDays().set(4, new Day(5L, true, 2000, 200, 150, 100, false, null));
    	week.getDays().set(5, new Day(6L, true, 2000, 200, 150, 100, false, null));
    	week.getDays().set(6, new Day(7L, false, 2000, 200, 150, 100, false, null));
    	
    	kieSession.insert(user);
    	kieSession.insert(week);
    	
        int firedRules = kieSession.fireAllRules();
        assertEquals(0, firedRules);
       
    	assertEquals(100, user.getScore());
    	kieSession.dispose();
    }
    
    @Test
    public void calculateScoreRule_AllDaysSubmitted_shouldCalculateScore() {
    	RegisteredUser user = new RegisteredUser();
    	user.setId(1L);
    	user.setFirstName("Aleksa");
    	user.setLastName("Goljovic");
    	user.setDiet("OMNIVORE");
    	user.setAllergens(null);
    	user.setPreviousCategory("BEGINNER");
    	user.setCategory("BEGINNER");
    	user.setScore(100);
    	
    	Week week = new Week();
    	week.setId(1L);
    	week.setGoalCalories(2000);
    	week.setGoalProteins(150);
    	week.setGoalCarbs(200);
    	week.setGoalFats(100);
    	
    	week.getDays().set(0, new Day(1L, true, 2000, 200, 150, 100, false, null));
    	week.getDays().set(1, new Day(2L, true, 2000, 200, 150, 100, false, null));
    	week.getDays().set(2, new Day(3L, true, 2000, 200, 150, 100, false, null));
    	week.getDays().set(3, new Day(4L, true, 2000, 200, 150, 100, false, null));
    	week.getDays().set(4, new Day(5L, true, 2000, 200, 150, 100, false, null));
    	week.getDays().set(5, new Day(6L, true, 2000, 200, 150, 100, false, null));
    	week.getDays().set(6, new Day(7L, true, 2000, 200, 150, 100, false, null));
    	
    	kieSession.insert(user);
    	kieSession.insert(week);
    	
        int firedRules = kieSession.fireAllRules();
        assertEquals(1, firedRules);
       
    	assertEquals(100 + 70 + 3*3*7, user.getScore());
    	kieSession.dispose();
    }
    
    @Test
    public void calculateScoreRule_AllDaysSubmittedNotRespectCalories_shouldCalculateScore() {
    	RegisteredUser user = new RegisteredUser();
    	user.setId(1L);
    	user.setFirstName("Aleksa");
    	user.setLastName("Goljovic");
    	user.setDiet("OMNIVORE");
    	user.setAllergens(null);
    	user.setPreviousCategory("BEGINNER");
    	user.setCategory("BEGINNER");
    	user.setScore(100);
    	
    	Week week = new Week();
    	week.setId(1L);
    	week.setGoalCalories(2000);
    	week.setGoalProteins(150);
    	week.setGoalCarbs(200);
    	week.setGoalFats(100);
    	
    	week.getDays().set(0, new Day(1L, true, 2201, 200, 150, 100, false, null));
    	week.getDays().set(1, new Day(2L, true, 1799, 200, 150, 100, false, null));
    	week.getDays().set(2, new Day(3L, true, 1000, 200, 150, 100, false, null));
    	week.getDays().set(3, new Day(4L, true, 3000, 200, 150, 100, false, null));
    	week.getDays().set(4, new Day(5L, true, 2200, 200, 150, 100, false, null));
    	week.getDays().set(5, new Day(6L, true, 1800, 200, 150, 100, false, null));
    	week.getDays().set(6, new Day(7L, true, 2000, 200, 150, 100, false, null));
    	
    	kieSession.insert(user);
    	kieSession.insert(week);
    	
        int firedRules = kieSession.fireAllRules();
        assertEquals(1, firedRules);
       
    	assertEquals(100 + 30 + 3*3*7, user.getScore());
    	kieSession.dispose();
    }
    
    @Test
    public void calculateScoreRule_AllDaysSubmittedNotRespectedMacros_shouldCalculateScore() {
    	RegisteredUser user = new RegisteredUser();
    	user.setId(1L);
    	user.setFirstName("Aleksa");
    	user.setLastName("Goljovic");
    	user.setDiet("OMNIVORE");
    	user.setAllergens(null);
    	user.setPreviousCategory("BEGINNER");
    	user.setCategory("BEGINNER");
    	user.setScore(100);
    	
    	Week week = new Week();
    	week.setId(1L);
    	week.setGoalCalories(2000);
    	week.setGoalProteins(150);
    	week.setGoalCarbs(200);
    	week.setGoalFats(100);
    	
    	week.getDays().set(0, new Day(1L, true, 2000, 221, 150, 111, false, null));
    	week.getDays().set(1, new Day(2L, true, 2000, 179, 150, 89, false, null));
    	week.getDays().set(2, new Day(3L, true, 2000, 300, 150, 150, false, null));
    	week.getDays().set(3, new Day(4L, true, 2000, 100, 150, 50, false, null));
    	week.getDays().set(4, new Day(5L, true, 2000, 220, 150, 110, false, null));
    	week.getDays().set(5, new Day(6L, true, 2000, 180, 150, 90, false, null));
    	week.getDays().set(6, new Day(7L, true, 2000, 200, 150, 100, false, null));
    	
    	kieSession.insert(user);
    	kieSession.insert(week);
    	
        int firedRules = kieSession.fireAllRules();
        assertEquals(1, firedRules);
       
    	assertEquals(100 + 70 + 3*13, user.getScore());
    	kieSession.dispose();
    }
    
}

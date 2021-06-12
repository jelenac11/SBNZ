package tests;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.drools.core.time.SessionPseudoClock;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

import better.me.events.LoginEvent;
import better.me.events.MealEatenEvent;
import better.me.events.MealRatedEvent;
import better.me.events.MidnightEvent;
import better.me.events.UserAteMealEvent;
import better.me.model.AdminReport;
import better.me.model.Day;
import better.me.model.Meal;
import better.me.model.RegisteredUser;
import better.me.model.User;
import better.me.model.Week;
import better.me.util.MyLogger;

public class CepTest {

	private final MyLogger myLogger = new MyLogger();
	
	@Test
	public void test_cepMidnightEventTest() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kc = ks.newKieClasspathContainer();
		KieSession ksession = kc.newKieSession("cepKsessionPseudoClock");
		ksession.getAgenda().getAgendaGroup("end-day").setFocus();
		SessionPseudoClock clock = ksession.getSessionClock();
		ksession.setGlobal("myLogger", myLogger);
		
		RegisteredUser user = new RegisteredUser();
    	user.setId(1L);
    	user.setUsername("Aleksa");
    	user.setFirstName("Aleksa");
    	user.setLastName("Goljovic");
    	user.setDiet("OMNIVORE");
    	user.setAllergens(null);
    	user.setPreviousCategory("BEGINNER");
    	user.setCategory("BEGINNER");
    	user.setScore(100);
    	
    	Week week = new Week();
    	week.setId(1L);
    	week.setGoal("LOSE_WEIGHT");
    	week.setGoalCalories(2000);
    	week.setGoalProteins(150);
    	week.setGoalCarbs(200);
    	week.setGoalFats(100);
    	
    	week.getDays().set(0, new Day(1L, false, 2000, 200, 150, 100, false, null));
    	week.getDays().set(1, new Day(2L, false, 2000, 200, 150, 100, false, null));
    	week.getDays().set(2, new Day(3L, false, 2000, 200, 150, 100, false, null));
    	week.getDays().set(3, new Day(4L, false, 2000, 200, 150, 100, false, null));
    	week.getDays().set(4, new Day(5L, false, 2000, 200, 150, 100, false, null));
    	week.getDays().set(5, new Day(6L, false, 2000, 200, 150, 100, false, null));
    	week.getDays().set(6, new Day(7L, false, 2000, 200, 150, 100, false, null));
    	user.getWeeks().add(week);
    	
    	ksession.insert(user);
    	ksession.insert(new MidnightEvent(user, 1));
    	ksession.getAgenda().getAgendaGroup("end-day").setFocus();
    	clock.advanceTime(1, TimeUnit.DAYS);
    	ksession.fireAllRules();
    	
    	ksession.insert(new MidnightEvent(user, 1));
    	ksession.getAgenda().getAgendaGroup("end-day").setFocus();
    	clock.advanceTime(1, TimeUnit.DAYS);
    	ksession.fireAllRules();
    	
    	ksession.insert(new MidnightEvent(user, 1));
    	ksession.getAgenda().getAgendaGroup("end-day").setFocus();
    	clock.advanceTime(1, TimeUnit.DAYS);
    	ksession.fireAllRules();
    	
    	assertEquals(true, week.getDays().get(0).isSubmitted());
    	assertEquals(true, week.getDays().get(1).isSubmitted());
    	assertEquals(true, week.getDays().get(2).isSubmitted());
    	assertEquals(false, week.getDays().get(3).isSubmitted());
    	assertEquals(false, week.getDays().get(4).isSubmitted());
    	assertEquals(false, week.getDays().get(5).isSubmitted());
    	assertEquals(false, week.getDays().get(6).isSubmitted());
    	assertEquals(1, user.getWeeks().size());
    	assertEquals(100, user.getScore());
    	assertEquals("BEGINNER", user.getCategory());
    	
    	ksession.insert(new MidnightEvent(user, 1));
    	ksession.getAgenda().getAgendaGroup("end-day").setFocus();
    	clock.advanceTime(1, TimeUnit.DAYS);
    	ksession.fireAllRules();
    	
    	ksession.insert(new MidnightEvent(user, 1));
    	ksession.getAgenda().getAgendaGroup("end-day").setFocus();
    	clock.advanceTime(1, TimeUnit.DAYS);
    	ksession.fireAllRules();
    	
    	ksession.insert(new MidnightEvent(user, 1));
    	ksession.getAgenda().getAgendaGroup("end-day").setFocus();
    	clock.advanceTime(1, TimeUnit.DAYS);
    	ksession.fireAllRules();
    	
    	ksession.insert(new MidnightEvent(user, 1));
    	ksession.getAgenda().getAgendaGroup("end-day").setFocus();
    	clock.advanceTime(1, TimeUnit.DAYS);
    	ksession.fireAllRules();
    	
    	assertEquals(true, week.getDays().get(0).isSubmitted());
    	assertEquals(true, week.getDays().get(1).isSubmitted());
    	assertEquals(true, week.getDays().get(2).isSubmitted());
    	assertEquals(true, week.getDays().get(3).isSubmitted());
    	assertEquals(true, week.getDays().get(4).isSubmitted());
    	assertEquals(true, week.getDays().get(5).isSubmitted());
    	assertEquals(true, week.getDays().get(6).isSubmitted());
    	assertEquals(2, user.getWeeks().size());
    	assertEquals(233, user.getScore());
    	assertEquals("INTERMEDIATE", user.getCategory());
    	assertEquals(true, user.getWeeks().get(1).isCheat());
    	
    	Week week2 = user.getWeeks().get(1);
    	week2.setId(2L);
    	week2.setGoal("LOSE_WEIGHT");
    	week2.setGoalCalories(2000);
    	week2.setGoalProteins(150);
    	week2.setGoalCarbs(200);
    	week2.setGoalFats(100);
    	
    	week2.getDays().set(0, new Day(1L, false, 1000, 100, 10, 0, false, null)); // lose kalorije
    	week2.getDays().set(1, new Day(2L, false, 1000, 100, 10, 0, false, null)); // lose kalorije
    	week2.getDays().set(2, new Day(3L, false, 1000, 100, 10, 0, false, null)); // lose kalorije
    	week2.getDays().set(3, new Day(4L, false, 2000, 100, 100, 100, false, null)); 
    	week2.getDays().set(4, new Day(5L, false, 2000, 100, 100, 200, false, null)); //losi makronutrijenti
    	week2.getDays().set(5, new Day(6L, false, 2000, 0, 50, 10, false, null)); // losi makro
    	week2.getDays().set(6, new Day(7L, false, 1000, 200, 0, 100, false, null));
    	
    	ksession.insert(user);
    	
    	ksession.insert(new MidnightEvent(user, 2));
    	ksession.getAgenda().getAgendaGroup("end-day").setFocus();
    	clock.advanceTime(1, TimeUnit.DAYS);
    	ksession.fireAllRules();
    	
    	ksession.insert(new MidnightEvent(user, 2));
    	ksession.getAgenda().getAgendaGroup("end-day").setFocus();
    	clock.advanceTime(1, TimeUnit.DAYS);
    	ksession.fireAllRules();
    	
    	assertEquals(true, week2.getDays().get(0).isSubmitted());
    	assertEquals(true, week2.getDays().get(1).isSubmitted());
    	assertEquals(false, week2.getDays().get(2).isSubmitted());
    	assertEquals(2, user.getWeeks().size());
    	assertEquals(233, user.getScore());
    	assertEquals("INTERMEDIATE", user.getCategory());
    	
    	ksession.insert(new MidnightEvent(user, 2));
    	ksession.getAgenda().getAgendaGroup("end-day").setFocus();
    	clock.advanceTime(1, TimeUnit.DAYS);
    	ksession.fireAllRules();
    	
    	assertEquals(true, week2.getDays().get(2).isSubmitted());
    	assertEquals(183, user.getScore());
    	
    	ksession.insert(new MidnightEvent(user, 2));
    	ksession.getAgenda().getAgendaGroup("end-day").setFocus();
    	clock.advanceTime(1, TimeUnit.DAYS);
    	ksession.fireAllRules();
    	
    	ksession.insert(new MidnightEvent(user, 2));
    	ksession.getAgenda().getAgendaGroup("end-day").setFocus();
    	clock.advanceTime(1, TimeUnit.DAYS);
    	ksession.fireAllRules();
    	
    	ksession.insert(new MidnightEvent(user, 2));
    	ksession.getAgenda().getAgendaGroup("end-day").setFocus();
    	clock.advanceTime(1, TimeUnit.DAYS);
    	ksession.fireAllRules();
    	
    	assertEquals(true, week2.getDays().get(3).isSubmitted());
    	assertEquals(true, week2.getDays().get(4).isSubmitted());
    	assertEquals(true, week2.getDays().get(5).isSubmitted());
    	assertEquals(133, user.getScore());
    	
    	ksession.insert(new MidnightEvent(user, 2));
    	ksession.getAgenda().getAgendaGroup("end-day").setFocus();
    	clock.advanceTime(1, TimeUnit.DAYS);
    	ksession.fireAllRules();
    	
    	assertEquals(true, week2.getDays().get(6).isSubmitted());
    	assertEquals(172, user.getScore());
    	assertEquals(3, user.getWeeks().size());
    	assertEquals("BEGINNER", user.getCategory());
    	assertEquals(false, user.getWeeks().get(2).isCheat());
    }

	/*
	 * Testira se kreiranje izvestaja koji prikazuje top 5 jela koja su najvise puta
	 * pojedena u poslednjih 30 dana
	 * 
	 */
	@Test
	public void test_cepReportRulesTestTop5EatenMeals() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kc = ks.newKieClasspathContainer();
		KieSession ksession = kc.newKieSession("cepKsessionPseudoClock");
		SessionPseudoClock clock = ksession.getSessionClock();
		ksession.setGlobal("myLogger", myLogger);

		Meal meal1 = new Meal();
		meal1.setName("Meal1");
		Meal meal2 = new Meal();
		meal2.setName("Meal2");
		Meal meal3 = new Meal();
		meal3.setName("Meal3");
		Meal meal4 = new Meal();
		meal4.setName("Meal4");
		Meal meal5 = new Meal();
		meal5.setName("Meal5");
		Meal meal6 = new Meal();
		meal6.setName("Meal6");
		Meal meal7 = new Meal();
		meal7.setName("Meal7");

		AdminReport r = new AdminReport();

		FactHandle reportFactHandle = ksession.insert(r);
		ksession.getAgenda().getAgendaGroup("admin-reports").setFocus();
		
		for (int i = 0; i < 2; i++) {
			ksession.insert(new MealEatenEvent(meal1));
		}
		
		for (int i = 0; i < 4; i++) {
			ksession.insert(new MealEatenEvent(meal2));
		}
		
		for (int i = 0; i < 3; i++) {
			ksession.insert(new MealEatenEvent(meal3));
		}

		for (int i = 0; i < 5; i++) {
			ksession.insert(new MealEatenEvent(meal4));
		}
		
		for (int i = 0; i < 6; i++) {
			ksession.insert(new MealEatenEvent(meal5));
		}
		ksession.insert(new MealEatenEvent(meal6));
		ksession.insert(new MealEatenEvent(meal7));

		ksession.fireAllRules();

		assertEquals(22, r.getEatings());
		assertEquals(5, r.getMostPopularMeals().size());
		assertEquals("Meal5", r.getMostPopularMeals().get(0).getMeal());
		assertEquals("Meal4", r.getMostPopularMeals().get(1).getMeal());
		assertEquals("Meal2", r.getMostPopularMeals().get(2).getMeal());
		assertEquals("Meal3", r.getMostPopularMeals().get(3).getMeal());
		assertEquals("Meal1", r.getMostPopularMeals().get(4).getMeal());

		clock.advanceTime(29, TimeUnit.DAYS);

		ksession.fireAllRules();

		assertEquals(22, r.getEatings());
		assertEquals(5, r.getMostPopularMeals().size());
		assertEquals("Meal5", r.getMostPopularMeals().get(0).getMeal());
		assertEquals("Meal4", r.getMostPopularMeals().get(1).getMeal());
		assertEquals("Meal2", r.getMostPopularMeals().get(2).getMeal());
		assertEquals("Meal3", r.getMostPopularMeals().get(3).getMeal());
		assertEquals("Meal1", r.getMostPopularMeals().get(4).getMeal());

		clock.advanceTime(1, TimeUnit.DAYS);

		AdminReport r2 = new AdminReport();
		ksession.insert(r2);
		ksession.delete(reportFactHandle);
		ksession.fireAllRules();

		assertEquals(0, r2.getEatings());
		assertEquals(0, r2.getMostPopularMeals().size());
	}

	/*
	 * Testira se kreiranje izvestaja koji prikazuje top 5 najbolje 
	 * ocenjenih jela u poslednjih 30 dana 
	 * 
	 */
	@Test
	public void test_cepReportRulesTestTop5RatedMeals() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kc = ks.newKieClasspathContainer();
		KieSession ksession = kc.newKieSession("cepKsessionPseudoClock");
		ksession.getAgenda().getAgendaGroup("admin-reports").setFocus();
		SessionPseudoClock clock = ksession.getSessionClock();
		ksession.setGlobal("myLogger", myLogger);

		Meal meal1 = new Meal();
		meal1.setName("Meal1");
		Meal meal2 = new Meal();
		meal2.setName("Meal2");
		Meal meal3 = new Meal();
		meal3.setName("Meal3");
		Meal meal4 = new Meal();
		meal4.setName("Meal4");
		Meal meal5 = new Meal();
		meal5.setName("Meal5");
		Meal meal6 = new Meal();
		meal6.setName("Meal6");
		Meal meal7 = new Meal();
		meal7.setName("Meal7");

		AdminReport r = new AdminReport();

		FactHandle reportFactHandle = ksession.insert(r);
		ksession.insert(new MealRatedEvent(meal1, 5));
		ksession.insert(new MealRatedEvent(meal1, 4));
		ksession.insert(new MealRatedEvent(meal2, 5));
		ksession.insert(new MealRatedEvent(meal2, 5));
		ksession.insert(new MealRatedEvent(meal2, 5));
		ksession.insert(new MealRatedEvent(meal2, 4));
		ksession.insert(new MealRatedEvent(meal3, 3));
		ksession.insert(new MealRatedEvent(meal3, 4));
		ksession.insert(new MealRatedEvent(meal3, 5));
		ksession.insert(new MealRatedEvent(meal4, 1));
		ksession.insert(new MealRatedEvent(meal4, 2));
		ksession.insert(new MealRatedEvent(meal4, 3));
		ksession.insert(new MealRatedEvent(meal4, 4));
		ksession.insert(new MealRatedEvent(meal4, 5));
		ksession.insert(new MealRatedEvent(meal5, 5));
		ksession.insert(new MealRatedEvent(meal6, 1));
		ksession.insert(new MealRatedEvent(meal7, 2));

		ksession.fireAllRules();

		assertEquals(5, r.getMostRatedMeals().size());
		assertEquals("Meal5", r.getMostRatedMeals().get(0).getMeal());
		assertEquals("Meal2", r.getMostRatedMeals().get(1).getMeal());
		assertEquals("Meal1", r.getMostRatedMeals().get(2).getMeal());
		assertEquals("Meal3", r.getMostRatedMeals().get(3).getMeal());
		assertEquals("Meal4", r.getMostRatedMeals().get(4).getMeal());

		clock.advanceTime(29, TimeUnit.DAYS);

		ksession.fireAllRules();

		assertEquals(5, r.getMostRatedMeals().size());
		assertEquals("Meal5", r.getMostRatedMeals().get(0).getMeal());
		assertEquals("Meal2", r.getMostRatedMeals().get(1).getMeal());
		assertEquals("Meal1", r.getMostRatedMeals().get(2).getMeal());
		assertEquals("Meal3", r.getMostRatedMeals().get(3).getMeal());
		assertEquals("Meal4", r.getMostRatedMeals().get(4).getMeal());

		clock.advanceTime(1, TimeUnit.DAYS);

		AdminReport r2 = new AdminReport();
		ksession.insert(r2);
		ksession.delete(reportFactHandle);
		ksession.fireAllRules();

		assertEquals(0, r2.getMostRatedMeals().size());
	}
	
	/*
	 * Korisnik je jeo 3 puta u poslednjih nekoliko sati,
	 * ne moze da jede narednih 6
	 * Nakon 6 sati unosi novo jelo i dozvoljeno mu je
	 * da jede
	 */
	@Test
	public void testUnsuccessfulEatingAfterThreeMeals() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kc = ks.newKieClasspathContainer();
		KieSession ksession = kc.newKieSession("cepKsessionPseudoClock");
		SessionPseudoClock clock = ksession.getSessionClock();
		ksession.setGlobal("myLogger", myLogger);
		
		RegisteredUser u = new RegisteredUser();
		u.setId(1L);

		ksession.insert(new UserAteMealEvent(u));
		ksession.insert(new UserAteMealEvent(u));
		ksession.insert(new UserAteMealEvent(u));
		ksession.fireAllRules();
		
		assertEquals(false, u.isAllowedToEat());
		
		clock.advanceTime(6, TimeUnit.HOURS);
		
		ksession.insert(new UserAteMealEvent(u));
		ksession.fireAllRules();
		assertEquals(true, u.isAllowedToEat());
	}
	

	/*
	 * Korisnik moze da jede jer ne postoje dogadjaji u radnoj memoriji
	 */
	@Test
	public void testSuccessfulEating() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kc = ks.newKieClasspathContainer();
		KieSession ksession = kc.newKieSession("cepKsessionPseudoClock");
		ksession.setGlobal("myLogger", myLogger);
		
		RegisteredUser u = new RegisteredUser();
		u.setId(1L);
		u.setAllowedToEat(false);
		ksession.insert(new UserAteMealEvent(u));
		ksession.fireAllRules();
		
		assertEquals(true, u.isAllowedToEat());
	}
	
	/*
	 * Testira se da nakon tri neuspesna logina u roku od 5 minuta,
	 * korisnik vise ne moze da se loguje i privremeno se blokira
	 * Napomena: korisniku je prethodno dozvoljeno logovanje
	 */
	@Test
	public void testUnsuccessfulLoginAfterThreeAttempts() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kc = ks.newKieClasspathContainer();
		KieSession ksession = kc.newKieSession("cepSession");
		ksession.setGlobal("myLogger", myLogger);
		
		User u = new User(1L, "aleksag12", "aleksa.g.98@gmail.com", "Aleksa", "Goljovic", true);

		ksession.insert(new LoginEvent(u, false));
		ksession.insert(new LoginEvent(u, false));
		ksession.insert(new LoginEvent(u, false));
		ksession.fireAllRules();
		
		assertEquals(false, u.isAllowedToLogin());
	}

	/*
	 * Testira se da se korisniku omogucava login
	 * jer u radnoj memoriji ne postoji event-ovi koji signaliziraju da je 
	 * korisnik pogresio sifru tri puta u prethodnih 5 minuta
	 * Napomena: korisniku je prethodno onemoguceno logovanje
	 */
	@Test
	public void testSuccessfulLoginWhenNoUnsuccessfulLogingEventsInWorkingMemory() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kc = ks.newKieClasspathContainer();
		KieSession ksession = kc.newKieSession("cepSession");
		ksession.setGlobal("myLogger", myLogger);

		User u = new User(1L, "aleksag12", "aleksa.g.98@gmail.com", "Aleksa", "Goljovic", true);

		ksession.insert(new LoginEvent(u, true));
		ksession.fireAllRules();
		
		assertEquals(true, u.isAllowedToLogin());
	}
	
	/*
	 * Testira se da se korisniku omogucava login
	 * nakon 5 minuta od trenutka njegovog blokiranja 
	 * 
	 */
	@Test
	public void testSuccessfulLoginAfter5MinutesOfBlocking() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kc = ks.newKieClasspathContainer();
		KieSession ksession = kc.newKieSession("cepKsessionPseudoClock");
		SessionPseudoClock clock = ksession.getSessionClock();
		ksession.setGlobal("myLogger", myLogger);

		User u = new User(1L, "aleksag12", "aleksa.g.98@gmail.com", "Aleksa", "Goljovic", true);

		ksession.insert(new LoginEvent(u, false));
		ksession.insert(new LoginEvent(u, false));
		ksession.insert(new LoginEvent(u, false));
		ksession.fireAllRules();
		
		assertEquals(false, u.isAllowedToLogin());
		
		clock.advanceTime(5, TimeUnit.MINUTES);
		
		ksession.insert(new LoginEvent(u, true));
		ksession.fireAllRules();
		
		assertEquals(true, u.isAllowedToLogin());
	}

}

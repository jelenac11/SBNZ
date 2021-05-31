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
import better.me.model.AdminReport;
import better.me.model.Meal;
import better.me.model.User;
import better.me.util.MyLogger;

public class CepTest {

	private final MyLogger myLogger = new MyLogger();

	/*
	 * Testira se kreiranje izvestaja koji prikazuje top 5 jela koja su najvise puta
	 * pojedena u poslednjih 30 dana
	 * 
	 */
	@Test
	public void test_cepReportRulesTestTop5EatenMeals() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kc = ks.newKieClasspathContainer();
		KieSession ksession = kc.newKieSession("cepReportKsessionPseudoClock");
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
		ksession.insert(new MealEatenEvent(meal1));
		ksession.insert(new MealEatenEvent(meal1));
		ksession.insert(new MealEatenEvent(meal2));
		ksession.insert(new MealEatenEvent(meal2));
		ksession.insert(new MealEatenEvent(meal2));
		ksession.insert(new MealEatenEvent(meal2));
		ksession.insert(new MealEatenEvent(meal3));
		ksession.insert(new MealEatenEvent(meal3));
		ksession.insert(new MealEatenEvent(meal3));
		ksession.insert(new MealEatenEvent(meal4));
		ksession.insert(new MealEatenEvent(meal4));
		ksession.insert(new MealEatenEvent(meal4));
		ksession.insert(new MealEatenEvent(meal4));
		ksession.insert(new MealEatenEvent(meal4));
		ksession.insert(new MealEatenEvent(meal5));
		ksession.insert(new MealEatenEvent(meal5));
		ksession.insert(new MealEatenEvent(meal5));
		ksession.insert(new MealEatenEvent(meal5));
		ksession.insert(new MealEatenEvent(meal5));
		ksession.insert(new MealEatenEvent(meal5));
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
		KieSession ksession = kc.newKieSession("cepReportKsessionPseudoClock");
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
	 * Testira se da nakon tri neuspesna logina u roku od 5 minuta,
	 * korisnik vise ne moze da se loguje i privremeno se blokira
	 * Napomena: korisniku je prethodno dozvoljeno logovanje
	 */
	@Test
	public void testUnsuccessfulLoginAfterThreeAttempts() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kc = ks.newKieClasspathContainer();
		KieSession ksession = kc.newKieSession("cepLoginSession");
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
		KieSession ksession = kc.newKieSession("cepLoginSession");
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
		KieSession ksession = kc.newKieSession("cepLoginSessionPseudoClock");
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

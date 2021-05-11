package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import better.me.facts.RegisteredUserFact;
import better.me.facts.WeekFact;
import better.me.util.MyLogger;

public class BmiTest {

	private final MyLogger myLogger = new MyLogger();

	protected final String ksessionName = "session";

	private KieSession kieSession;

	@Before
	public void setUp() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks
				.newKieContainer(ks.newReleaseId("sbnz.integracija", "drools-spring-kjar", "0.0.1-SNAPSHOT"));
		kieSession = kContainer.newKieSession("session");
		kieSession.getAgenda().getAgendaGroup("bmi").setFocus();
	}

	@Test
	public void bmiRule_bmi15age15maleGiven_shouldDetermineGainWeight() {
		RegisteredUserFact user = new RegisteredUserFact(15, "MALE", 190, 50, "ECTOMORPH", "INACTIVE", "VEGAN",
				"BEGINNER", "BEGINNER", null, 0, 0, 0);
		WeekFact week = new WeekFact();

		kieSession.setGlobal("myLogger", myLogger);

		kieSession.insert(user);
		kieSession.insert(week);

		int firedRules = kieSession.fireAllRules();
		assertEquals(2, firedRules);
		assertEquals("GAIN_WEIGHT", week.getGoal());
	}

	@Test
	public void bmiRule_bmi30age15maleGiven_shouldDetermineLoseWeight() {
		RegisteredUserFact user = new RegisteredUserFact(15, "MALE", 190, 110, "ECTOMORPH", "INACTIVE", "VEGAN",
				"BEGINNER", "BEGINNER", null, 0, 0, 0);
		WeekFact week = new WeekFact();

		kieSession.setGlobal("myLogger", myLogger);

		kieSession.insert(user);
		kieSession.insert(week);

		int firedRules = kieSession.fireAllRules();
		assertEquals(2, firedRules);
		assertEquals("LOSE_WEIGHT", week.getGoal());
	}

	@Test
	public void bmiRule_bmi19age15maleGiven_shouldDetermineMaintainWeight() {
		RegisteredUserFact user = new RegisteredUserFact(15, "MALE", 190, 70, "ECTOMORPH", "INACTIVE", "VEGAN",
				"BEGINNER", "BEGINNER", null, 0, 0, 0);
		WeekFact week = new WeekFact();

		kieSession.setGlobal("myLogger", myLogger);

		kieSession.insert(user);
		kieSession.insert(week);

		int firedRules = kieSession.fireAllRules();
		assertEquals(2, firedRules);
		assertEquals("MAINTAIN_WEIGHT", week.getGoal());
	}

	@Test
	public void bmiRule_bmi29age15femaleGiven_shouldDetermineLoseWeight() {
		RegisteredUserFact user = new RegisteredUserFact(15, "FEMALE", 170, 85, "ECTOMORPH", "INACTIVE", "VEGAN",
				"BEGINNER", "BEGINNER", null, 0, 0, 0);
		WeekFact week = new WeekFact();

		kieSession.setGlobal("myLogger", myLogger);

		kieSession.insert(user);
		kieSession.insert(week);

		int firedRules = kieSession.fireAllRules();
		assertEquals(2, firedRules);
		assertEquals("LOSE_WEIGHT", week.getGoal());
	}

	@Test
	public void bmiRule_bmi17age15femaleGiven_shouldDetermineGainWeight() {
		RegisteredUserFact user = new RegisteredUserFact(15, "FEMALE", 190, 50, "ECTOMORPH", "INACTIVE", "VEGAN",
				"BEGINNER", "BEGINNER", null, 0, 0, 0);
		WeekFact week = new WeekFact();

		kieSession.setGlobal("myLogger", myLogger);

		kieSession.insert(user);
		kieSession.insert(week);

		int firedRules = kieSession.fireAllRules();
		assertEquals(2, firedRules);
		assertEquals("GAIN_WEIGHT", week.getGoal());
	}

	@Test
	public void bmiRule_bmi20age15femaleGiven_shouldDetermineMaintainWeight() {
		RegisteredUserFact user = new RegisteredUserFact(15, "FEMALE", 190, 65, "ECTOMORPH", "INACTIVE", "VEGAN",
				"BEGINNER", "BEGINNER", null, 0, 0, 0);
		WeekFact week = new WeekFact();

		kieSession.setGlobal("myLogger", myLogger);

		kieSession.insert(user);
		kieSession.insert(week);

		int firedRules = kieSession.fireAllRules();
		assertEquals(2, firedRules);
		assertEquals("MAINTAIN_WEIGHT", week.getGoal());
	}

	@Test
	public void bmiRule_bmi20age30maleGiven_shouldDetermineMaintainWeight() {
		RegisteredUserFact user = new RegisteredUserFact(30, "MALE", 190, 75, "ECTOMORPH", "INACTIVE", "VEGAN",
				"BEGINNER", "BEGINNER", null, 0, 0, 0);
		WeekFact week = new WeekFact();

		kieSession.setGlobal("myLogger", myLogger);

		kieSession.insert(user);
		kieSession.insert(week);

		int firedRules = kieSession.fireAllRules();
		assertEquals(2, firedRules);
		assertEquals("MAINTAIN_WEIGHT", week.getGoal());
	}

	@Test
	public void bmiRule_bmi30age35femaleGiven_shouldDetermineLoseWeight() {
		RegisteredUserFact user = new RegisteredUserFact(35, "FEMALE", 190, 110, "ECTOMORPH", "INACTIVE", "VEGAN",
				"BEGINNER", "BEGINNER", null, 0, 0, 0);
		WeekFact week = new WeekFact();

		kieSession.setGlobal("myLogger", myLogger);

		kieSession.insert(user);
		kieSession.insert(week);

		int firedRules = kieSession.fireAllRules();
		assertEquals(2, firedRules);
		assertEquals("LOSE_WEIGHT", week.getGoal());
	}

	@Test
	public void bmiRule_bmi15age21femaleGiven_shouldDetermineGainWeight() {
		RegisteredUserFact user = new RegisteredUserFact(21, "FEMALE", 190, 45, "ECTOMORPH", "INACTIVE", "VEGAN",
				"BEGINNER", "BEGINNER", null, 0, 0, 0);
		WeekFact week = new WeekFact();

		kieSession.setGlobal("myLogger", myLogger);

		kieSession.insert(user);
		kieSession.insert(week);

		int firedRules = kieSession.fireAllRules();
		assertEquals(2, firedRules);
		assertEquals("GAIN_WEIGHT", week.getGoal());
	}

	@Test
	public void bmiRule_bmi32age19femaleGiven_shouldDetermineLoseWeight() {
		RegisteredUserFact user = new RegisteredUserFact(19, "FEMALE", 180, 110, "ECTOMORPH", "INACTIVE", "VEGAN",
				"BEGINNER", "BEGINNER", null, 0, 0, 0);
		WeekFact week = new WeekFact();

		kieSession.setGlobal("myLogger", myLogger);

		kieSession.insert(user);
		kieSession.insert(week);

		int firedRules = kieSession.fireAllRules();
		assertEquals(2, firedRules);
		assertEquals("LOSE_WEIGHT", week.getGoal());
	}

}

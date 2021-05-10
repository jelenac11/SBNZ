package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import better.me.dto.RegisteredUserDTO;
import better.me.dto.WeekDTO;
import better.me.enums.Goal;
import better.me.util.MyLogger;

public class BmiTest {

	private final MyLogger myLogger = new MyLogger();

	protected final String ksessionName = "bmiSession";

	private KieSession kieSession;

	@Before
	public void setUp() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks
				.newKieContainer(ks.newReleaseId("sbnz.integracija", "drools-spring-kjar", "0.0.1-SNAPSHOT"));
		kieSession = kContainer.newKieSession("bmiSession");
	}

	@Test
	public void bmiRule_bmi15age15maleGiven_shouldDetermineGainWeight() {
		RegisteredUserDTO userDTO = new RegisteredUserDTO("username", "email@gmail.com", "first", "last", 15, "MALE",
				190, 50, "ECTOMORPH", "INACTIVE", "VEGAN", "BEGINNER", "BEGINNER",
				0, 0, 0);
		WeekDTO weekDTO = new WeekDTO();

		kieSession.setGlobal("myLogger", myLogger);

		kieSession.insert(userDTO);
		kieSession.insert(weekDTO);

		int firedRules = kieSession.fireAllRules();
		assertEquals(2, firedRules);
		assertEquals(Goal.GAIN_WEIGHT, weekDTO.getGoal());
	}
	
	@Test
	public void bmiRule_bmi30age15maleGiven_shouldDetermineLoseWeight() {
		RegisteredUserDTO userDTO = new RegisteredUserDTO("username", "email@gmail.com", "first", "last", 15, "MALE",
				190, 110, "ECTOMORPH", "INACTIVE", "VEGAN", "BEGINNER", "BEGINNER",
				0, 0, 0);
		WeekDTO weekDTO = new WeekDTO();

		kieSession.setGlobal("myLogger", myLogger);

		kieSession.insert(userDTO);
		kieSession.insert(weekDTO);

		int firedRules = kieSession.fireAllRules();
		assertEquals(2, firedRules);
		assertEquals(Goal.LOSE_WEIGHT, weekDTO.getGoal());
	}
	
	@Test
	public void bmiRule_bmi19age15maleGiven_shouldDetermineMaintainWeight() {
		RegisteredUserDTO userDTO = new RegisteredUserDTO("username", "email@gmail.com", "first", "last", 15, "MALE",
				190, 70, "ECTOMORPH", "INACTIVE", "VEGAN", "BEGINNER", "BEGINNER",
				0, 0, 0);
		WeekDTO weekDTO = new WeekDTO();

		kieSession.setGlobal("myLogger", myLogger);

		kieSession.insert(userDTO);
		kieSession.insert(weekDTO);

		int firedRules = kieSession.fireAllRules();
		assertEquals(2, firedRules);
		assertEquals(Goal.MAINTAIN_WEIGHT, weekDTO.getGoal());
	}
	
	@Test
	public void bmiRule_bmi29age15femaleGiven_shouldDetermineLoseWeight() {
		RegisteredUserDTO userDTO = new RegisteredUserDTO("username", "email@gmail.com", "first", "last", 15, "FEMALE",
				170, 85, "ECTOMORPH", "INACTIVE", "VEGAN", "BEGINNER", "BEGINNER",
				0, 0, 0);
		WeekDTO weekDTO = new WeekDTO();

		kieSession.setGlobal("myLogger", myLogger);

		kieSession.insert(userDTO);
		kieSession.insert(weekDTO);

		int firedRules = kieSession.fireAllRules();
		assertEquals(2, firedRules);
		assertEquals(Goal.LOSE_WEIGHT, weekDTO.getGoal());
	}
	
	@Test
	public void bmiRule_bmi17age15femaleGiven_shouldDetermineGainWeight() {
		RegisteredUserDTO userDTO = new RegisteredUserDTO("username", "email@gmail.com", "first", "last", 15, "FEMALE",
				190, 50, "ECTOMORPH", "INACTIVE", "VEGAN", "BEGINNER", "BEGINNER",
				0, 0, 0);
		WeekDTO weekDTO = new WeekDTO();

		kieSession.setGlobal("myLogger", myLogger);

		kieSession.insert(userDTO);
		kieSession.insert(weekDTO);

		int firedRules = kieSession.fireAllRules();
		assertEquals(2, firedRules);
		assertEquals(Goal.GAIN_WEIGHT, weekDTO.getGoal());
	}
	
	@Test
	public void bmiRule_bmi20age15femaleGiven_shouldDetermineMaintainWeight() {
		RegisteredUserDTO userDTO = new RegisteredUserDTO("username", "email@gmail.com", "first", "last", 15, "FEMALE",
				190, 65, "ECTOMORPH", "INACTIVE", "VEGAN", "BEGINNER", "BEGINNER",
				0, 0, 0);
		WeekDTO weekDTO = new WeekDTO();

		kieSession.setGlobal("myLogger", myLogger);

		kieSession.insert(userDTO);
		kieSession.insert(weekDTO);

		int firedRules = kieSession.fireAllRules();
		assertEquals(2, firedRules);
		assertEquals(Goal.MAINTAIN_WEIGHT, weekDTO.getGoal());
	}
	
	@Test
	public void bmiRule_bmi20age30maleGiven_shouldDetermineMaintainWeight() {
		RegisteredUserDTO userDTO = new RegisteredUserDTO("username", "email@gmail.com", "first", "last", 30, "MALE",
				190, 75, "ECTOMORPH", "INACTIVE", "VEGAN", "BEGINNER", "BEGINNER",
				0, 0, 0);
		WeekDTO weekDTO = new WeekDTO();

		kieSession.setGlobal("myLogger", myLogger);

		kieSession.insert(userDTO);
		kieSession.insert(weekDTO);

		int firedRules = kieSession.fireAllRules();
		assertEquals(2, firedRules);
		assertEquals(Goal.MAINTAIN_WEIGHT, weekDTO.getGoal());
	}
	
	@Test
	public void bmiRule_bmi30age35femaleGiven_shouldDetermineLoseWeight() {
		RegisteredUserDTO userDTO = new RegisteredUserDTO("username", "email@gmail.com", "first", "last", 35, "FEMALE",
				190, 110, "ECTOMORPH", "INACTIVE", "VEGAN", "BEGINNER", "BEGINNER",
				0, 0, 0);
		WeekDTO weekDTO = new WeekDTO();

		kieSession.setGlobal("myLogger", myLogger);

		kieSession.insert(userDTO);
		kieSession.insert(weekDTO);

		int firedRules = kieSession.fireAllRules();
		assertEquals(2, firedRules);
		assertEquals(Goal.LOSE_WEIGHT, weekDTO.getGoal());
	}
	
	@Test
	public void bmiRule_bmi15age21femaleGiven_shouldDetermineGainWeight() {
		RegisteredUserDTO userDTO = new RegisteredUserDTO("username", "email@gmail.com", "first", "last", 21, "FEMALE",
				190, 45, "ECTOMORPH", "INACTIVE", "VEGAN", "BEGINNER", "BEGINNER",
				0, 0, 0);
		WeekDTO weekDTO = new WeekDTO();

		kieSession.setGlobal("myLogger", myLogger);

		kieSession.insert(userDTO);
		kieSession.insert(weekDTO);

		int firedRules = kieSession.fireAllRules();
		assertEquals(2, firedRules);
		assertEquals(Goal.GAIN_WEIGHT, weekDTO.getGoal());
	}
	
	@Test
	public void bmiRule_bmi32age19femaleGiven_shouldDetermineLoseWeight() {
		RegisteredUserDTO userDTO = new RegisteredUserDTO("username", "email@gmail.com", "first", "last", 19, "FEMALE",
				180, 110, "ECTOMORPH", "INACTIVE", "VEGAN", "BEGINNER", "BEGINNER",
				0, 0, 0);
		WeekDTO weekDTO = new WeekDTO();

		kieSession.setGlobal("myLogger", myLogger);

		kieSession.insert(userDTO);
		kieSession.insert(weekDTO);

		int firedRules = kieSession.fireAllRules();
		assertEquals(2, firedRules);
		assertEquals(Goal.LOSE_WEIGHT, weekDTO.getGoal());
	}

}

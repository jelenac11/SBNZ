package tests;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import better.me.dto.RegisteredUserDTO;
import better.me.dto.WeekDTO;
import better.me.enums.Goal;
import better.me.util.MyLogger;

public class BmiTest {

	private final MyLogger myLogger = new MyLogger();

	protected final String ksessionName = "session";

	private KieSession kieSession;

	@BeforeEach
	public void setUpBeforeEach() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks
				.newKieContainer(ks.newReleaseId("sbnz.integracija", "drools-spring-kjar", "0.0.1-SNAPSHOT"));
		kieSession = kContainer.newKieSession("session");
		kieSession.getAgenda().getAgendaGroup("bmi").setFocus();
		kieSession.setGlobal("myLogger", myLogger);
	}
		
	@ParameterizedTest
	@CsvSource({
		"15, MALE, 190, 50",
		"15, FEMALE, 190, 50",
		"17, MALE, 190, 50",
		"21, FEMALE, 180, 45",
	})
	public void bmiRule_allValuesGiven_shouldDetermineGainWeight(int age, String sex, double height, double weight) {
		RegisteredUserDTO userDTO = new RegisteredUserDTO("username", "email@gmail.com", "first", "last", age, sex,
				height, weight, "ECTOMORPH", "INACTIVE", "VEGAN", "BEGINNER", "BEGINNER",
				0, null, 0, 0);
		WeekDTO weekDTO = new WeekDTO();
		
		kieSession.insert(userDTO);
		kieSession.insert(weekDTO);

		int firedRules = kieSession.fireAllRules();
		assertEquals(2, firedRules);
		assertEquals(Goal.GAIN_WEIGHT, weekDTO.getGoal());
	}
	
	@ParameterizedTest
	@CsvSource({
		"15, MALE, 190, 110",
		"15, FEMALE, 170, 85",
		"19, FEMALE, 180, 110",
		"35, MALE, 190, 109",
	})
	public void bmiRule_allValuesGiven_shouldDetermineLoseWeight(int age, String sex, double height, double weight) {
		RegisteredUserDTO userDTO = new RegisteredUserDTO("username", "email@gmail.com", "first", "last", age, sex,
				height, weight, "ECTOMORPH", "INACTIVE", "VEGAN", "BEGINNER", "BEGINNER",
				0, null, 0, 0);
		WeekDTO weekDTO = new WeekDTO();

		kieSession.insert(userDTO);
		kieSession.insert(weekDTO);

		int firedRules = kieSession.fireAllRules();
		assertEquals(2, firedRules);
		assertEquals(Goal.LOSE_WEIGHT, weekDTO.getGoal());
	}
	
	@ParameterizedTest
	@CsvSource({
		"15, MALE, 190, 72",
		"15, FEMALE, 185, 65",
		"42, FEMALE, 176, 60",
		"30, MALE, 190, 70",
	})
	public void bmiRule_allValuesGiven_shouldDetermineMaintainWeight(int age, String sex, double height, double weight) {
		RegisteredUserDTO userDTO = new RegisteredUserDTO("username", "email@gmail.com", "first", "last", age, sex,
				height, weight, "ECTOMORPH", "INACTIVE", "VEGAN", "BEGINNER", "BEGINNER",
				0, null, 0, 0);
		WeekDTO weekDTO = new WeekDTO();

		kieSession.insert(userDTO);
		kieSession.insert(weekDTO);

		int firedRules = kieSession.fireAllRules();
		assertEquals(2, firedRules);
		assertEquals(Goal.MAINTAIN_WEIGHT, weekDTO.getGoal());
	}

}

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
import better.me.util.MyLogger;

public class NutritionTest {

	private final MyLogger myLogger = new MyLogger();

	protected final String ksessionName = "session";

	private KieSession kieSession;

	@BeforeEach
	public void setUp() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks
				.newKieContainer(ks.newReleaseId("sbnz.integracija", "drools-spring-kjar", "0.0.1-SNAPSHOT"));
		kieSession = kContainer.newKieSession("session");
		kieSession.getAgenda().getAgendaGroup("nutrition").setFocus();
	}

	@ParameterizedTest
	@CsvSource({
		"INACTIVE, 1",
		"SEDENTARY, 1.2",
		"LIGHTLY_ACTIVE, 1.375",
		"MODERATELY_ACTIVE, 1.55",
		"VERY_ACTIVE, 1.725",
	})
	public void activityCountRule_allValuesGiven_shouldSetActivityCount(String activityLevel, double activityCount) {
		RegisteredUserDTO userDTO = new RegisteredUserDTO("username", "email@gmail.com", "first", "last", 22, "MALE",
				170, 70, "ECTOMORPH", activityLevel, "VEGAN", "BEGINNER", "BEGINNER", 0, null, 0, 0);

		kieSession.setGlobal("myLogger", myLogger);

		kieSession.insert(userDTO);

		int firedRules = kieSession.fireAllRules();
		assertEquals(1, firedRules);
		assertEquals(activityCount, userDTO.getActivityCount(), 0.0);
	}
	
	@ParameterizedTest
	@CsvSource({
		"22, MALE, 170, 70, INACTIVE, 1657.5",
		"35, FEMALE, 170, 55, VERY_ACTIVE, 2201.9625",
		"28, MALE, 180, 80, SEDENTARY, 2148",
		"19, MALE, 190, 85, MODERATELY_ACTIVE, 3018.625",
		"27, FEMALE, 177, 70, LIGHTLY_ACTIVE, 2076.59375",
		"18, FEMALE, 165, 50, SEDENTARY, 1536.3",
	})
	public void calculateCaloriesRule_allValuesGiven_shouldSetGoalCalories(int age, String sex, double height, double weight, String activityLevel, double goalCalories) {
		RegisteredUserDTO userDTO = new RegisteredUserDTO("username", "email@gmail.com", "first", "last", age, sex,
				height, weight, "ECTOMORPH", activityLevel, "VEGAN", "BEGINNER", "BEGINNER", 0, null, 0, 0);
		WeekDTO weekDTO = new WeekDTO();

		kieSession.setGlobal("myLogger", myLogger);

		kieSession.insert(userDTO);
		kieSession.insert(weekDTO);

		int firedRules = kieSession.fireAllRules();
		assertEquals(2, firedRules);
		assertEquals(goalCalories, weekDTO.getGoalCalories(), 0.0);
	}

}

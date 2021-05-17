package tests;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import better.me.enums.Goal;
import better.me.model.RegisteredUser;
import better.me.model.Week;
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
		RegisteredUser user = new RegisteredUser(22, "MALE", 170, 70, "ECTOMORPH", activityLevel, "VEGAN",
				"BEGINNER", "BEGINNER", null, 0, 0, 0);

		kieSession.setGlobal("myLogger", myLogger);

		kieSession.insert(user);

		int firedRules = kieSession.fireAllRules();
		assertEquals(1, firedRules);
		assertEquals(activityCount, user.getActivityCount(), 0.0);
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
	public void calculateCaloriesRule_allValuesGiven_shouldSetGoalCalories(int age, String sex, double height,
			double weight, String activityLevel, double goalCalories) {
		RegisteredUser user = new RegisteredUser(age, sex, height, weight, "ECTOMORPH", activityLevel, "VEGAN",
				"BEGINNER", "BEGINNER", null, 0, 0, 0);
		Week week = new Week();

		kieSession.setGlobal("myLogger", myLogger);

		kieSession.insert(user);
		kieSession.insert(week);

		int firedRules = kieSession.fireAllRules();
		assertEquals(2, firedRules);
		assertEquals(goalCalories, week.getGoalCalories(), 0.0);
	}

	@ParameterizedTest
	@CsvSource({ 
		"70, GAIN_WEIGHT, 168", 
		"70, LOSE_WEIGHT, 98", 
		"70, MAINTAIN_WEIGHT, 126", 
		"95, GAIN_WEIGHT, 228",
		"95, LOSE_WEIGHT, 133", 
		"95, MAINTAIN_WEIGHT, 171", 
	})
	public void calculateProteinsRule_allValuesGiven_shouldSetGoalProteins(double weight, Goal goal,
			double goalProteins) {
		RegisteredUser user = new RegisteredUser(22, "MALE", 175, weight, "ECTOMORPH", "INACTIVE", "VEGAN",
				"BEGINNER", "BEGINNER", null, 0, 0, 0);
		Week week = new Week();
		week.setGoal(goal.toString());

		kieSession.setGlobal("myLogger", myLogger);

		kieSession.insert(user);
		kieSession.insert(week);

		int firedRules = kieSession.fireAllRules();
		assertEquals(4, firedRules);
		assertEquals(goalProteins, week.getGoalProteins(), 0.0);
	}

	@ParameterizedTest
	@CsvSource({ 
		"70, ECTOMORPH, GAIN_WEIGHT, 172.4625, 32.81715", 
		"70, MESOMORPH, LOSE_WEIGHT, 189.825, 56.1882", 
		"70, ENDOMORPH, MAINTAIN_WEIGHT, 144.1875, 64.01925", 
	})
	public void calculateCarbsAndFatsRule_allValuesGiven_shouldSetGoalCarbsAndGoalFats(double weight, String bodyType, Goal goal,
			double goalCarbs, double goalFats) {
		RegisteredUser user = new RegisteredUser(22, "MALE", 170, weight, bodyType, "INACTIVE", "VEGAN",
				"BEGINNER", "BEGINNER", null, 0, 0, 0);
		Week week = new Week();
		week.setGoal(goal.toString());

		kieSession.setGlobal("myLogger", myLogger);

		kieSession.insert(user);
		kieSession.insert(week);

		int firedRules = kieSession.fireAllRules();
		assertEquals(4, firedRules);
		assertEquals(goalCarbs, week.getGoalCarbs(), 0.1);
		assertEquals(goalFats, week.getGoalFats(), 0.1);
	}

}

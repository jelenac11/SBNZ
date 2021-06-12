package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@RunWith(Suite.class)
@SuiteClasses({ 
		DailyNutritionTest.class, BodyTypeDeterminationTest.class,
		BmiTest.class, FilterTest.class,
		GroceriesTest.class, NewMealTest.class, NutritionTest.class,
		RecommendationTest.class, ReportTest.class, CepTest.class
})
@ContextConfiguration
@TestPropertySource("classpath:test.properties")
public class TestAll {

}

package better.me.facts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyNutritionFact {

	private int day;
	private double calories;
	private double fats;
	private double proteins;
	private double carbs;
	private boolean exceed;
}

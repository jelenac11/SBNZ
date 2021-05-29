package better.me.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyNutrition {

	private int day;
	private double calories;
	private double fats;
	private double proteins;
	private double carbs;
	private boolean exceed;
	private Notification notification;
}

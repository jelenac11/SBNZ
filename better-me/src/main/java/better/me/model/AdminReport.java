package better.me.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class AdminReport {

	private Date date;
	private List<MealPopularity> mostPopularMeals;
	private List<MealPopularity> mostRatedMeals;
	private int eatings;
	
	public AdminReport() {
		this.mostPopularMeals = new ArrayList<MealPopularity>();
		this.mostRatedMeals = new ArrayList<MealPopularity>();
	}
}

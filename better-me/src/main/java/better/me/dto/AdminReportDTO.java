package better.me.dto;

import java.util.ArrayList;
import java.util.stream.Collectors;

import better.me.modelDB.AdminReportDB;
import better.me.modelDB.MealPopularityDB;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminReportDTO {
	
	private int eatings;
	private ArrayList<MealPopularityDTO> mostPopularMeals;
	private ArrayList<MealPopularityDTO> mostRatedMeals;

	public AdminReportDTO(AdminReportDB a) {
		this.eatings = a.getEatings();
		this.mostPopularMeals = (ArrayList<MealPopularityDTO>) (new ArrayList<MealPopularityDB>(a.getMostPopularMeals())).stream().map(MealPopularityDTO::new).collect(Collectors.toList());
		this.mostRatedMeals = (ArrayList<MealPopularityDTO>) (new ArrayList<MealPopularityDB>(a.getMostRatedMeals())).stream().map(MealPopularityDTO::new).collect(Collectors.toList());
	}
	
}

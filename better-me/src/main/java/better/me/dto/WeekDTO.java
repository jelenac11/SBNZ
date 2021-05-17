package better.me.dto;

import better.me.enums.Goal;
import better.me.modelDB.WeekDB;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeekDTO {

	private Goal goal;
	private double goalCalories;
	private double goalCarbs;
	private double goalProteins;
	private double goalFats;
	
	public WeekDTO(WeekDB week) {
		this(week.getGoal(), week.getGoalCalories(), week.getGoalCarbs(), week.getGoalProteins(), week.getGoalFats());
	}

}

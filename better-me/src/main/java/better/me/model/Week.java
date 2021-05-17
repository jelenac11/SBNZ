package better.me.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import better.me.modelDB.DayDB;
import better.me.modelDB.WeekDB;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Week {
	
	private Long id;
	private String goal;
	private double goalCalories;
	private double goalCarbs;
	private double goalProteins;
	private double goalFats;
	private boolean submitted;
	private List<Day> days;
	private Long userId;
	
	public Week(WeekDB w) {
		this(w.getId(), w.getGoal().toString(), w.getGoalCalories(), w.getGoalCarbs(), w.getGoalProteins(), w.getGoalFats(), w.isSubmitted(), (new ArrayList<DayDB>(w.getDays())).stream().map(Day::new).collect(Collectors.toList()), w.getUser().getId());
	}

	public Week() {
		this.days = new ArrayList<Day>();
		for (int i = 0; i <= 6; i++) 
			this.days.add(new Day());
	}
}

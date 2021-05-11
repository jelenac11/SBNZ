package better.me.facts;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import better.me.model.Day;
import better.me.model.Week;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeekFact {
	
	private Long id;
	private String goal;
	private double goalCalories;
	private double goalCarbs;
	private double goalProteins;
	private double goalFats;
	private boolean submitted;
	private List<DayFact> days;
	private Long userId;
	
	public WeekFact(Week w) {
		this(w.getId(), w.getGoal().toString(), w.getGoalCalories(), w.getGoalCarbs(), w.getGoalProteins(), w.getGoalFats(), w.isSubmitted(), (new ArrayList<Day>(w.getDays())).stream().map(DayFact::new).collect(Collectors.toList()), w.getUser().getId());
	}
	
}

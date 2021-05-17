package better.me.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import better.me.modelDB.ConcreteMealDB;
import better.me.modelDB.DayDB;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Day {

	private Long id;
	private boolean submitted;
	private double calories;
	private double carbs;
	private double proteins;
	private double fats;
	private boolean exceed;
	private List<ConcreteMeal> concreteMeals;
	
	public Day(DayDB d) {
		this(d.getId(), d.isSubmitted(), d.getCalories(), d.getCarbs(), d.getProteins(), d.getFats(), d.isExceed(), (new ArrayList<ConcreteMealDB>(d.getConcreteMeals())).stream().map(ConcreteMeal::new).collect(Collectors.toList()));
	}
	
	public Day() {
		concreteMeals = new ArrayList<ConcreteMeal>();
	}
}

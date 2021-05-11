package better.me.facts;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import better.me.model.ConcreteMeal;
import better.me.model.Day;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DayFact {

	private Long id;
	private boolean submitted;
	private double calories;
	private double carbs;
	private double proteins;
	private double fats;
	private boolean exceed;
	private List<ConcreteMealFact> concreteMeals;
	
	public DayFact(Day d) {
		this(d.getId(), d.isSubmitted(), d.getCalories(), d.getCarbs(), d.getProteins(), d.getFats(), d.isExceed(), (new ArrayList<ConcreteMeal>(d.getConcreteMeals())).stream().map(ConcreteMealFact::new).collect(Collectors.toList()));
	}
	
	public DayFact() {
		concreteMeals = new ArrayList<ConcreteMealFact>();
	}
}

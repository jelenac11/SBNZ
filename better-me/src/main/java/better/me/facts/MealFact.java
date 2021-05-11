package better.me.facts;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import better.me.model.Ingredient;
import better.me.model.Meal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MealFact {

	private Long id;
	private String name;
	private double calories;
	private double carbs;
	private double proteins;
	private double fats;
	private int time;
	private String description;
	private List<IngredientFact> ingredients;
	
	public MealFact(Meal m) {
		this(m.getId(), m.getName(), m.getCalories(), m.getCarbs(), m.getProteins(), m.getFats(), m.getTime(), m.getDescription(), (new ArrayList<Ingredient>(m.getIngredients())).stream().map(IngredientFact::new).collect(Collectors.toList()));
	}
	
	public MealFact() {
		ingredients = new ArrayList<IngredientFact>();
	}

}

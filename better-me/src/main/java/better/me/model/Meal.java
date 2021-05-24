package better.me.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import better.me.modelDB.IngredientDB;
import better.me.modelDB.MealDB;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Meal {

	private Long id;
	private String name;
	private double calories;
	private double carbs;
	private double proteins;
	private double fats;
	private int time;
	private String description;
	private List<Ingredient> ingredients;
	private double averageGrade;

	public Meal(MealDB m) {
		this(m.getId(), m.getName(), m.getCalories(), m.getCarbs(), m.getProteins(), m.getFats(), m.getTime(),
				m.getDescription(),
				(new ArrayList<IngredientDB>(m.getIngredients())).stream().map(Ingredient::new)
						.collect(Collectors.toList()),
				m.getAverageGrade());
	}

	public Meal() {
		ingredients = new ArrayList<Ingredient>();
	}

}

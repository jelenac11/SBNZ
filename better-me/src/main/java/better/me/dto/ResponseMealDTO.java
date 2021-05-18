package better.me.dto;

import java.util.List;

import better.me.model.Ingredient;
import better.me.model.Meal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMealDTO {

	private Long id;
	private String name;
	private boolean allergen;
	private double calories;
	private double carbs;
	private double proteins;
	private double fats;
	private int time;
	private String description;
	private List<Ingredient> ingredients;
	
	public ResponseMealDTO(Meal m, boolean allergen) {
		this.id = m.getId();
		this.name = m.getName();
		this.allergen = allergen;
		this.calories = m.getCalories();
		this.carbs = m.getCarbs();
		this.proteins = m.getProteins();
		this.fats = m.getFats();
		this.time = m.getTime();
		this.description = m.getDescription();
		this.ingredients = m.getIngredients();
	}
}

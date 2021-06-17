package better.me.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import better.me.modelDB.ConcreteMealDB;
import better.me.modelDB.IngredientDB;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ConcreteMeal {

	private Long id;
	private int grams;
	private boolean customMeal;
	private Meal meal;
	private Long dayId;
	private List<Ingredient> ingredients;
	
	public ConcreteMeal(ConcreteMealDB cm) {
		this(cm.getId(), cm.getGrams(), cm.isCustomMeal(), null, cm.getDay().getId(), (new ArrayList<IngredientDB>(cm.getIngredients())).stream().map(Ingredient::new).collect(Collectors.toList()));
		if (cm.getMeal() != null) {
			this.meal = new Meal(cm.getMeal());
		}
	}
	
	public ConcreteMeal() {
		ingredients = new ArrayList<Ingredient>();
	}
}

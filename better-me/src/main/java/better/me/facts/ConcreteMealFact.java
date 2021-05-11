package better.me.facts;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import better.me.model.ConcreteMeal;
import better.me.model.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ConcreteMealFact {

	private Long id;
	private int grams;
	private boolean customMeal;
	private MealFact meal;
	private Long dayId;
	private List<IngredientFact> ingredients;
	
	public ConcreteMealFact(ConcreteMeal cm) {
		this(cm.getId(), cm.getGrams(), cm.isCustomMeal(), new MealFact(cm.getMeal()), cm.getDay().getId(), (new ArrayList<Ingredient>(cm.getIngredients())).stream().map(IngredientFact::new).collect(Collectors.toList()));
	}
	
	public ConcreteMealFact() {
		ingredients = new ArrayList<IngredientFact>();
	}
}

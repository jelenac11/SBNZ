package better.me.facts;

import better.me.model.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientFact {

	private Long id;
	private int grams;
	private GroceryFact grocery;
	
	public IngredientFact(Ingredient i) {
		this(i.getId(), i.getGrams(), new GroceryFact(i.getGrocery()));
	}

}
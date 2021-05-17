package better.me.model;

import better.me.modelDB.IngredientDB;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {

	private Long id;
	private int grams;
	private Grocery grocery;
	
	public Ingredient(IngredientDB i) {
		this(i.getId(), i.getGrams(), new Grocery(i.getGrocery()));
	}

}

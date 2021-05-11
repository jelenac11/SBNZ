package better.me.facts;

import better.me.model.Grocery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroceryFact {

	private Long id;
	private String name;
	private double calories;
	private double carbs;
	private double proteins;
	private double fats;
	
	public GroceryFact(Grocery g) {
		this(g.getId(), g.getName(), g.getCalories(), g.getCarbs(), g.getProteins(), g.getFats());
	}

}

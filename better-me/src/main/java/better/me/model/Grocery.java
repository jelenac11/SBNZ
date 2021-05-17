package better.me.model;

import better.me.enums.Diet;
import better.me.modelDB.GroceryDB;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Grocery {

	private Long id;
	private String name;
	private Diet diet;
	private double calories;
	private double carbs;
	private double proteins;
	private double fats;
	
	public Grocery(GroceryDB g) {
		this(g.getId(), g.getName(), g.getDiet(), g.getCalories(), g.getCarbs(), g.getProteins(), g.getFats());
	}

}

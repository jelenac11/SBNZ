package better.me.dto;

import better.me.model.Grocery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroceryDTO {

	private String name;
	private String diet;
	private double calories;
	private double carbs;
	private double proteins;
	private double fats;
	
	public GroceryDTO(Grocery g) {
		this.name = g.getName();
		this.diet = g.getDiet().getValue();
		this.calories = g.getCalories();
		this.carbs = g.getCarbs();
		this.proteins = g.getProteins();
		this.fats = g.getFats();
	}
}

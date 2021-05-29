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
public class ResponseGroceryDTO {

	private Long id;
	private String name;
	private boolean allergen;
	private double calories;
	private double carbs;
	private double proteins;
	private double fats;
	
	public ResponseGroceryDTO(Grocery g, boolean allergen) {
		this.id = g.getId();
		this.name = g.getName();
		this.allergen = allergen;
		this.calories = g.getCalories();
		this.carbs = g.getCarbs();
		this.proteins = g.getProteins();
		this.fats = g.getFats();
	}
}

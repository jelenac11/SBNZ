package better.me.modelDB;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import better.me.enums.Diet;
import better.me.model.Grocery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "groceries")
public class GroceryDB {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "grocery_id")
	private Long id;
	
	@Column
	private String name;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Diet diet;
	
	@Column
	private double calories;
	
	@Column
	private double carbs;
	
	@Column
	private double proteins;
	
	@Column
	private double fats;
	
	@OneToMany(mappedBy = "grocery")
	private Set<IngredientDB> ingredients;
	
	public GroceryDB(Grocery g) {
		this.id = g.getId();
		this.name = g.getName();
		this.diet = g.getDiet();
		this.calories = g.getCalories();
		this.carbs = g.getCarbs();
		this.proteins = g.getProteins();
		this.fats = g.getFats();
		this.ingredients = null;
	}

}

package better.me.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "meals")
public class Meal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "meal_id")
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private double calories;
	
	@Column
	private double carbs;
	
	@Column
	private double proteins;
	
	@Column
	private double fats;
	
	@Column
	private int time; //in minutes
	
	@Column
	private String description;
	
	@ManyToMany(mappedBy = "meals_ingredients", fetch = FetchType.LAZY)
	private Set<Ingredient> ingredients;
	
	@OneToMany(mappedBy = "meal")
	private Set<ConcreteMeal> concreteMeals;
	
}

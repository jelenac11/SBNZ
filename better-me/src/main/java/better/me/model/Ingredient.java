package better.me.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ingredients")
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ingredient_id")
	private Long id;
	
	@Column
	private int grams;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "grocery_id")
	private Grocery grocery;
	
	@ManyToMany(cascade=CascadeType.ALL)  
    @JoinTable(name="concrete_meals_ingredients", joinColumns=@JoinColumn(name="ingredient_id"), inverseJoinColumns=@JoinColumn(name="concrete_meal_id"))  
	private Set<ConcreteMeal> concreteMeals;
	
	@ManyToMany(cascade=CascadeType.ALL)  
    @JoinTable(name="meals_ingredients", joinColumns=@JoinColumn(name="ingredient_id"), inverseJoinColumns=@JoinColumn(name="meal_id"))  
	private Set<Meal> meals;
	
}

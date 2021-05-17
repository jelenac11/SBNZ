package better.me.modelDB;

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
@Entity(name = "concrete_meals")
public class ConcreteMealDB {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "concrete_meal_id")
	private Long id;
	
	@Column
	private int grams;
	
	@Column
	private boolean isCustomMeal;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "meal_id")
	private MealDB meal;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "day_id")
	private DayDB day;
	
	@ManyToMany(cascade=CascadeType.ALL)  
    @JoinTable(name="concrete_meals_ingredients", joinColumns=@JoinColumn(name="concrete_meal_id", referencedColumnName = "concrete_meal_id"), inverseJoinColumns=@JoinColumn(name="ingredient_id", referencedColumnName = "ingredient_id"))  
	private Set<IngredientDB> ingredients;
}

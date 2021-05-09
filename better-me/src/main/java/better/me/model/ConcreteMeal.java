package better.me.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
public class ConcreteMeal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "concrete_meal_id")
	private Long id;
	
	@Column
	private int grams;
	
	@Column
	private boolean isCustomMeal;
	
	@ManyToMany(mappedBy = "concrete_meals_ingredients", fetch = FetchType.LAZY)
	private Set<Ingredient> ingredients;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "meal_id")
	private Meal meal;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "day_id")
	private Day day;
}

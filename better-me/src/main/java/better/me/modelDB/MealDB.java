package better.me.modelDB;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
public class MealDB {

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
	private int time;

	@Column
	private String description;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "meals_ingredients", joinColumns = @JoinColumn(name = "meal_id", referencedColumnName = "meal_id"), inverseJoinColumns = @JoinColumn(name = "ingredient_id", referencedColumnName = "ingredient_id"))
	private Set<IngredientDB> ingredients;

	@OneToMany(mappedBy = "meal")
	private Set<ConcreteMealDB> concreteMeals;

	@OneToMany(mappedBy = "meal")
	private Set<GradeDB> grades;

	@Column
	private double averageGrade;

}

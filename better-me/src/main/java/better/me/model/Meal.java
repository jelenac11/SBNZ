package better.me.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import better.me.modelDB.GradeDB;
import better.me.modelDB.IngredientDB;
import better.me.modelDB.MealDB;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Meal {

	private Long id;
	private String name;
	private double calories;
	private double carbs;
	private double proteins;
	private double fats;
	private int time;
	private String description;
	private List<Ingredient> ingredients;
	private List<Grade> grades;
	private double averageGrade;

	public Meal(MealDB m) {
		if (m != null) {
			this.id = m.getId();
			this.name = m.getName();
			this.calories = m.getCalories();
			this.proteins = m.getProteins();
			this.carbs = m.getCarbs();
			this.fats = m.getFats();
			this.time = m.getTime();
			this.description = m.getDescription();
			this.ingredients = (new ArrayList<IngredientDB>(m.getIngredients())).stream().map(Ingredient::new).collect(Collectors.toList());
			this.grades = (new ArrayList<GradeDB>(m.getGrades())).stream().map(Grade::new).collect(Collectors.toList());
			this.averageGrade = m.getAverageGrade();
		}
	}

	public Meal() {
		ingredients = new ArrayList<Ingredient>();
		grades = new ArrayList<Grade>();
	}

}

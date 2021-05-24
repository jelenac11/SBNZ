package better.me.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.kie.api.definition.type.PropertyReactive;

import better.me.modelDB.AllergenDB;
import better.me.modelDB.GradeDB;
import better.me.modelDB.RegisteredUserDB;
import better.me.modelDB.WeekDB;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@PropertyReactive
public class RegisteredUser extends User {

	private int age;
	private String sex;
	private double height;
	private double weight;
	private String bodyType;
	private String activityLevel;
	private String diet;
	private String category;
	private String previousCategory;
	private List<Allergen> allergens;
	private double bmi;
	private double activityCount;
	private int score;
	private List<Grade> grades;
	private List<Week> weeks;
	private String ageCategory;

	public RegisteredUser(RegisteredUserDB ru) {
		this(ru.getAge(), ru.getSex().toString(), ru.getHeight(), ru.getWeight(), ru.getBodyType().toString(),
				ru.getActivityLevel().toString(), ru.getDiet().toString(), ru.getCategory().toString(),
				ru.getPreviousCategory().toString(),
				(new ArrayList<AllergenDB>(ru.getAllergens())).stream().map(Allergen::new).collect(Collectors.toList()),
				ru.getBmi(), ru.getActivityCount(), ru.getScore(),
				(new ArrayList<GradeDB>(ru.getGrades())).stream().map(Grade::new).collect(Collectors.toList()),
				(new ArrayList<WeekDB>(ru.getWeeks())).stream().map(Week::new).collect(Collectors.toList()),
				ru.getAgeCategory().toString());
		this.id = ru.getId();
		this.username = ru.getUsername();
		this.email = ru.getEmail();
		this.firstName = ru.getFirstName();
		this.lastName = ru.getLastName();
	}

	public RegisteredUser() {
		this.allergens = new ArrayList<Allergen>();
		this.grades = new ArrayList<Grade>();
		this.weeks = new ArrayList<Week>();
	}

}

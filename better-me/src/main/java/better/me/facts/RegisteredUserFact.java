package better.me.facts;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import better.me.model.Allergen;
import better.me.model.RegisteredUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredUserFact extends UserFact {

	private int age;
	private String sex;
	private double height;
	private double weight;
	private String bodyType;
	private String activityLevel;
	private String diet;
	private String category;
	private String previousCategory;
	private List<AllergenFact> allergens;
	private double bmi;
	private double activityCount;
	private int score;

	public RegisteredUserFact(RegisteredUser ru) {
		this(ru.getAge(), ru.getSex().toString(), ru.getHeight(), ru.getWeight(), ru.getBodyType().toString(),
				ru.getActivityLevel().toString(), ru.getDiet().toString(), ru.getCategory().toString(),
				ru.getPreviousCategory().toString(), (new ArrayList<Allergen>(ru.getAllergens())).stream()
						.map(AllergenFact::new).collect(Collectors.toList()),
				ru.getBmi(), ru.getActivityCount(), ru.getScore());
		this.id = ru.getId();
		this.username = ru.getUsername();
		this.email = ru.getEmail();
		this.firstName = ru.getFirstName();
		this.lastName = ru.getLastName();
	}

}

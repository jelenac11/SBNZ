package better.me.dto;

import java.util.ArrayList;
import java.util.List;

import better.me.model.RegisteredUser;
import better.me.modelDB.AllergenDB;
import better.me.modelDB.RegisteredUserDB;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredUserDTO {

	private String username;
	private String email;
	private String firstName;
	private String lastName;
	private int age;
	private String sex;
	private double height;
	private double weight;
	private String bodyType;
	private String activityLevel;
	private String diet;
	private String category;
	private String previousCategory;
	private double bmi;
	private List<AllergenDB> allergens;
	private double activityCount;
	private int score;

	public RegisteredUserDTO(RegisteredUserDB user) {
		this(user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getAge(),
				user.getSex().getValue(), user.getHeight(), user.getWeight(), user.getBodyType().getValue(),
				user.getActivityLevel().getValue(), user.getDiet().getValue(), user.getCategory().getValue(),
				user.getPreviousCategory().getValue(), user.getBmi(), new ArrayList<AllergenDB>(user.getAllergens()),
				user.getActivityCount(), user.getScore());
	}

	public RegisteredUserDTO(RegisteredUser user) {
		this(user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getAge(), user.getSex(),
				user.getHeight(), user.getWeight(), user.getBodyType(), user.getActivityLevel(), user.getDiet(),
				user.getCategory(), user.getPreviousCategory(), user.getBmi(), null, user.getActivityCount(),
				user.getScore());
	}
}

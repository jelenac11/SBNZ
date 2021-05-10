package better.me.dto;

import better.me.enums.ActivityLevel;
import better.me.enums.BodyType;
import better.me.enums.Category;
import better.me.enums.Diet;
import better.me.enums.Sex;
import better.me.model.RegisteredUser;
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
	private int activityCount;
	private int score;

	public RegisteredUserDTO(RegisteredUser user) {
		this(user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getAge(), user.getSex().getValue(),
				user.getHeight(), user.getWeight(), user.getBodyType().getValue(), user.getActivityLevel().getValue(), user.getDiet().getValue(),
				user.getCategory().getValue(), user.getPreviousCategory().getValue(), user.getBmi(), user.getActivityCount(), user.getScore());
	}
}

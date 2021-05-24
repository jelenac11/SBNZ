package better.me.model;

import better.me.modelDB.GradeDB;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Grade {
	
	private int value;
	private Meal meal;
	private RegisteredUser user;
	
	public Grade(GradeDB grade) {
		this(grade.getValue(), new Meal(grade.getMeal()), new RegisteredUser(grade.getUser()));
	}
	
}

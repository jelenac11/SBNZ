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
		this(grade.getValue(),
				new Meal(grade.getMeal().getId(), grade.getMeal().getName(), grade.getMeal().getCalories(),
						grade.getMeal().getCarbs(), grade.getMeal().getProteins(), grade.getMeal().getFats(),
						grade.getMeal().getTime(), grade.getMeal().getDescription(), null, null,
						grade.getMeal().getAverageGrade()),
				new RegisteredUser(grade.getUser().getAge(), grade.getUser().getSex().toString(),
						grade.getUser().getHeight(), grade.getUser().getWeight(),
						grade.getUser().getBodyType().toString(), grade.getUser().getActivityLevel().toString(),
						grade.getUser().getDiet().toString(), grade.getUser().getCategory().toString(),
						grade.getUser().getPreviousCategory().toString(), null, grade.getUser().getBmi(),
						grade.getUser().getActivityCount(), grade.getUser().getScore(), null, null,
						grade.getUser().getAgeCategory().toString(), grade.getUser().isAllowedToEat()));
	}

}

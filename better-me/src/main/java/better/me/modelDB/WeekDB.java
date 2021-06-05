package better.me.modelDB;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import better.me.enums.Goal;
import better.me.model.Week;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity(name = "weeks")
public class WeekDB {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "week_id")
	private Long id;

	@Column
	private Goal goal;

	@Column
	private double goalCalories;

	@Column
	private double goalCarbs;

	@Column
	private double goalProteins;

	@Column
	private double goalFats;

	@Column
	private boolean submitted;

	@Column
	private boolean cheat;

	@OneToMany(mappedBy = "week")
	private Set<DayDB> days;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "user_id")
	private UserDB user;

	public WeekDB(Week fact, RegisteredUserDB rUser) {
		this(null, Goal.valueOf(fact.getGoal()), fact.getGoalCalories(), fact.getGoalCarbs(), fact.getGoalProteins(),
				fact.getGoalFats(), false, fact.isCheat(), new HashSet<DayDB>(), rUser);
		ArrayList<DayDB> days = new ArrayList<DayDB>();
		for (int i = 0; i < 7; i++) {
			days.add(new DayDB());
		}
		this.days = new HashSet<DayDB>(days);
	}

	public WeekDB() {
		ArrayList<DayDB> days = new ArrayList<DayDB>();
		for (int i = 0; i < 7; i++) {
			days.add(new DayDB());
		}
		this.days = new HashSet<DayDB>(days);
	}

}

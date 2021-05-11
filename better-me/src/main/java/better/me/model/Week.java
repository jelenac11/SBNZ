package better.me.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import better.me.enums.Goal;
import better.me.facts.WeekFact;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "weeks")
public class Week {
	
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
	
	@OneToMany(mappedBy = "week")
	private Set<Day> days;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER, optional = false)
	@JoinColumn(name="user_id")
	private RegisteredUser user;
	
	public Week(WeekFact fact, RegisteredUser rUser) {
		this(null, Goal.valueOf(fact.getGoal()), fact.getGoalCalories(), fact.getGoalCarbs(), fact.getGoalProteins(), fact.getGoalFats(), false, new HashSet<Day>(), rUser);
	}

}

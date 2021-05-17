package better.me.modelDB;

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
import better.me.model.Week;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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
	
	@OneToMany(mappedBy = "week")
	private Set<DayDB> days;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER, optional = false)
	@JoinColumn(name="user_id")
	private RegisteredUserDB user;
	
	public WeekDB(Week fact, RegisteredUserDB rUser) {
		this(null, Goal.valueOf(fact.getGoal()), fact.getGoalCalories(), fact.getGoalCarbs(), fact.getGoalProteins(), fact.getGoalFats(), false, new HashSet<DayDB>(), rUser);
	}

}

package better.me.modelDB;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "days")
public class DayDB {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "day_id")
	private Long id;
	
	@Column
	private boolean submitted;
	
	@Column
	private double calories;
	
	@Column
	private double carbs;
	
	@Column
	private double proteins;
	
	@Column
	private double fats;
	
	@Column
	private boolean exceed;
	
	@OneToMany(mappedBy = "day", cascade = CascadeType.ALL)
	private Set<ConcreteMealDB> concreteMeals;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "week_id")
	private WeekDB week;
	
}

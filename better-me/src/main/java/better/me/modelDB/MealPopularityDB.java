package better.me.modelDB;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import better.me.model.MealPopularity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "meal_popularity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MealPopularityDB {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "meal_popularity_id")
	private Long id;

	@Column
	private String meal;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "admin_report_most_popular_id", referencedColumnName = "admin_report_id")
	private AdminReportDB reportMostPopular;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "admin_report_most_rated_id", referencedColumnName = "admin_report_id")
	private AdminReportDB reportMostRated;

	@Column
	private double rate;

	@Column
	private int eaten;

	public MealPopularityDB(MealPopularity m) {
		this(null, m.getMeal(), null, null, m.getRate(), m.getEaten());
	}
	
}

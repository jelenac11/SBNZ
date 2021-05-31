package better.me.modelDB;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import better.me.model.AdminReport;
import better.me.model.MealPopularity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminReportDB {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "admin_report_id")
	private Long id;

	@Column
	private Date date;

	@OneToMany(mappedBy = "reportMostPopular")
	private Set<MealPopularityDB> mostPopularMeals;

	@OneToMany(mappedBy = "reportMostRated")
	private Set<MealPopularityDB> mostRatedMeals;

	@Column
	private int eatings;

	@SuppressWarnings("unchecked")
	public AdminReportDB(AdminReport report) {
		this(null, report.getDate(),
				(Set<MealPopularityDB>) (new HashSet<MealPopularity>(report.getMostPopularMeals())).stream()
						.map(MealPopularityDB::new).collect(Collectors.toList()),
				(Set<MealPopularityDB>) (new HashSet<MealPopularity>(report.getMostRatedMeals())).stream()
						.map(MealPopularityDB::new).collect(Collectors.toList()),
				0);
	}

}

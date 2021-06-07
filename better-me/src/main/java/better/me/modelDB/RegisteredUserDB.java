package better.me.modelDB;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import better.me.enums.ActivityLevel;
import better.me.enums.AgeCategory;
import better.me.enums.BodyType;
import better.me.enums.Category;
import better.me.enums.Diet;
import better.me.enums.Sex;

import javax.persistence.JoinColumn;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("RU")
public class RegisteredUserDB extends UserDB {

	private static final long serialVersionUID = 1L;
	@Column
	private int age;

	@Column
	@Enumerated(EnumType.STRING)
	private Sex sex;

	@Column
	private double height;

	@Column
	private double weight;

	@Column
	@Enumerated(EnumType.STRING)
	private BodyType bodyType;

	@Column
	@Enumerated(EnumType.STRING)
	private ActivityLevel activityLevel;

	@Column
	@Enumerated(EnumType.STRING)
	private Diet diet;

	@Column
	@Enumerated(EnumType.STRING)
	private Category category;

	@Column
	@Enumerated(EnumType.STRING)
	private Category previousCategory;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "allergen_user", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"), inverseJoinColumns = @JoinColumn(name = "allergen_id", referencedColumnName = "allergen_id"))
	private Set<AllergenDB> allergens;

	@Column
	private double bmi;

	@Column
	private double activityCount;

	@Column
	private int score;

	@OneToMany(mappedBy = "user")
	private Set<GradeDB> grades;

	@OneToMany(mappedBy = "user")
	private Set<WeekDB> weeks;
	
	@Column
	@Enumerated(EnumType.STRING)
	private AgeCategory ageCategory;
	
	@Column
	private boolean allowedToEat;

	public RegisteredUserDB(Long id) {
		super(id);
	}

	public RegisteredUserDB(Long id, String username, String email, String password, String firstName,
			String lastName, boolean allowedToLogin) {
		super(id, username, email, password, firstName, lastName, allowedToLogin);
	}

	public RegisteredUserDB(String username, String email, String password, String firstName, String lastName, boolean allowedToLogin) {
		super(username, email, password, firstName, lastName, allowedToLogin);
		this.sex = Sex.MALE;
		this.bodyType = BodyType.ECTOMORPH;
		this.activityLevel = ActivityLevel.INACTIVE;
		this.diet = Diet.OMNIVORE;
		this.category = Category.BEGINNER;
		this.previousCategory = Category.BEGINNER;
		this.ageCategory = AgeCategory.CHILD;
		this.allowedToEat = true;
		this.allergens = new HashSet<AllergenDB>();
	}

}

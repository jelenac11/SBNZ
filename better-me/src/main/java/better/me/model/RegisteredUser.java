package better.me.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import better.me.enums.ActivityLevel;
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
public class RegisteredUser extends User {

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
	
	@ManyToMany(cascade=CascadeType.ALL)  
    @JoinTable(name="allergen_user", joinColumns=@JoinColumn(name="user_id", referencedColumnName = "user_id"), inverseJoinColumns=@JoinColumn(name="allergen_id", referencedColumnName = "allergen_id"))  
	private Set<Allergen> allergens;
	
	@Column
	private double bmi;
	
	@Column
	private int activityCount;
	
	@Column
	private int score;

	public RegisteredUser(Long id) {
		super(id);
	}

	public RegisteredUser(Long id, String username, String email, String password, String firstName, String lastName) {
		super(id, username, email, password, firstName, lastName);
	}

	public RegisteredUser(String username, String email, String password, String firstName, String lastName) {
		super(username, email, password, firstName, lastName);
	}

}

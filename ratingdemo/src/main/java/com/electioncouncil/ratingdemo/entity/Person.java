package com.electioncouncil.ratingdemo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * <p>This class represents a hibernate entity mapping to the person table</p>
 * <p>There is a 1 to 1 mapping to the  manifesto table as well as a 
 * one to many mapping to the ratings table</p>
 * @author User
 *
 */
@Entity
@Table(name="person")
public class Person implements IPerson {

	// Fields
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="email")
	private String email;
	
	@Column(name="isContender")
	private boolean isContender;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="id_manifesto")
	@JsonIgnore
	private Manifesto manifesto;
	
	@OneToMany(mappedBy="person", cascade=CascadeType.ALL)
	//@JsonIgnore
	private List<Rating> ratingList;
	
	@Transient
	private Double totalAverageRating;

	// Constructors

	public Person() {}

	public Person(String firstName, String lastName, String email, boolean isContender, Manifesto manifesto) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.isContender = isContender;
		this.manifesto = manifesto;
	}

	// Getter and Setters

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isContender() {
		return isContender;
	}

	public void setContender(boolean isContender) {
		this.isContender = isContender;
	}

	public Manifesto getManifesto() {
		return manifesto;
	}

	public void setManifesto(Manifesto manifesto) {
		this.manifesto = manifesto;
	}
	
	public List<Rating> getRatingList() {
		return ratingList;
	}

	public void setRatingList(List<Rating> ratingList) {
		this.ratingList = ratingList;
	}

	
	public void add(Rating rating)
	{
		if(ratingList == null)
		{
			ratingList = new ArrayList<Rating>();
		}
		
		ratingList.add(rating);
		
		rating.setPerson(this);
	}


	public Double getTotalAverageRating() {
		return totalAverageRating;
	}

	public void setTotalAverageRating(Double totalAverageRating) {
		this.totalAverageRating = totalAverageRating;
	}

	// tostring
	@Override
	public String toString() {
		return "Person [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", isContender=" + isContender + ", manifesto=" + manifesto + "]";
	}

	public double calculteContendersTotalAverageRatings() {
		totalAverageRating = 0.0;
		
		if(!isContender || getManifesto() == null)
			return 0.0;
		
		List<Idea> ideaList = getManifesto().getIdeaList();
		
		for(Idea idea : ideaList)			
			totalAverageRating += idea.calculateAverageRating();

		return totalAverageRating;
		
		
	}
	
}

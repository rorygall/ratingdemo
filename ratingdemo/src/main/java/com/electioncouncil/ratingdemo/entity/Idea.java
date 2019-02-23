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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * <p>This class is a Hibernate mapped Entity representing an idea</p>
 * @author User
 *
 */
@Entity
@Table(name="idea")
public class Idea {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="description")
	private String description;
	
	@ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.REFRESH,
			CascadeType.MERGE, CascadeType.DETACH})
	@JoinColumn(name="id_manifesto")
	@JsonIgnore
	private Manifesto manifesto;

	@OneToMany(mappedBy="idea", cascade=CascadeType.ALL)
	//@JsonIgnore
	private List<Rating> ratingList;

	public Idea() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		
		rating.setIdea(this);
	}

	@Override
	public String toString() {
		return "Idea [id=" + id + ", title=" + title + ", description=" + description + ", manifesto=" + manifesto
				+ "]";
	}

	/**
	 * <p>Calculate the average rating for the idea</p>
	 * @return
	 */
	public double calculateAverageRating() {
		double averageRating = 0;
		try
		{
			List<Rating> ratingList = getRatingList();
			if(ratingList == null)
				return 0.0;
			
			double totalRating = 0;
			for(Rating rating: ratingList)		
				totalRating += rating.getRating();
	
			averageRating = totalRating/ratingList.size();
		} catch (Exception e)
		{
			// Do Nothing for now
		}
		return averageRating;
	}
	
	
}

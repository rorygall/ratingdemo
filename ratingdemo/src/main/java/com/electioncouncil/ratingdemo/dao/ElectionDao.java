package com.electioncouncil.ratingdemo.dao;

import java.util.List;

import com.electioncouncil.ratingdemo.entity.Idea;
import com.electioncouncil.ratingdemo.entity.Person;
import com.electioncouncil.ratingdemo.entity.Rating;

/**
 * <p>This Interface represents the DAO Layer providing data access methods for the election</p> 
 * @author User
 *
 */
public interface ElectionDao {

	/**
	 * <p>Get all people in the database</p>
	 * @return
	 */
	public List<Person> findAllPeople();

	/**
	 * <p>Find a person by their Id</p>
	 * @param theId
	 * @return
	 */
	public Person findPersonById(int theId);
	
	/**
	 * <p>Persist a person to storage</p>
	 * @param thePerson
	 */
	public void savePerson(Person thePerson);
	
	/**
	 * <p>Delete a person by Id</p>
	 * @param theId
	 */
	public void deletePersonById(int theId);
	
	/**
	 * <p>Find a rating by Id</p>
	 * @param theId
	 * @return
	 */
	public Rating findRatingById(int theId);

	/**
	 * <p>Get a list of all Ratings</p>
	 * @return
	 */
	public List<Rating> findAllRatings();

	/**
	 * <p>Delete a rating</p>
	 * @param rating
	 */
	public void deleteRating(Rating rating);

	/**
	 * <p>Find an idea by id</p>
	 * @param theId
	 * @return
	 */
	public Idea findIdeaById(int theId);

	/**
	 * <p>Persist an Idea to storage</p>
	 * @param theIdea
	 */
	public void saveIdea(Idea theIdea);

}

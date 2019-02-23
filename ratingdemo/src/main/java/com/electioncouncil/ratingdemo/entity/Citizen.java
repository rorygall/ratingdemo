package com.electioncouncil.ratingdemo.entity;

/**
 * <p>This interface is to be used to represent a citizen and provide
 * the functionality unique for citizens.</p>
 * 

 * @author User
 *
 */
public interface Citizen {

	public Person getPerson();
	
	public void setPerson(Person person);
	
	public void nominateContender();
	
	public void rateContender(int personId, int rating);
	
	public void deleteRatingById(int theId);
}

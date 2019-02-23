package com.electioncouncil.ratingdemo.entity;

/**
 * <p>This interface represents the functionality of People in the election.</p>
 * <p>A Person in the system can be either a citizen or a contender.</p> 
 * @author User
 *
 */
public interface IPerson {

	public int getId();

	public void setId(int id);

	public String getFirstName();

	public void setFirstName(String firstName) ;

	public String getLastName();

	public void setLastName(String lastName);

	public String getEmail() ;
	
	public void setEmail(String email);

	public boolean isContender();

	public void setContender(boolean isContender);

}

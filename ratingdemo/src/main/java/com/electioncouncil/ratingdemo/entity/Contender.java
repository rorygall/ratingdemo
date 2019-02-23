package com.electioncouncil.ratingdemo.entity;

/**
 * <p>This interface is to be used to represent a contender and provide
 * the functionality unique for contenders.</p>
 * 

 * @author User
 *
 */

public interface Contender {
	
	public Person getPerson();
	
	public void setPerson(Person person);

	public void  postManifesto(String manifesto);
	
	public void postIdea(String idea);
}

package com.electioncouncil.ratingdemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electioncouncil.ratingdemo.entity.Manifesto;
import com.electioncouncil.ratingdemo.entity.Person;
import com.electioncouncil.ratingdemo.resource.ElectionErrorResponse;
import com.electioncouncil.ratingdemo.service.CityElectionService;
/**
 * <p>This class acts as the Controller for the REST API.</p>
 * <p>The API can be used in lieu of a user interface. 
 * It maps calls to 
 * <ul>
 * 	<li>view all the people in the election</li>
 * 	<li>view a Manifesto for a  contender, including ideas and their ratings.</li> 
 * 	<li>nominate a contender</li> 
 * 	<li>add a manifesto for a contender</li> 
 *  <li>add an idea to a manifesto</li> 
 *  <li>rate an idea</li> 
 *  <li>remove a rating </li> 
 *  <li>determine the winner of the election</li> 
 * </ul>
 * 
 *  The endpoint for the API is /api . For local testing you can use http://localhost:8080/api/ 
 *  and append appropriate endpoints and parameters for the various calls .
 * </p>
 * 
 * @author Rory
 *
 */
@RestController
@RequestMapping("/api")
public class CityElectionRestController {
	
	// Our service layer object
	private CityElectionService cityElectionService;

	@Autowired
	public CityElectionRestController(CityElectionService theCityElectionService)
	{
		cityElectionService = theCityElectionService;
	}
	
	/**
	 *  </p>Return a list of all people.<p>
	 * <p>This can be tested from a browser with this URL: http://localhost:8080/api/people </p>
	 * @return
	 */
	@GetMapping("/people")
	public List<Person> getAllPeople()
	{
		return cityElectionService.findAllPeople();	
	}
	
	/**
	 * <p>Return the Manifesto for the contenderId passed in.</p>
	 * <p>This can be tested from a browser with a url such as
	 *  http://localhost:8080/api/contender/manifesto/get/2 </p>
	 * @param contenderId
	 * @return
	 */
	@GetMapping("/contender/manifesto/get/{contenderId}")
	public Manifesto getContenderManifesto(@PathVariable("contenderId") int contenderId)
	{
		return cityElectionService.getContenderManifesto(contenderId);
		
	}
	
	/**
	 * <p>Post mapping to nominate a contender.</p>
	 * <p>This method can be tested from a REST Client such as postman with
	 *  a URL such as http://localhost:8080/api/citizen/nomiate/1  , where 1 would 
	 *  represent the ID of the citizen being nominated.</p>
	 * @param citizenId
	 */
	@PostMapping("/citizen/nomiate/{citizenId}")
	public void nominate(@PathVariable("citizenId") int citizenId)
	{
		cityElectionService.nominateContender(citizenId);
	}
	
	/**
	 * <p>Post mapping to add a manifesto to a contender.</p>
	 * <p>This method can be tested from a REST Client such as postman with
	 *  a URL such as http://localhost:8080/api/contender/manifesto/add/1/My Manifesto   
	 *  You need to supply the ID of the contender and the synopsis for the mannifesto.</p>
	 * @param contenderId
	 * @param synopsis
	 */
	@PostMapping("/contender/manifesto/add/{contenderId}/{synopsis}")
	public void addManifesto(@PathVariable("contenderId") int contenderId, @PathVariable("synopsis") String synopsis)
	{
		cityElectionService.addContenderManifesto(contenderId, synopsis);
	}
	

	/**
	 * <p>Post mapping to add an idea to a manifesto for a contender.</p>
	 * <p>This method can be tested from a REST Client such as postman with
	 *  a URL such as http://localhost:8080/api/idea/add/1/Idea Title/Idea Description   
	 *  You need to supply the ID of the contender and the title and description for the idea.</p>
	 * @param contenderId
	 * @param ideaTitle
	 * @param ideaDescription
	 */
	@PostMapping("/contender/idea/add/{contenderId}/{ideaTitle}/{ideaDescription}")
	public void addIdeaToManifesto(@PathVariable("contenderId") int contenderId, 
			@PathVariable("ideaTitle") String ideaTitle, 
			@PathVariable("ideaDescription") String ideaDescription)
	{
		cityElectionService.addIdeaToManifesto(contenderId, ideaTitle, ideaDescription);
	}
	
	/**
	 * <p>Post mapping to rate an idea.</p>
	 * <p>This method can be tested from a REST Client such as postman with
	 *  a URL such as http://localhost:8080/api/contender/idea/rate/1/3/9  
	 *  You need to supply the ID of the citizen who is adding the rating, the rating id and the rating value.</p>
	 * @param citizenId
	 * @param ideaId
	 * @param rating
	 */
	@PostMapping("/contender/idea/rate/{citizenId}/{ideaId}/{rating}")
	public void rateIdea(@PathVariable("citizenId") int citizenId, 
			@PathVariable("ideaId") int ideaId, 
			@PathVariable("rating") int rating)
	{
		cityElectionService.rateIdea(citizenId, ideaId, rating);
	}

	/**
	 * <p>Post mapping to remove the rating for an idea.</p>
	 * <p>This method can be tested from a REST Client such as postman with
	 *  a URL such as http://localhost:8080/api/citizen/rating/remove/1/3  
	 *  You need to supply the ID of the citizen who owns the rating, and the rating id.</p>
	 * @param citizenId
	 * @param ratingId
	 */
	@PostMapping("/citizen/rating/remove/{citizenId}/{ratingId}")
	public void removeRating(@PathVariable("citizenId") int citizenId, 
			@PathVariable("ratingId") int ratingId)
	{
		cityElectionService.removeRating(citizenId, ratingId);
	}

	/**
	 * <p>Post mapping to get the final result for the election.</p>
	 * <p>This method can be tested from a browser wit the URL:
	 *  http://localhost:8080/api/election/winner/get  
	 * @return
	 */
	@GetMapping("/election/winner/get")
	public Person getContenderManifesto()
	{
		return cityElectionService.determinElectionWinner();
		
	}
	
	/** 
	 * <p>Exception Handler to return a ResponseEntity to the caller for PersonNotFoundException </p>
	 * <p>This will contain basic error information which can be passed back to the caller.</p>
	 * @param ex
	 * @return
	 */
	@ExceptionHandler
	public ResponseEntity<ElectionErrorResponse> handleException(PersonNotFoundException ex)
	{
		ElectionErrorResponse error = new ElectionErrorResponse();
		
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(ex.getMessage());
		error.setTimestamp(System.currentTimeMillis());
		
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);		
	}
	
	/** 
	 * <p>Exception Handler to return a ResponseEntity to the caller for ManifestoNotFoundException </p>
	 * <p>This will contain basic error information which can be passed back to the caller.</p>
	 * @param ex
	 * @return
	 */
	@ExceptionHandler
	public ResponseEntity<ElectionErrorResponse> handleException(ManifestoNotFoundException ex)
	{
		ElectionErrorResponse error = new ElectionErrorResponse();
		
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(ex.getMessage());
		error.setTimestamp(System.currentTimeMillis());
		
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);		
	}

	/** 
	 * <p>Exception Handler to return a ResponseEntity to the caller for IdeaNotFoundException </p>
	 * <p>This will contain basic error information which can be passed back to the caller.</p>
	 * @param ex
	 * @return
	 */
	@ExceptionHandler
	public ResponseEntity<ElectionErrorResponse> handleException(IdeaNotFoundException ex)
	{
		ElectionErrorResponse error = new ElectionErrorResponse();
		
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(ex.getMessage());
		error.setTimestamp(System.currentTimeMillis());
		
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);		
	}
	
	/** 
	 * <p>Exception Handler to return a ResponseEntity to the caller for General Exceptions </p>
	 * <p>This will contain basic error information which can be passed back to the caller.</p>
	 * @param ex
	 * @return
	 */
	@ExceptionHandler
	public ResponseEntity<ElectionErrorResponse> handleException(Exception ex)
	{
		ElectionErrorResponse error = new ElectionErrorResponse();
		
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(ex.getMessage());
		error.setTimestamp(System.currentTimeMillis());
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);		
	}

}

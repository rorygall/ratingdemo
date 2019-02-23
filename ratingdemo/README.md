============
Election Rating Application

==============

This project is setup as a Spring Boot REST API.
You should be able to run this as a spring boot app as it should include its own server instance.

The reason it is created as a Rest API is because without a user interface it is still possible to view and input 
data. With more time available a user interface is probably a good next step.

For now the data returned will be in JSON format which should be readable from a Browser or 
a REST client such as Postman.

Certain rest calls can be made via a Browser to view the data for people in the system as well as manifestos.
Manifestos will include ideas and ratings.
To post new data to the API you should use a REST client.

Instructions to test the various API calls are below.

================

Notes:
1. The main point of entry to start following the flow of the application is the CityElectionRestController class in package com.electioncouncil.ratingdemo.rest.
It should be straight forward to follow the different mappings and the code that executes to perform the required functionality.
Unfortunately I didnt have enough time to perform a lot of testing but I think the application will work well enough as is with the sample database provided. 


2. I decided to treat both citizens and contenders as people as they will share a lot of the same functionality. 
There is an interface to represent a person, IPerson, and an implementation class to implement its  methods.
There is a Citizen interface to represent the functionality unique to citizens and similarly a Contender interface for the functionality
unique to contenders.

3. 2. Due to time restrictions, the functionality for citizen and contender is not fully implemented. Calls to nominate a contender and post 
an idea (et. al.) should really be encapsulated in the implementation methods of the interfaces but I didn't get around to making those updates. If I had more time I believe that would be a more elegant approach.

4. The concept of followers for a contender has not been implemented fully. Currently there is a hardcoded list of certain people to act as followers.  If I had enough time my approach would be to create a table in the database with columns for the contender and followers. This would be a one-to-many or perhaps even many-to-many mapping to the person table.  
The Contender class would then contain a list of followers.

5. There is some exception handling but not a comprehensive implementation. A nice way to enhance this would be to maybe use AOP and a ControlerAdvice
to handle different Exceptions thrown.

6. I have included a seeded database dump with some sample data and scripts to create the required database tables;

=====================

Using the Application

1. Restore the dump file to create the database and insert some dummy data 
2. In the application.properties file the database username and password need to be updated to your own details.
2. Run the application
3. To view the people in the system you can use a URL such as this from your local machine: http://localhost:8080/api/people
4. You can view the winner of the election using this URL: http://localhost:8080/api/election/winner/get
With the sample data provided there should be enough information to display a winner.
4. To nominate a contender you can use a POST from a REST client , e.g. postman, with this url : http://localhost:8080/api/citizen/nomiate/1 . 
The 1 can be replaced with any valid id of person in the system.
5. To post a manifesto , from a REST client use the url: http://localhost:8080/api/contender/manifesto/add/2/this is a test manifesto .
The 2 in the example url represents the id of the contender and the last param would be the synopsis for the manifesto.
6. To add an idea to a manifesto, again with a REST client, use this URL http://localhost:8080/api/contender/idea/add/1/My Idea/A Description of my idea
In this URL you provice the id of the contender, note this is not the id of the manifesto. You also provide a Title and a description for the idea.
7. You can rate an idea, again with a REST client, using this url http://localhost:8080/api/contender/idea/rate/6/5/9
Here you provide the id of the citizen posting the rating followed by the id of the idea and then the value for the rating, i.e. 1 to 10. 
8. You can remove a rating for a citizen with the URL, again from a REST  client,  http://localhost:8080/api/citizen/rating/remove/{citizenId}/{ratingId}
here you need to provide the id of the citizen that placed the rating and the id of the rating to be removed.


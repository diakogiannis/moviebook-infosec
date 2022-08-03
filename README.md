# Social Media Platform PoC

Demo project for UEL security post graduate paper, yet​ ​another
social​ ​sharing​ ​platform​ ​where​ ​users​ ​can​ ​share​ ​their​ ​favorite​ ​movies.

[![Quality gate](https://sonarcloud.io/api/project_badges/quality_gate?project=diakogiannis_moviebook-infosec)](https://sonarcloud.io/dashboard?id=diakogiannis_moviebook)


## Introduction
This application was create within the scope of of my graduate paper. The main programming framework used is **Spring Boot 2.5.9.RELEASE** with **Thymeleaf**. 
The dependencies were check for vulnerabilities using the OWASP plugin and as expected some known  were identified that do not affect the project at the current scope.
Other framework/libraries used are Lombok and Mapstruct. The project was primarily developed using **openjdk-17**. 
 
## Installation
The following methods can be used  
 
### Docker File
A Docker file is provided with the source code.  

#### Requirements
Docker must be installed

#### Procedure
Navigate to the project folder and run `docker build -t moviebook .` in order to build the docker image. Afterwards you can run the application with `docker run -p 8080:8080 moviebook`.  
After the application loads just navigate to localhost:8080 and start using the application. 

### Jar File
A ready made jar file is provided  in `./target/moviebook-runnable.jar`

#### Requirements
Java openjdk-17 must be installed

#### Procedure
Navigate to project folder and you can start the application by `java -jar target/moviebook-runnable.jar` 
After the application loads just navigate to localhost:8080 and start using the application.

### Build from source
You can always build the project from scratch 

#### Requirements
Java openjdk-11 must be installed and also maven must be installed. Alternatively the maven wrapper included can be used but it is not thoroughly tested.

#### Procedure
Navigate to project folder and you can start the application by `mvn spring-boot:run` 
After the application loads just navigate to localhost:8080 and start using the application.

## Testing
### Initial Data
The application has three files containg demo data that are imported during startup
* `./src/main/resources/json/movies.json` that contains Harry Potter Movies
* `./src/main/resources/json/rating.json` that contains Movie Ratings from users
* `./src/main/resources/json/users.json` that contains the application users  
Initialli two users are created   
* username `alexius` with password `password`
* username `jdoe` with password `password` 

### Unit Testing
#### Test Creation and Coverage
The application is approx *60%* covered by unit tests. Test have been created in order to focus on key parts of the application such as  
* User Creation/Registration/Login
* Movie Creation
* Rating Creation
* Potential Malicious usage such as Voting on an non-existant movie or self voting
* Security check for methods and endpoints   

The junit tests are using in some cases the initial data imported above and they also test the import procedure as well.

#### Unit Testing Known Issues
Mapstruct has an issue regarding context loading/wiring without MockMVC. This means that in order to separate the tests in logical packages the MVC context is loaded 3 times during build. The delay of the build is trivial so this method was preferred instead of either putting all tests in a single file (!) or troubleshooting the Mapstruct issue.  

### Integration Testing
Some very basic integration tests exists that check the application is starting correctly and security is working

## Structure/Modeling
### Authentication Modeling
#### Introduction
The application relies on Spring Security JDBC for the authentication procedure. The effort was kept to a minimum so a basic Users-Authorities model was created as Spring Security demanded. 
#### Extra User Attributes
Extra attributes that needed like firstname/lastname was placed inside the table. In a real world scenario propably an external provider like Okta would have been used and the extra user attributes would have been stored there and propagated to the _Principal_ Object. Since it was a big effort for this scope to create a custom UserDetails Object and then create a custom authentication and authorisation provider and store everything to the User Pricipal, I use an UserDetailsDTO and store is to the session after the user login in order to simulate what stated before. For data integrity i fetch the user data just before a movie creation.

You may notice three fields
* usersId
* username
* publicIdentifier

That seem, to be redundant but I will soon describe that it's not. Since Hibernate was used and the application is a PoC thus no specific RDBMS with specific database design exits in order to map the entities on, I left Hibernate handle the creation of the the tables thus I needed a generated value column in order for Hibernate to propagate the FKs correctly, so usersId was used for that reason. 

Username is marked as `@NaturalKey` in order to take advantage of Hibernates caching capabilities and it is used as a key in the internal application procedures. 

As a common practice and since a potential security issue may occur if by any reason an application exposes usernames leading to DoS attacks etc, when a user is created, a UUID is generated and stored in field publicIdentifier. This PublicIdentifier is used in order to refer to the user in urls.

Both Username and publicIdentifier are indexed properly. 

### Database Structure
The database is structured  with two tables, besides the authorisation model of course, MOVIE and RATING

MOVIE

|FIELD | TYPE |	NULL | 	KEY  |	DEFAULT  |
|------|:-----|:----|:----|:----|
|MOVIE_ID|	BIGINT(19)|	NO|	PRI|	NULL|
|DESCRIPTION|	CLOB(2147483647)|	YES|		NULL|
|HATES|	BIGINT(19)|	YES|		NULL|
|LIKES|	BIGINT(19)|	YES	|	NULL|
|PUBLICATION_DATE|	TIMESTAMP(26)|	YES|		NULL|
|TITLE|	VARCHAR(255)|	YES|		NULL|
|FK_USER|	BIGINT(19)|	YES|		NULL|
 
 * MovieId autogenerated field for movieId
 * Description Movie Description
 * Hates total number of hates
 * Likes total number of likes
 * PublicationDate autogenerated publication date
 * Title movie title
 * FK_USER reference to the user that created it
 
 Please note that I chose to keep likes and hates in the movie table because I wanted to avoid any aggregation and calculation during select for performance reasons.
 
 RATING
 
 |FIELD|  	TYPE|  	NULL|  	KEY|  	DEFAULT  |
 |------|:-----|:----|:----|:----|
 |RATING_ID|	BIGINT(19)|	NO|	PRI|	NULL|
 |IS_LIKE|	BOOLEAN(1)|	YES|		NULL|
 |RATING_DATE|	TIMESTAMP(26)|	YES|		NULL|
 |FK_MOVIE	|BIGINT(19)|	YES		|NULL|
 |USER_USER_ID|	BIGINT(19)|	YES|		NULL|
 
  * RATING_ID autogenerated field for id
  * IS_LIKE flag wheather to mark if the particular rating is like or hate. True is like, Flase is hae
  * RATING_DATE autogenerated  date
  * FK_MOVIE reference to the movie the rating is for
  * USER_USER_ID reference to the user that created it
  
  Column names could be better but as I said before they are autogenerated by Hibernate.
  
  
  ## Application Flow
  ### Unauthorised User calls home
  The application selects with natural order the data from MOVIE Table. In production and large scale environments incremental loading and caching should have been used.
  ### Any User requests sorting
  The sorting field is validated through an Enum and the appropiate query is called
  ### Unauthorised User creates and account
  The application queries by username if that exists a checked exception is thrown and the user is notified to change his username. This exposes a username in the database and a captcha could have been used in order to avoid username phishing. If everything is fine a user is created and the application goes to home screen.
  ### Authorised User calls home
  The application selects with natural order the data from MOVIE Table. The application selects the users rating in order to display and filter the voting buttons properly.
  ### Authorised User creates a Movie
  The application selects user data from DB. This is happening for mainly two reasons. First the lack of userDetails in UserPrincipal but also, if the application supported user profile editing, the profile could have been alter from another device or browser than the one used for saving the movie resulting in possible data integrity issues.   
  ### Authorised User rates a Movie
  The application validates the rating action with an Enum. The actions are 
  * Like, for liking a movie
  * Hate, for hating a movie
  * Undo, for undoing a vote regardless it the initial was like or hate

**The application by principal treats all user input as NOT TRUSTED** thus in **every vote** the application checks if the user tries to rate a movie that himself made, the previous vote -if exists- and disregards in the undo action whether the vote is reported initially as like or hate.
#### First Time vote
  The application does the check mentioned above and casts the vote
#### Change vote
  The application does the check mentioned above, finds out what was the previous vote and mirrors the result by updating the rating and Movile like/hate calculation. 
#### Undo vote
  The application does the check mentioned above, finds out what was the previous vote, deducts the vote from movie and removes the rating entry. 
  
## Code Quality
As mentioned before the project if 60% covered by unit tests with emphasis to core features. Besides that, the project features *0%* code duplication and according to Sonarqube *zero* major or above issues. Sonar reports a false positive regarding a controller method that abstractly treats 4xx and 5xx errors and servers both GET and POST by design.       

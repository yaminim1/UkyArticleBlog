## UKy Sample Blog


##Aim:##
 To create a simple forum/blog to post Articles and add comments to it.  
Each user may post an Article   
User may edit/delete their own posts  
Each may see all articles posted by other users.  
Each may read all messages posted related to the article.  
Administrator can approve/reject articles and comments.  
Simple search can be performed. The application searches the keyword in title and description of the article and returns the articles.  

#Technologies Used:#

* Spring Boot   
* Spring Security  
* Spring Data JPA  
* MYSQL  
* Apache Tomcat  
* Maven
* Java  
* BootStrap  

##Project Structure:##
The SpringBootBlogApplication.java has the main method from where the spring boots starts to run.  
Application.properties- src/main/resources  
Table.sql-contains all the sql statements required for the application.  
ArticleController has the rest API calls.  
ArticleServiceImpl has the implemented functions.  
#Project Dependency:#  
The project dependencies are present in pom.xml. All the jars required for the project are taken care by maven. Pom.xml has dependencies related to spring data JPA, spring Boot, spring Security, mysql connector and others.  
#Tables:#
The required data and tables can be created by executing the queries present in table.sql  
* User – Contains all the user details  
* Role – Contains 2 roles Admin and User  
* User_role – Maps the users with different roles  
* Article – Article table has all the article data like title, description, createdBy, createdDate, reply_count, reply Flag, verify flag, del flag,etc.  
*Reply – This table contains data related to the comments.    
The User table contains two users with role admin and user.    
	Role Admin (id/pwd) – admin/admin  
	Role User (id/pwd) – yamini/yamini  
#Configuration:#  
The configuration can be set up in src/main/application.properties.  
```
server.port=8085
spring.datasource.url=jdbc:mysql://localhost:3306/nov2017?useSSL=false
spring.datasource.username=root
spring.datasource.password =yamini123
spring.datasource.testWhileIdle=true
spring.datasource.validationQuery=SELECT 1
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.hibernate.naming-strategy=org.hibernate.cfg.ImprovedNamingStrategy
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp
logging.level.org.springframework.web=INFO
logging.level.org.springframework.security=DEBUG
logging.level.com.uk=DEBUG
```
The port on which the project runs is set to 8085.  
The project is deployed http://localhost:8085/  
The db details can be changed. spring.datasource.username, spring.datasource.password and the url need to be set according to the mysql setup.  

#Application Demo:#   
Go to the github path and download the zip file and import it as a maven project in your eclipse.  
Modify application.properties file with correct url, username, password for the MySQL database.  
Create the required data and tables by running the commands present in tables.sql.  
Right click on the project and go to maven->Update Project.  
Right click on the SpringBootBlogApplication.java and run as java application.  
The spring boot runs and shows that the application started on port 8085.  
Once the application is started. Go to Http://localhost:8085/login  



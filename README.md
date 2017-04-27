# Media Library
Media Library is an API RestFul used to request easily all medias available in a house.<br>
In fact, we can considerate Media Library like a register for all medias present in the house.<br>
The media manage by Media Library are :

| Media  | Statement |
| :-----:|-----------|
| Movies | Draft     |
| Musics | Forecast  |
| Games  | Forecast  |
| Books  | Forecast  |
| Comics | Forecast  |

The statement is a representation of the current state of development of each media :
- `Forecast` mean the media not working and appear in the future released.
- `Draft` mean the media is on "Work in Progress" statement and it's the object of the future released.
- `Release` mean the media worked correctly on the project, and normally, the api don't change, except for fix some bugs.

## Features
### About Movies

| Method | URL                                  | BODY  | Description                                                                           |
|:------:|--------------------------------------|-------|---------------------------------------------------------------------------------------|
| GET    | /media-library/movies/               | /     | Retrieve all Movies from the persistent system.                                       |
| GET    | /media-library/movies/search/{title} | /     | Search one movie by his title passed on parameter of the url.                         |
| POST   | /media-library/movies/               | Movie | Insert a new movie on persistent system.                                              |
| PUT    | /media-library/movies/{id}           | Movie | Update a movie on persistent system in function on the id receive from the url.       |
| DELETE | /media-library/movies/{id}           | /     | Delete a movie from the persistent system in function of the id receive from the url. |

## Commands
- `mvn spring-boot:run` : Deploy Media Library in localhost:8080/

## Authors
- Nicolas GILLE <nic.gille@gmail.com>

## Resources
### Useful links 
- Tutorial about jpa and hibernate : [http://blog.netgloo.com/2014/10/27/using-mysql-in-spring-boot-via-spring-data-jpa-and-hibernate/](http://blog.netgloo.com/2014/10/27/using-mysql-in-spring-boot-via-spring-data-jpa-and-hibernate/)
- Link about jpa query method nomenclature : [http://docs.spring.io/spring-data/data-jpa/docs/current/reference/html/#jpa.query-methods.query-creation](http://docs.spring.io/spring-data/data-jpa/docs/current/reference/html/#jpa.query-methods.query-creation)
- Example of API Rest in Spring Boot : [http://websystique.com/spring-boot/spring-boot-rest-api-example/](http://websystique.com/spring-boot/spring-boot-rest-api-example/)
- Problem about timezone of MySQL : [http://stackoverflow.com/questions/26515700/mysql-jdbc-driver-5-1-33-time-zone-issue](http://stackoverflow.com/questions/26515700/mysql-jdbc-driver-5-1-33-time-zone-issue)
- JPA help about Enumeration and Collection : [https://en.wikibooks.org/wiki/Java_Persistence/ElementCollection](https://en.wikibooks.org/wiki/Java_Persistence/ElementCollection) 

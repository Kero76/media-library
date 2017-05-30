# Media-Library
Media-Library is a RESTFUL API used to request easily all medias available in a house.<br>
It can be considerate as a *register* to check presence of movie, CD, book, ... on the house.<br>
Media manages by Media Library are :

| Media    | Statement |
| :-------:|-----------|
| Movies   | Release   |
| Series   | Release   |
| Cartoon  | Release   |
| Musics   | Release   |
| Games    | Release   |
| Books    | Release   |
| Comics   | Release   |

The statement is a representation of the current state of development of each media :
- `Forecast` mean the media not working and appear in the future released.
- `Draft` mean the media is on "Work in Progress" statement and it's the object of the future released.
- `Release` mean the media worked correctly on the project, and normally, the api don't change, except for fix some bugs.

## Features
### Path to call service

| Method | URL                                                  | BODY   | Description                                                                           |
|:------:|------------------------------------------------------|--------|---------------------------------------------------------------------------------------|
| GET    | /media-library/movies/                               | /      | Get all Movies from the persistent system.                                            |
| GET    | /media-library/movies/search/title/{title}           | /      | Search one Movie by his title.                                                        |
| POST   | /media-library/movies/                               | Movie  | Insert a new Movie on persistent system.                                              |
| PUT    | /media-library/movies/{id}                           | Movie  | Update a Movie on persistent system thanks to the id receive from the url.            |
| DELETE | /media-library/movies/{id}                           | /      | Delete a Movie from the persistent system thanks to the id receive from the url.      |
| GET    | /media-library/series/                               | /      | Get all Movies from the persistent system.                                            |
| GET    | /media-library/series/search/title/{title}           | /      | Search one Series by his title.                                                       |
| POST   | /media-library/series/                               | Series | Insert a new Series on persistent system.                                             |
| PUT    | /media-library/series/{id}                           | Series | Update a Series on persistent system thanks to the id receive from the url.           |
| DELETE | /media-library/series/{id}                           | /      | Delete a Series from the persistent system thanks to the id receive from the url.     |
| GET    | /media-library/actors/                               | /      | Get all Actors from the persistent system.                                            |
| GET    | /media-library/directors/                            | /      | Get all Directors from the persistent system.                                         |
| GET    | /media-library/producers/                            | /      | Get all Producers from the persistent system.                                         |
| GET    | /media-library/search/actor?fname=XXX&lname=YYY      | /      | Search one Actor by his first and last name.                                          |
| GET    | /media-library/search/director?fname=XXX&lname=YYY   | /      | Search one Director by his first and last name.                                       |
| GET    | /media-library/search/producer?fname=XXX&lname=YYY   | /      | Search one Producer by his first and last name.                                       |
 
## Commands
- `mvn spring-boot:run` : Deploy Media Library in localhost:8080/
- `mvn package` : Compile and generate .war.
- `mvn package` : Clean project.

## License 
Media-Library is under GPLv3 license.

## Changelog 
- V0.4 : CRUD Release - Released May 30, 2017
- V0.3 : Cartoon Release - Released May 18, 2017
- V0.2.1 : Person Update - Released May 11, 2017
- V0.2 : Refactoring Release - Released May 10, 2017
- V0.1.1 : Deployment Update - Released May 08, 2017
- V0.1 : Movie's Release - Released May 04, 2017

## Authors
- Nicolas GILLE <nic.gille@gmail.com>

## Resources
### Services used (in a future to get poster)
- The Movie DB API : [https://www.themoviedb.org/](https://www.themoviedb.org/)

### External libraries used
- Libraries about [ISO 639-1](https://en.wikipedia.org/wiki/ISO_639-1) : [https://github.com/TakahikoKawasaki/nv-i18n](https://github.com/TakahikoKawasaki/nv-i18n)

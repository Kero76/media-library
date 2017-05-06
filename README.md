# Media-Library
Media-Library is a RESTFUL API used to request easily all medias available in a house.<br>
It can be considerate as a *register* to check presence of movie, CD, book, ... on the house.<br>
Media manages by Media Library are :

| Media  | Statement |
| :-----:|-----------|
| Movies | Release   |
| Musics | Forecast  |
| Games  | Forecast  |
| Books  | Forecast  |
| Comics | Forecast  |

The statement is a representation of the current state of development of each media :
- `Forecast` mean the media not working and appear in the future released.
- `Draft` mean the media is on "Work in Progress" statement and it's the object of the future released.
- `Release` mean the media worked correctly on the project, and normally, the api don't change, except for fix some bugs.

## Features
### Movies
#### Movies Features

| Method | URL                                  | BODY  | Description                                                                           |
|:------:|--------------------------------------|-------|---------------------------------------------------------------------------------------|
| GET    | /media-library/movies/               | /     | Retrieve all Movies from the persistent system.                                       |
| GET    | /media-library/movies/search/{title} | /     | Search one Movie by his title passed on parameter of the url.                         |
| POST   | /media-library/movies/               | Movie | Insert a new Movie on persistent system.                                              |
| PUT    | /media-library/movies/{id}           | Movie | Update a Movie on persistent system in function on the id receive from the url.       |
| DELETE | /media-library/movies/{id}           | /     | Delete a Movie from the persistent system in function of the id receive from the url. |

#### Movies attributes
- [x] id : Identifier of the Movie with the following format `[0-9]*`.
- [x] title : Title of the Movie.
- [x] releaseDate : Date of release of the Movie. 
- [x] duration : Duration of the Movie in minute.
- [x] synopsis : Synopsis of the Movie.
- [x] main Actors : List of main actors of the Movie.
- [x] producers : List of producers of the Movie.
- [x] directors : List of directors of the Movie.
- [x] categories : List of genre of the Movie.
- [x] supports : List of support available for the Movie. Video Tape, DVD, Blu-Ray.
- [ ] poster : Poster of the Movie.
- [x] spoken languages : List of languages spoken available on the Movie. 
- [x] subtitle languages : List of languages subtitled available on the Movie.
- [x] Original title : Original title of the movie (For example _Ghostbusters_ is renamed _SOS Fantômes_ in French).
 
## Commands
- `mvn spring-boot:run` : Deploy Media Library in localhost:8080/
- `mvn package` : Compile and generate .war.
- `mvn package` : Clean project.

## License 
This software is under GPLv3 license.

## Authors
- Nicolas GILLE <nic.gille@gmail.com>

## Resources
### Services used (in a future to get poster)
- The Movie DB API : [https://www.themoviedb.org/](https://www.themoviedb.org/)

### External libraries used
- Libraries about [ISO 639-1](https://en.wikipedia.org/wiki/ISO_639-1) : [https://github.com/TakahikoKawasaki/nv-i18n](https://github.com/TakahikoKawasaki/nv-i18n)

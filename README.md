# Media-Library
Media-Library is a RESTFUL API used to request easily all medias available in a house.<br>
It can be considerate as a *register* to check presence of movie, CD, book, ... on the house.<br>
Media manages by Media Library are :

| Media  | Statement |
| :-----:|-----------|
| Movies | Release   |
| Series | Release   |
| Anime  | Forecast  |
| Musics | Forecast  |
| Games  | Forecast  |
| Books  | Forecast  |
| Comics | Forecast  |
| Mangas | Forecast  |

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

### Medias
#### Movies content
- [x] id : Identifier of the Movie with the following format `[0-9]*`.
- [x] title : Title of the Movie.
- [x] Original title : Original title of the movie (For example _Ghostbusters_ is renamed _SOS Fantômes_ in French).
- [x] releaseDate : Date of release of the Movie. 
- [x] duration : Duration of the Movie in minute.
- [x] synopsis : Synopsis of the Movie.
- [x] main Actors : List of main actors of the Movie.
- [x] producers : List of producers of the Movie.
- [x] directors : List of directors of the Movie.
- [x] genres : List of genre of the Movie.
- [x] supports : List of support available for the Movie. Video Tape, DVD, Blu-Ray.
- [ ] poster : Poster of the Movie.
- [x] spoken languages : List of languages spoken available on the Movie. 
- [x] subtitle languages : List of languages subtitled available on the Movie.

#### Series content
- [x] id : Identifier of the Series with the following format `[0-9]*`.
- [x] title : Title of the Series.
- [x] Original title : Original title of the movie (For example _Ghostbusters_ is renamed _SOS Fantômes_ in French).
- [x] synopsis : Synopsis of the Series.
- [x] main Actors : List of main actors of the Series.
- [x] producers : List of producers of the Series.
- [x] directors : List of directors of the Series.
- [x] genres : List of genre of the Series.
- [x] supports : List of support available for the Series. Video Tape, DVD, Blu-Ray.
- [x] spoken languages : List of languages spoken available on the Series. 
- [x] subtitle languages : List of languages subtitled available on the Series.
- [x] numberOfSeasons : Number of seasons for the series.
- [x] currentSeason : Current season of the series.
- [x] numberOfEpisode : Number of episode available during the season.
- [x] averageEpisodeRuntime : Average runtime for episodes on series (in minutes).
- [x] startDate : Date of first release of the series.
- [x] endDate : Date of the end of the series.
- [ ] poster : Poster of the Series.
 
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

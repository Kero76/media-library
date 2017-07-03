# Changelog 
## V1.0 : Media-Library Release - Released July 03, 2017

### Features
- Add Anime media.
- Add Parser for .csv and .tsv file to fill your service with data present on file.
- Add builder object to build Model object from data get by a parser.
- Complete enum `MediaGenre` with some new element.

### Bugs Fixed
- Fix bug on series controller when you get a series by his name.
- Fix bug about date format on Series `toString()`.
- Fix bug when you add an music album with the same name of another album previously insert on the service.

## V0.4 : Medias Release - Released May 30, 2017

### Features
- Add following medias :
  * Book
  * Comic
  * Music Album
  * Video Game
- Add some new elements on `MediaGenre`.
- Add some new elements on `MediaSupport`

## V0.3 : Cartoon Release - Released May 18, 2017

### Features
- Add media _Cartoon_ to manage Cartoon on app.
- Add `ROMANTIC` on enum `VideoGenre`.
- Add new method to search Series on `SeriesRepository`
- Add many data on class `DataUtils` to test Media-Library.

### Bugs Fixed
- Fix bug when you try to add new Series on Media-Library.

---

## V0.2.1 : Person Update - Released May 11, 2017

### Features
- Add Interface `IPerson` and abstract class `Person` to manage _Human_ present on Media-Library like Actor, Director, Producer, ...
- Refactor constructors of classes `Movies` and `Series` to change parameters order and regroup them by specific theme.
- Add `Digital` value on `VideoSupport`.
- Update class `Series` with new attribute : `maxEpisodes`.

### Bugs Fixed
- Fix bug about same first name or same last name for Person can't saved in Database. 

---

## V0.2 : Refactoring Release - Released May 10, 2017
Refactor all code to improve media extensibility.

### Features
- Refactor project to improve extensibility of future media.
- Manage Series.

---

## V0.1.1 : Deployment Update - Released May 08, 2017
Fix bug about deployment on Tomcat instance.

### Features 
- Get all Actors present on Database.
- Get all Directors present on Database.
- Get all Producers present on Database.

### Bugs Fixed
- Fix bug about Tomcat deployment.

---

## V0.1 : Movie's Release - Released May 04, 2017
This release allows the management of Movies by Media-Library.

### Features 
- Manage Movie with the following attributes :
- Save data on persistent system.
- Request Restful service.

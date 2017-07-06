# Media Library

## What is Media Library ?
**Media Library** is a _REST API_ used to request easily all medias available in a house.
In fact, **Media Library** concentrate all medias available on you house in one application.
Thanks that, you can check if you have a media before buying new book, DVD, CD, ... to avoid duplicates medias.

## Why "Media" are supported by Media Library
Currently, **Media Library** supports different type of Media, you can get all medias supported by **Media Library** below :

| Media      | Statement |
| :---------:|-----------|
| Movies     | Release   |
| Cartoons   | Release   |
| Animes     | Release   |
| Series     | Release   |
| Musics     | Release   |
| VideoGames | Release   |
| Books      | Release   |
| Comics     | Release   |

The statement is a representation of the current state of development of each media :
- _Forecast_ mean the media not working and appear in the future released.
- _Draft_ mean the media is on "Work in Progress" statement and it's the object of the future released.
- _Release_ mean the media worked correctly on the project, and normally, the api don't change, except for fix some bugs.

## About the Parser available on Media Library
In fact, **Media Library** have different parser for .csv and .tsv files.
If you write your data on a spreadsheet with your information, you can export these data on .csv or .tsv file to fill your **Media Library** service.
So, these file respect some standards describe here.

### About "missing data"
In fact, you can have a media with some hole for the information, so, you just replace these missing data by the following snippet : `MISSING_ARGUMENTS`.
Parser detects automatically these flag and transform it into a right data who corresponding at empty data.

### About end date of Series, ...
If the series or animes are currently in production, you must replace the date of end by the keyword `pending` to indicate on parser that data not present.
So, the parser replace these keyword by the current date, so can't forget to change it when the media is finished ;).

## Contributions
You can get more information about the contribution on reading [CONTRIBUTING.md](https://github.com/Kero76/media-library/blob/master/CONTRIBUTING.md)

## Changelog 
- v1.0 : Media-Library Release - Released July 03, 2017
- V0.4 : CRUD Release - Released May 30, 2017
- V0.3 : Cartoon Release - Released May 18, 2017
- V0.2.1 : Person Update - Released May 11, 2017
- V0.2 : Refactoring Release - Released May 10, 2017
- V0.1.1 : Deployment Update - Released May 08, 2017
- V0.1 : Movie's Release - Released May 04, 2017

You can get more information about the modification of the project here : [Changelog.md](https://github.com/Kero76/media-library/blob/master/CHANGELOG.md)

## License 
Media-Library is under GPLv3 license.

## Authors
- Nicolas GILLE : <nic.gille@gmail.com>

## External Libraries used in project
- Libraries about [ISO 639-1](https://en.wikipedia.org/wiki/ISO_639-1) : [https://github.com/TakahikoKawasaki/nv-i18n](https://github.com/TakahikoKawasaki/nv-i18n)

## Resources
- Information about the [ISO 639-1](https://www.iso.org/iso-639-language-codes.html) for the language code available on the project.

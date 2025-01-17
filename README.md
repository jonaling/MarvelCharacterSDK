# MarvelCharacterSDK
Marvel Character SDK created to obtain a list of characters and cache frequent results. Makes use of Springboot, Caffine and Loombok to create a lightweight Marvel Character SDK be imported for use.
[Marvel API]{https://developer.marvel.com/} is used with the baseline being the the [/v1/public/characters]{https://developer.marvel.com/docs} endpoint.

##Installation
To create a jar file, simply have [gradle]{https://gradle.org/install/} installed locally, go to the directory and run:
```bash
./gradlew clean build
```

The jar file will be created in the build/generated folder.

##Usage

To use the SDK, one must first build the CharacterRequest body as such:

```java
CharactersRequest request = new CharactersRequest.Builder("Your public Key",
        		"Your private key")
                .build();
```
Where the first two fields are public and private key respectively which are provided by having a marvel developer account at https://developer.marvel.com/ .

Then after the request is built, you must call the service method :

```java
@Autowired
CharacterService characterService;

 charactersService.getCharacterDetails( Character Request);
```

There are a number of optional field:

|Field | Type (in Java) | Example | Description |
|----------|----------|----------|-------|
| .name(name)   | String   | Spider-man   |Return only characters matching the specified full character name  |
| .nameStartsWith( name)    | String   | Spi  | Return characters with names that begin with the specified string|
| .modifiedSince( date)    | LocalDate  | LocalDate.of(2020, 1, 1)   |Return only characters which have been modified since the specified date. |
|.comicsId( list of ids) | List<String> | [123,345] | Return only characters which appear in the specified comics (number at end of referenceURI of issues results) |
| .seriesId( lists of ids) | List<String> | [123,345] | Return only characters which appear the specified series ( number at end of referenceURI of series results) |
|.events.ID( lists of ids) | List<String> |[123,345] |  Return only characters which appear in the specified events ( number at end of referenceURI of events results)|
|.storyIds(lists of ids) | List<String> | [123,345] | Return only characters which appear the specified stories ( number at end of referenceURI of stories results) |
|.orderBy( orderByEnum) | Enum orderByEnum|orderByEnum.nameAsc |Order the result set by a field or fields. (nameAsc , nameDsc , modifiedAsc, modifiedDsc) |
|.limit | int | 100 | Limit the result set to the specified number of resources. Capped to 100 |
|.offset | int |3| Skip the specified number of resources in the result set.|
|.useCache| Boolean | False | Make use of Caffine InHeap memory cache. True by default.|

eg.

```java
ArrayList<String> comicIds = new ArrayList<String>();
    	comicIds.add("76353");
    	comicIds.add("76351");
        CharactersRequest request = new CharactersRequest.Builder("APIkey","PrivateKey")
        		.modifiedSince(LocalDate.of(2020, 1, 1))
        		.comicsIds(comicIds)
        		.orderBy(OrderByEnum.nameDsc)
        		.limit(20)
                .build();
CharactersResponse characterDetails;
		try {
			characterDetails = charactersService.getCharacterDetails(request);
			System.out.println(characterDetails.getData().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
        
```

##Response

The response is in the following object formats which contain getters and toString():

```java
public class CharactersResponse {
	public int code; //status code
	public String status; //status message
	public String copyright; // marvel copyright
	public String attributionText; //copyright attribution text
	public String attributionHTML; //copygith attribution html
	public String etag; // etag used for versioning of requests
	public Data data; // data as shown in the following class
}

public class Data {
	public int offset; // The requested offset (number of skipped results) of the call.,
    public int limit; //The requested result limit.,
    public int total; //The total number of resources available given the current filter set.,
    public int count; //The total number of results returned by this call.,
    public List<Result> results; //The list of characters returned by the call. Result class shown below
}

public class Result {
	public int id; //The unique ID of the character resource.,
	public String name; //The name of the character
	public String description; // A short bio or description of the character
	public Date modified; //The date the resource was most recently modified
	public Thumbnail thumbnail; //The canonical URL identifier for this resource
	public String resourceURI; // A set of public web site URLs for the resource
	public Comics comics; //The representative image for this character
	public Series series; // A resource list containing comics which feature this character
	public Stories stories; //A resource list of stories in which this character appears
	public Events events; // A resource list of events in which this character appears
	public List<Url> urls; //A resource list of series in which this character appears
}

public class Thumbnail {
	public String path; //A URL path to the thumbnail.
	public String extension; //A picture format
}

//Note Stories, Series and event classes follow the same format.
public class Comics {
	public int available; //he number of total available issues in this list. Will always be greater than or equal to the "returned" value.
	public String collectionURI; //The path to the full list of issues in this collection.
	public List<Item> items; // The list of returned issues in this collection.
	public int returned; //The number of issues returned in this collection (up to 20).
}

public class Item {
	public String resourceURI; // A full URL (including scheme, domain, and path).
    public String name; // A text identifier for the URL.
    public String type; //The type of the story (null, interior or cover).
}

public class Url {
	public String type; //A text identifier for the URL.
	public String url; //A full URL (including scheme, domain, and path)
}

```

Example of rull call is as follows:

```java
CharactersRequest request = new CharactersRequest.Builder("publicKey",
        		"privateKey")
        		.modifiedSince(LocalDate.of(2020, 1, 1))
        		.comicsIds(comicIds)
        		.orderBy(OrderByEnum.nameDsc)
        		.limit(20)
                .build();
                
 CharactersResponse characterDetails;
		try {
			characterDetails = charactersService.getCharacterDetails(request);
			System.out.println(characterDetails.getData().getResults().get(0).getName());
			System.out.println(characterDetails.getData().getResults().get(0).toString());

```

Will print out:

```java
Wave (Wave)
Result(id=1017853, name=Wave (Wave), description=, modified=Sat Aug 28 05:43:56 HKT 2021, thumbnail=Thumbnail(path=http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available, extension=jpg), resourceURI=http://gateway.marvel.com/v1/public/characters/1017853, comics=Comics(available=16, collectionURI=http://gateway.marvel.com/v1/public/characters/1017853/comics, items=[Item(resourceURI=http://gateway.marvel.com/v1/public/comics/76349, name=Aero (2019) #1, type=null), Item(resourceURI=http://gateway.marvel.com/v1/public/comics/76350, name=Aero (2019) #2, type=null), Item(resourceURI=http://gateway.marvel.com/v1/public/comics/76351, name=Aero (2019) #3, type=null), Item(resourceURI=http://gateway.marvel.com/v1/public/comics/76352, name=Aero (2019) #4, type=null), Item(resourceURI=http://gateway.marvel.com/v1/public/comics/76353, name=Aero (2019) #5, type=null), Item(resourceURI=http://gateway.marvel.com/v1/public/comics/76354, name=Aero (2019) #6, type=null), Item(resourceURI=http://gateway.marvel.com/v1/public/comics/77001, name=Agents of Atlas (2019) #1, type=null), Item(resourceURI=http://gateway.marvel.com/v1/public/comics/77003, name=Agents of Atlas (2019) #3, type=null), Item(resourceURI=http://gateway.marvel.com/v1/public/comics/77004, name=Agents of Atlas (2019) #4, type=null), Item(resourceURI=http://gateway.marvel.com/v1/public/comics/77005, name=Agents of Atlas (2019) #5, type=null), Item(resourceURI=http://gateway.marvel.com/v1/public/comics/83989, name=Atlantis Attacks (2020) #1, type=null), Item(resourceURI=http://gateway.marvel.com/v1/public/comics/83993, name=Atlantis Attacks (2020) #2, type=null), Item(resourceURI=http://gateway.marvel.com/v1/public/comics/83994, name=Atlantis Attacks (2020) #3, type=null), Item(resourceURI=http://gateway.marvel.com/v1/public/comics/83995, name=Atlantis Attacks (2020) #4, type=null), Item(resourceURI=http://gateway.marvel.com/v1/public/comics/83996, name=Atlantis Attacks (2020) #5, type=null), Item(resourceURI=http://gateway.marvel.com/v1/public/comics/106112, name=Marvel's Voices Infinity Comic (2022) #50, type=null)], returned=16), series=Series(available=4, collectionURI=http://gateway.marvel.com/v1/public/characters/1017853/series, items=[Item(resourceURI=http://gateway.marvel.com/v1/public/series/27392, name=Aero (2019 - 2020), type=null), Item(resourceURI=http://gateway.marvel.com/v1/public/series/27624, name=Agents of Atlas (2019), type=null), Item(resourceURI=http://gateway.marvel.com/v1/public/series/29600, name=Atlantis Attacks (2020), type=null), Item(resourceURI=http://gateway.marvel.com/v1/public/series/34353, name=Marvel's Voices Infinity Comic (2022 - 2023), type=null)], returned=4), stories=MarvelSDK.character.Model.Stories@4ae280da, events=Events(available=0, collectionURI=http://gateway.marvel.com/v1/public/characters/1017853/events, items=[], returned=0), urls=[Url(type=detail, url=http://marvel.com/comics/characters/1017853/wave_wave?utm_campaign=apiRef&utm_source=b05a5801fc5def0dfbfc858bf84b23bd), Url(type=comiclink, url=http://marvel.com/comics/characters/1017853/wave_wave?utm_campaign=apiRef&utm_source=b05a5801fc5def0dfbfc858bf84b23bd)])
```


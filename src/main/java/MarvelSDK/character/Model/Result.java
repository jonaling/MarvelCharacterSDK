package MarvelSDK.character.Model;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
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

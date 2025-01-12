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
	public int id;
	public String name;
	public String description;
	public Date modified;
	public Thumbnail thumbnail;
	public String resourceURI;
	public Comics comics;
	public Series series;
	public Stories stories;
	public Events events;
	public List<Url> urls;
}

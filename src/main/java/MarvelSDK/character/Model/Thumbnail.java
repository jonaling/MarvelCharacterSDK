package MarvelSDK.character.Model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Thumbnail {
	public String path; //A URL path to the thumbnail.
	public String extension; //A picture format
}

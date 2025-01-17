package MarvelSDK.character.Model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CharactersResponse {
	public int code; //status code
	public String status; //status message
	public String copyright; // marvel copyright
	public String attributionText; //copyright attribution text
	public String attributionHTML; //copyright attribution html
	public String etag; // etag used for versioning of requests
	public Data data;
}

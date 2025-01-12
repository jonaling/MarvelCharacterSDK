package MarvelSDK.character.Model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CharactersResponse {
	public int code;
	public String status;
	public String copyright;
	public String attributionText;
	public String attributionHTML;
	public String etag;
	public Data data;
}

package MarvelSDK.character.Model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Url {
	public String type; //A text identifier for the URL.
	public String url; //A full URL (including scheme, domain, and path)
}

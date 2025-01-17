package MarvelSDK.character.Model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Item {
	public String resourceURI; // A full URL (including scheme, domain, and path).
    public String name; // A text identifier for the URL.
    public String type; //The type of the story (null, interior or cover).
}

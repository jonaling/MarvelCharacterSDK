package MarvelSDK.character.Model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Comics {
	public int available;
	public String collectionURI;
	public List<Item> items;
	public int returned;
}

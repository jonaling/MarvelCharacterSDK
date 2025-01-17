package MarvelSDK.character.Model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Comics {
	public int available; //he number of total available issues in this list. Will always be greater than or equal to the "returned" value.
	public String collectionURI; //The path to the full list of issues in this collection.
	public List<Item> items; // The list of returned issues in this collection.
	public int returned; //The number of issues returned in this collection (up to 20).
}

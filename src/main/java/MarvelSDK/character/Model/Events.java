package MarvelSDK.character.Model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Events {
	public int available;
    public String collectionURI;
    public List<Object> items;
    public int returned;
}

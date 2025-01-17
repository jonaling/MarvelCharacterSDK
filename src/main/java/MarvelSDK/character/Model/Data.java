package MarvelSDK.character.Model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Data {
	public int offset; // The requested offset (number of skipped results) of the call.,
    public int limit; //The requested result limit.,
    public int total; //The total number of resources available given the current filter set.,
    public int count; //The total number of results returned by this call.,
    public List<Result> results; //The list of characters returned by the call.
}

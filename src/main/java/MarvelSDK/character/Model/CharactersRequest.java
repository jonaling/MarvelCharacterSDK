package MarvelSDK.character.Model;

import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CharactersRequest {
	public String apiKey;
	public String hash;
	@Builder.Default public String tn = "" +System.currentTimeMillis();
	public String name;
	public String nameStartsWith;
	public Date modifiedSince;
	public List<Integer> comicsIds;
	public List<Integer> seriesIds;
	public List<Integer> eventIds;
	public List<Integer> storyIds;
	public OrderByEnum orderBy;
	public int limit;
	public int offset;
}

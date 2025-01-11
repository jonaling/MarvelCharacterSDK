package MarvelSDK.character.Model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Data {
	public int offset;
    public int limit;
    public int total;
    public int count;
    public List<Result> results;
}

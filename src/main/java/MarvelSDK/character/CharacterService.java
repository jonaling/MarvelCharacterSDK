package MarvelSDK.character;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import MarvelSDK.character.Model.CharactersRequest;
import MarvelSDK.character.Model.CharactersResponse;

@Service
public class CharacterService {
	
	private final RestTemplate restTemplate;
	
	@Value("${marvel.api.url}")
	private String marvelApiUrl;
	
	public CharacterService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
	
	public CharactersResponse getCharacterDetails(CharactersRequest request) {
		
		
		String url = String.format("%s/characters?apikey=%s", 
	            marvelApiUrl, 
	             request.apiKey);
		
		if(request.comicsIds != null && !request.comicsIds.isEmpty() ) {
			
		}
	        return restTemplate.getForObject(url, CharactersResponse.class);
	}

}

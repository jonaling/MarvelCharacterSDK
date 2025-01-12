package MarvelSDK.character;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import MarvelSDK.character.Exceptions.ApiExceptionHandler;
import MarvelSDK.character.Model.CharactersRequest;
import MarvelSDK.character.Model.CharactersResponse;

@Service
public class CharacterService {

	private final RestTemplate restTemplate;

	@Value("${marvel.api.url}")
	private String marvelApiUrl;

	private CharactersRequestValidator validator = new CharactersRequestValidator();

	public CharacterService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public CharactersResponse getCharacterDetails(CharactersRequest request) throws Exception {
		try {

			validator.validate(request);

			String url = String.format("%s/characters?apikey=%s", marvelApiUrl, request.getApiKey());

			if(request.getNameStartsWith() != null) {
				url += String.format("&nameStartsWith=%s", request.getNameStartsWith());
			}
			if (request.getComicsIds() != null && !request.getComicsIds().isEmpty()) {

			}
			return restTemplate.getForObject(url, CharactersResponse.class);
		} catch (Exception e) {
			ApiExceptionHandler.handleException(e);
			return null;
		}
	}

}

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

			String url = String.format("%s/characters?apikey=%s&hash=%s&ts=%s", marvelApiUrl, request.getApiKey(),
					request.getHash(), request.getTn());

			url = addCharactersOptionalFields(url, request);
			return restTemplate.getForObject(url, CharactersResponse.class);
		} catch (Exception e) {
			ApiExceptionHandler.handleException(e);
			return null;
		}
	}

	//Filter By optional parameters
	private String addCharactersOptionalFields(String url, CharactersRequest request) {
		if (request.getName() != null) {
			url += String.format("&name=%s", request.getName());
		}
		if (request.getNameStartsWith() != null) {
			url += String.format("&nameStartsWith=%s", request.getNameStartsWith());
		}
		if (request.getModifiedSince() != null) {
			url += String.format("&modifiedSince=%s", request.getModifiedSince().toString());
		}
		if (request.getComicsIds() != null && !request.getComicsIds().isBlank()) {
			url += String.format("&comics=%s", request.getComicsIds());
		}
		if (request.getSeriesIds() != null && !request.getSeriesIds().isBlank()) {
			url += String.format("&series=%s", request.getSeriesIds());
		}
		if (request.getEventIds() != null && !request.getEventIds().isBlank()) {
			url += String.format("&events=%s", request.getEventIds());
		}
		if (request.getStoryIds() != null && !request.getStoryIds().isBlank()) {
			url += String.format("&stories=%s", request.getStoryIds());
		}
		if (request.getOrderBy() != null) {
			url += String.format("&orderBy=%s", request.getOrderBy());
		}
		if (request.getLimit() != null) {
			url += String.format("&limit=%s", request.getLimit());
		}
		if (request.getOffset() != null) {
			url += String.format("&offSet=%s", request.getOffset());
		}
		return url;
	}

}

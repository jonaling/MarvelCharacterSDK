package MarvelSDK.character;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponse;
import org.springframework.web.client.RestTemplate;

import MarvelSDK.character.Cache.CharacterCache;
import MarvelSDK.character.Exceptions.ApiException;
import MarvelSDK.character.Exceptions.ApiExceptionHandler;
import MarvelSDK.character.Model.CharactersRequest;
import MarvelSDK.character.Model.CharactersResponse;

@Service
public class CharacterService {

	private final RestTemplate restTemplate;
	private final CharacterCache characterCache;

	@Value("${marvel.api.url}")
	private String marvelApiUrl;

	private CharactersRequestValidator validator = new CharactersRequestValidator();

	public CharacterService(RestTemplate restTemplate, CharacterCache characterCache) {
		this.restTemplate = restTemplate;
		this.characterCache = characterCache;
	}

	public CharactersResponse getCharacterDetails(CharactersRequest request) throws Exception {
		try {
			validator.validate(request);

			String additionalFields = addCharactersOptionalFields(request);

			String url = String.format("%s/characters?apikey=%s&hash=%s&ts=%s", marvelApiUrl, request.getApiKey(),
					request.getHash(), request.getTn()) + additionalFields;

			String cacheKey = String.format("%s/characters?apikey=%s", marvelApiUrl, request.getApiKey())
					+ additionalFields;
			
			//System.out.println("cacheKey: "+ cacheKey);

			// Check for a cached ETag
			String cachedEtag = characterCache.getEtag(cacheKey);

			if (cachedEtag != null && request.getUseCache()) {
				CharactersResponse cachedResponse = characterCache.getResponse(cachedEtag);
				if (cachedResponse != null) {
					return cachedResponse;
				}
			}
			// Perform the GET request
			ResponseEntity<CharactersResponse> response = restTemplate.exchange(url, HttpMethod.GET, null,
					CharactersResponse.class);

			// Check for the response status
			if (response.getStatusCode().is2xxSuccessful()) {
				// Update ETag cache
				if (request.getUseCache()) {
					String newEtag = response.getBody().getEtag();
					// Cache the response
					if (newEtag != null) {
						characterCache.putEtag(cacheKey, newEtag);
						characterCache.putResponse(newEtag, response.getBody());
					}
				}
				return response.getBody();
			} else {
				if (response.getBody() instanceof ErrorResponse) {
					ErrorResponse errorResponse = (ErrorResponse) response.getBody();
					throw new ApiException(errorResponse.getBody().getTitle(), response.getStatusCode().value());
				}
				return response.getBody();
			}
		} catch (Exception e) {
			ApiExceptionHandler.handleException(e);
			return null;
		}
	}

	// Filter By optional parameters
	private String addCharactersOptionalFields(CharactersRequest request) {
		String url = "";
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

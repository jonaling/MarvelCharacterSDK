package MarvelSDK.character;

import MarvelSDK.character.Exceptions.InvalidArgumentException;
import MarvelSDK.character.Model.CharactersRequest;

public class CharactersRequestValidator {

	public CharactersRequestValidator() {
	}

	public void validate(CharactersRequest request) {
		requiredFieldsValidation(request);
		apiKeyValidation(request);
		invalidHashField(request);
		invalidIdsContainNonNumbers(request);
	}

	private void requiredFieldsValidation(CharactersRequest request) {
		if (request.getApiKey() == null || request.getHash() == null) {
			throw new InvalidArgumentException("Missing Required Field(s): apiKey, privateKey");
		}
	}

	private void apiKeyValidation(CharactersRequest request) {
		if (request.getApiKey().length() < 20 || !containsOnlyAlphanumeric(request.getApiKey())) {
			throw new InvalidArgumentException("Invalid ApiKey value: Ensure it is longer than 20 characters "
					+ "and only contains numbers and/or alphabets.");
		}
	}

	private void invalidHashField(CharactersRequest request) {
		if (request.getHash().contains(request.getApiKey()) || request.getHash().contains(request.getTn())) {
			throw new InvalidArgumentException("Invalid hash format.");
		}
	}

	private void invalidIdsContainNonNumbers(CharactersRequest request) {
		if (request.getComicsIds() != null && !containsOnlyNumericComma(request.getComicsIds())) {
			throw new InvalidArgumentException("Invalid comicIds.");
		}
		if (request.getSeriesIds() != null && !containsOnlyNumericComma(request.getSeriesIds())) {
			throw new InvalidArgumentException("Invalid seriesIds.");
		}
		if (request.getEventIds() != null && !containsOnlyNumericComma(request.getEventIds())) {
			throw new InvalidArgumentException("Invalid eventIds.");
		}
		if (request.getStoryIds() != null && !containsOnlyNumericComma(request.getStoryIds())) {
			throw new InvalidArgumentException("Invalid storyIds.");
		}
	}
	

	private static boolean containsOnlyAlphanumeric(String str) {
		return str != null && str.matches("^[a-zA-Z0-9]+$");
	}

	private static boolean containsOnlyNumericComma(String str) {
		return str.matches("^[0-9,\\s]*$");
	}
}

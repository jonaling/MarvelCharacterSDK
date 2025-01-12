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
	}

	public void requiredFieldsValidation(CharactersRequest request) {
		if (request.getApiKey() == null || request.getHash() == null ) {
			throw new InvalidArgumentException("Missing Required Field(s): apiKey, privateKey");
		}
	}
	public void apiKeyValidation(CharactersRequest request) {
		if(request.getApiKey().length() < 20 || !containsOnlyAlphanumeric(request.getApiKey())) {
			throw new InvalidArgumentException("Invalid ApiKey value: Ensure it is longer than 20 characters "
					+ "and only contains numbers and/or alphabets.");
		}
	}

	public void invalidHashField(CharactersRequest request) {
		if (request.getHash().contains(request.getApiKey()) || request.getHash().contains(request.getTn())) {
			throw new InvalidArgumentException("Invalid hash format.");
		}
	}
	
	public static boolean containsOnlyAlphanumeric(String str) {
        return str != null && str.matches("^[a-zA-Z0-9]+$");
    }
}

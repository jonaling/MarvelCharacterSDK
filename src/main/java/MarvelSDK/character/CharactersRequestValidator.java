package MarvelSDK.character;

import MarvelSDK.character.Model.CharactersRequest;

public class CharactersRequestValidator {
	CharactersRequest request;
	
	public CharactersRequestValidator(CharactersRequest request) {
		this.request =request;
	}
	
	public void validate() {
		
	}
	
	public void requiredFieldsValidation() {
		if(request.apiKey == null || request.hash == null || request.tn == null) {
			
		}
	}
}

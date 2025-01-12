package MarvelSDK.character.Exceptions;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

public class ApiExceptionHandler {

	public static void handleException(Exception e) throws Exception {
		if (e instanceof HttpClientErrorException) {
			HttpClientErrorException httpException = (HttpClientErrorException) e;
			throw new ApiException(httpException.getResponseBodyAsString(), httpException.getStatusCode().value());
		} else if (e instanceof ResourceAccessException) {
			throw new RuntimeException("Network error occurred: " + e.getMessage(), e);
		}
		throw e;
	}

}

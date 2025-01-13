package MarvelSDK.character;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import MarvelSDK.character.Exceptions.InvalidArgumentException;
import MarvelSDK.character.Model.CharactersRequest;

class CharactersRequestValidatorTest {

    private final CharactersRequestValidator validator = new CharactersRequestValidator();

    @Test
    void testValidateMissingApiKey() {
        CharactersRequest request = new CharactersRequest();

        Exception exception = assertThrows(InvalidArgumentException.class, () -> {
            validator.validate(request);
        });

        assertEquals("Missing Required Field(s): apiKey, privateKey", exception.getMessage());
    }



    @Test
    void testValidateInvalidApiKeyShort() {
        CharactersRequest request = new CharactersRequest.Builder("short","someHash").build();

        Exception exception = assertThrows(InvalidArgumentException.class, () -> {
            validator.validate(request);
        });

        assertEquals("Invalid ApiKey value: Ensure it is longer than 20 characters and only contains numbers and/or alphabets.", exception.getMessage());
    }

    @Test
    void testValidateInvalidApiKeyNonAlphanumeric() {
        CharactersRequest request = new CharactersRequest.Builder("invalidApiKey@1234567890","someHash").build();

        Exception exception = assertThrows(InvalidArgumentException.class, () -> {
            validator.validate(request);
        });

        assertEquals("Invalid ApiKey value: Ensure it is longer than 20 characters and only contains numbers and/or alphabets.", exception.getMessage());
    }

    @Test
    void testValidateInvalidComicIds() {
    	ArrayList<String> comicIds = new ArrayList<String> ();
    	comicIds.add("1");
    	comicIds.add("three");
        CharactersRequest request = new CharactersRequest.Builder("validApiKey1234567890","validApiKey1234567890someHash")
        		.comicsIds(comicIds)
        		.build();

        Exception exception = assertThrows(InvalidArgumentException.class, () -> {
            validator.validate(request);
        });

        assertEquals("Invalid comicIds.", exception.getMessage());
    }

    @Test
    void testValidateValidRequest() {
    	ArrayList<String> comicIds = new ArrayList<String> ();
    	comicIds.add("1");
    	comicIds.add("2");
        CharactersRequest request = new CharactersRequest.Builder("validApiKey1234567890","validApiKey1234567890someHash")
        		.comicsIds(comicIds)
        		.build();;


        // No exception should be thrown
        assertDoesNotThrow(() -> {
            validator.validate(request);
        });
    }
}

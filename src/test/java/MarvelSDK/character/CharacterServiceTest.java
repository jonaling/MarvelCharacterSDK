package MarvelSDK.character;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


import java.util.Arrays;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import MarvelSDK.character.Exceptions.ApiException;
import MarvelSDK.character.Exceptions.ApiExceptionHandler;
import MarvelSDK.character.Exceptions.InvalidArgumentException;
import MarvelSDK.character.Model.CharactersRequest;
import MarvelSDK.character.Model.CharactersResponse;
import MarvelSDK.character.Model.OrderByEnum;

@ExtendWith(MockitoExtension.class)
class CharacterServiceTest {

	@Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CharacterService characterService;

    @Value("${marvel.api.url}")
    private String marvelApiUrl;

    private CharactersRequest validRequest;
    
    private String url;
    
    @BeforeEach
    void setUp() {
    	validRequest =  new CharactersRequest.Builder("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789",
    			"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789")
                .build();
    	
    	url = String.format("%s/characters?apikey=%s&hash=%s&ts=%s",
        		marvelApiUrl, validRequest.getApiKey(), validRequest.getHash(), validRequest.getTn());
    	
    }

    @Test
    void testGetCharacterDetails_ValidRequest_Success() {
       
        CharactersResponse expectedResponse = new CharactersResponse();
        

        when(restTemplate.getForObject(url, CharactersResponse.class)).thenReturn(expectedResponse);

        // Act
        CharactersResponse actualResponse = null;
		try {
			actualResponse = characterService.getCharacterDetails(validRequest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        verify(restTemplate, times(1)).getForObject(url, CharactersResponse.class);
    }
    
    @Test
    void testGetCharacterDetails_WithOptionalFields() {
        // Arrange
        CharactersRequest request =  new CharactersRequest.Builder("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789",
    			"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789")
                .name("Spider-Man")
                .build();
        
        String currUrl = String.format("%s/characters?apikey=%s&hash=%s&ts=%s&name=%s",
        		marvelApiUrl, request.getApiKey(), request.getHash(), request.getTn(), request.getName());

        CharactersResponse mockedResponse = new CharactersResponse();
        when(restTemplate.getForObject(currUrl, CharactersResponse.class))
                .thenReturn(mockedResponse);

        // Act
        CharactersResponse response;
		try {
			response = characterService.getCharacterDetails(request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Assert
        assertNotNull(mockedResponse);
        verify(restTemplate, times(1)).getForObject(currUrl, CharactersResponse.class);
    }

    @Test
    void testGetCharacterDetails_InvalidRequest_ThrowsException() {
        // Arrange
    	CharactersRequest request = new CharactersRequest.Builder("", "")
                .build();

        // Act & Assert
        assertThrows(InvalidArgumentException.class, () -> characterService.getCharacterDetails(request));
        verify(restTemplate, never()).getForObject(anyString(), eq(CharactersResponse.class));
    }

    @Test
    void testGetCharacterDetails_ApiException()  {
        // Arrange
        when(restTemplate.getForObject(any(String.class), eq(CharactersResponse.class)))
                .thenThrow(new RuntimeException("API Error"));

        // Act
        
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            characterService.getCharacterDetails(validRequest);
        });
        //CharactersResponse response = characterService.getCharacterDetails(validRequest);

        assertEquals("API Error", exception.getMessage());
    }
}


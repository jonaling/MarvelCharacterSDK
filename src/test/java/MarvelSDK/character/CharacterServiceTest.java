package MarvelSDK.character;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import MarvelSDK.character.Exceptions.InvalidArgumentException;
import MarvelSDK.character.Model.CharactersRequest;
import MarvelSDK.character.Model.CharactersResponse;
import MarvelSDK.character.Model.OrderByEnum;


class CharacterServiceTest {

	@Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CharacterService characterService;

    @Value("${marvel.api.url}")
    private String marvelApiUrl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCharacterDetails_ValidRequest_Success() {
        // Arrange
    	CharactersRequest request =  new CharactersRequest.Builder("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789",
    			"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789")
                .orderBy(OrderByEnum.nameAsc) 
                .limit(10)
                .offset(0)
                .build();

        CharactersResponse expectedResponse = new CharactersResponse(); // Populate with expected data
        String url = String.format("%s/characters?apikey=%s&hash=%s&tn=%s",
        		marvelApiUrl, request.getApiKey(), request.getHash(), request.getTn());

        when(restTemplate.getForObject(url, CharactersResponse.class)).thenReturn(expectedResponse);

        // Act
        CharactersResponse actualResponse = null;
		try {
			actualResponse = characterService.getCharacterDetails(request);
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
    void testGetCharacterDetails_InvalidRequest_ThrowsException() {
        // Arrange
    	CharactersRequest request = new CharactersRequest.Builder("", "")
                .orderBy(OrderByEnum.nameAsc) 
                .limit(10)
                .offset(0)
                .build();

        // Act & Assert
        assertThrows(InvalidArgumentException.class, () -> characterService.getCharacterDetails(request));
        verify(restTemplate, never()).getForObject(anyString(), eq(CharactersResponse.class));
    }

    @Test
    void testGetCharacterDetails_ApiCallFails_HandlesException() throws Exception {
        // Arrange
    	CharactersRequest request = new CharactersRequest.Builder("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789",
    			"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789")
                .orderBy(OrderByEnum.nameAsc) 
                .limit(10)
                .offset(0)
                .build();

        String url = String.format("%s/characters?apikey=%s&hash=%s&tn=%s", 
        		marvelApiUrl, request.getApiKey(), request.getHash(), request.getTn());
        when(restTemplate.getForObject(url, CharactersResponse.class)).thenThrow(new RuntimeException("API error"));

        // Act
        CharactersResponse response = characterService.getCharacterDetails(request);

        // Assert
        assertNull(response);
        verify(restTemplate, times(1)).getForObject(url, CharactersResponse.class);
    }
}


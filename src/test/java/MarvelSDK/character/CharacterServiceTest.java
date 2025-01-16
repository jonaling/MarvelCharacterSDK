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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import MarvelSDK.character.Cache.CharacterCache;
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
	
	@Mock
	private CharacterCache characterCache;

    @InjectMocks
    private CharacterService characterService;

    @Value("${marvel.api.url}")
    private String marvelApiUrl;

    private CharactersRequest validRequest;
    
    private String validUrl;
    
    private String validCacheKey;
    
    @BeforeEach
    void setUp() {
    	
    	validRequest =  new CharactersRequest.Builder("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789",
    			"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789")
                .build();
    	
    	validUrl = String.format("%s/characters?apikey=%s&hash=%s&ts=%s",
        		marvelApiUrl, validRequest.getApiKey(), validRequest.getHash(), validRequest.getTn());
    	
    	validCacheKey = String.format("%s/characters?apikey=%s", marvelApiUrl, validRequest.getApiKey());
    	
    }

    @Test
    void testGetCharacterDetails_ValidRequest_Success() {
       
        CharactersResponse expectedResponse = new CharactersResponse();
        ResponseEntity<CharactersResponse> responseEntity = new ResponseEntity<>(expectedResponse, new HttpHeaders(), HttpStatus.OK);

        when(restTemplate.exchange(eq(validUrl), eq(HttpMethod.GET), isNull(), eq(CharactersResponse.class)))
        .thenReturn(responseEntity);

        CharactersResponse actualResponse = null;
		try {
			actualResponse = characterService.getCharacterDetails(validRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        verify(restTemplate, times(1)).exchange(validUrl,HttpMethod.GET, null, CharactersResponse.class);
    }
    
    @Test
    void testGetCharacterDetails_CachedResponse() throws Exception {

        CharactersResponse cachedResponse = new CharactersResponse();
        when(characterCache.getEtag(validCacheKey)).thenReturn("etag");
        when(characterCache.getResponse("etag")).thenReturn(cachedResponse);

        CharactersResponse response = characterService.getCharacterDetails(validRequest);

        assertNotNull(response);
        assertEquals(cachedResponse, response);
        verify(restTemplate, never()).exchange(validUrl,HttpMethod.GET, null, CharactersResponse.class);
    }
    
    @Test
    void testGetCharacterDetails_WithOptionalFields() {

        CharactersRequest request =  new CharactersRequest.Builder("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789",
    			"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789")
                .name("Spider-Man")
                .build();
        
        String currUrl = String.format("%s/characters?apikey=%s&hash=%s&ts=%s&name=%s",
        		marvelApiUrl, request.getApiKey(), request.getHash(), request.getTn(), request.getName());

        CharactersResponse expectedResponse = new CharactersResponse();
        ResponseEntity<CharactersResponse> responseEntity = new ResponseEntity<>(expectedResponse, new HttpHeaders(), HttpStatus.OK);

        when(restTemplate.exchange(eq(currUrl), eq(HttpMethod.GET), isNull(), eq(CharactersResponse.class)))
        .thenReturn(responseEntity);

        CharactersResponse actualResponse = null;
		try {
			actualResponse= characterService.getCharacterDetails(request);
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        verify(restTemplate, times(1)).exchange(currUrl,HttpMethod.GET, null, CharactersResponse.class);
    }

    @Test
    void testGetCharacterDetails_InvalidRequest_ThrowsException() {

    	CharactersRequest request = new CharactersRequest.Builder("", "")
                .build();


        assertThrows(InvalidArgumentException.class, () -> characterService.getCharacterDetails(request));
        verify(restTemplate, never()).getForObject(anyString(), eq(CharactersResponse.class));
    }
    

    @Test
    void testGetCharacterDetails_ApiException()  {
    	
    	when(restTemplate.exchange(any(String.class), eq(HttpMethod.GET), isNull(), eq(CharactersResponse.class)))
.thenThrow(new RuntimeException("API Error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            characterService.getCharacterDetails(validRequest);
        });

        assertEquals("API Error", exception.getMessage());
    }
}


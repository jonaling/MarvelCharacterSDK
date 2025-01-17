package MarvelSDK.character.Cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import MarvelSDK.character.Model.CharactersResponse;

import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class CharacterCache {

    private final Cache<String, String> etagCache;
    private final Cache<String, CharactersResponse> responseCache;

    public CharacterCache() {
        this.etagCache = Caffeine.newBuilder()
                .expireAfterWrite(7, TimeUnit.DAYS) // Expire after one week
                .build();

        this.responseCache = Caffeine.newBuilder()
                .expireAfterWrite(7, TimeUnit.DAYS) // Expire after one week
                .build();
    }

    public String getEtag(String url) {
        return etagCache.getIfPresent(url);
    }

    public void putEtag(String url, String etag) {
        etagCache.put(url, etag);
    }

    public CharactersResponse getResponse(String etag) {
        return responseCache.getIfPresent(etag);
    }

    public void putResponse(String etag, CharactersResponse response) {
        responseCache.put(etag, response);
    }
    
    public void invalidateCaches() {
    	etagCache.invalidateAll();
    	responseCache.invalidateAll();
    }
}
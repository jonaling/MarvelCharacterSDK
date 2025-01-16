package MarvelSDK.character;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CharacterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CharacterApplication.class, args);
		
	}
}

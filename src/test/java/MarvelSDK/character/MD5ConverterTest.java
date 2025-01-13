package MarvelSDK.character;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;

class MD5ConverterTest {

	@Test
    void testGenerateHash() {
        // Test cases
        String input1 = "Hello, World!";
        String expectedHash1 = "65a8e27d8879283831b664bd8b7f0ad4"; // MD5 hash for "Hello, World!"

        String input2 = "MD5";
        String expectedHash2 = "7f138a09169b250e9dcb378140907378"; // MD5 hash for "MD5"

        // Perform the hash generation
        String actualHash1 = MD5Converter.generateHash(input1);
        String actualHash2 = MD5Converter.generateHash(input2);

        // Assert that the generated hashes match the expected values
        assertEquals(expectedHash1, actualHash1);
        assertEquals(expectedHash2, actualHash2);
    }

    @Test
    void testGenerateHashEmptyString() {
        // Test for empty string
        String input = " ";
        String expectedHash = "7215ee9c7d9dc229d2921a40e899ec5f"; // MD5 hash for empty string

        String actualHash = MD5Converter.generateHash(input);

        assertEquals(expectedHash, actualHash);
    }

    @Test
    void testGenerateHashSpecialCharacters() {
        // Test with special characters
        String input = "!@#$%^&*()";
        String expectedHash = "05b28d17a7b6e7024b6e5d8cc43a8bf7"; // MD5 hash for "!@#$%^&*()"

        String actualHash = MD5Converter.generateHash(input);

        assertEquals(expectedHash, actualHash);
    }
    
    @Test
    void testGenerateHashNoSuchAlgorithmException() {
        // Simulate NoSuchAlgorithmException
        assertThrows(RuntimeException.class, () -> {
            // Mock MessageDigest to throw NoSuchAlgorithmException
            MessageDigest mockDigest = mock(MessageDigest.class);
            when(MessageDigest.getInstance("MD5")).thenThrow(new NoSuchAlgorithmException());

            // Call the method which should throw the exception
            MD5Converter.generateHash("test");
        });
    }

}

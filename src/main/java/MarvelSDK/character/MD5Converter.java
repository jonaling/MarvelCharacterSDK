package MarvelSDK.character;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Converter {
	public static String generateHash(String toHash) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(toHash.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }
}

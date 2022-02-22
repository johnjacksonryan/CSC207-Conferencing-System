package security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Random;

/**
 * Security module that manages password security.
 *
 * Features:
 * - hashes and salts passwords
 * - authenticates passwords
 *
 * Sources we found very helpful:
 * https://auth0.com/blog/adding-salt-to-hashing-a-better-way-to-store-passwords/
 * https://stackoverflow.com/questions/18142745/how-do-i-generate-a-salt-in-java-for-salted-hash
 *
 * Minimum recommended iteration count derived from this source:
 * https://tools.ietf.org/html/rfc2898#section-4.2
 *
 * Key generation algorithm obtained from this source:
 * https://tools.ietf.org/html/rfc8018#section-5
 *
 * @author Linjia
 * @author Justin
 * @version 1.0
 */
public class PasswordSystem {
    private final CredentialManager credentialManager;
    private final Random RNG = new SecureRandom();
    private final int ITERATION_COUNT = 1000;
    private final int KEY_LENGTH = 128;

    public PasswordSystem(CredentialManager credentialManager) {
        this.credentialManager = credentialManager;
    }

    /**
     * Generate a random value (salt) to add further randomness
     * to hashed passwords.
     *
     * @return a cryptographically secure random 64-byte salt
     */
    public byte[] getSalt() {
        byte[] salt = new byte[64];
        RNG.nextBytes(salt);
        return salt;
    }

    /**
     * Generate a random sequence of characters based on input password.
     * IMPORTANT: salt used in hashing is RANDOM.
     *
     * @param password  the password in plaintext
     * @return a hashed password and salt
     */
    public Credentials hash(char[] password) {
        byte[] salt = getSalt();
        PBEKeySpec pbe = new PBEKeySpec(password, salt, ITERATION_COUNT, KEY_LENGTH);
        return new Credentials(getSecretKey(password, pbe), salt);
    }

    private byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec pbe = new PBEKeySpec(password, salt, ITERATION_COUNT, KEY_LENGTH);
        return getSecretKey(password, pbe);
    }

    private byte[] getSecretKey(char[] password, PBEKeySpec pbe) {
        // Clear character array to dispose of data.
        Arrays.fill(password, '0');

        // Attempt to generate a key.
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            return keyFactory.generateSecret(pbe).getEncoded();

        } catch (NoSuchAlgorithmException | InvalidKeySpecException exception) {
            throw new AssertionError(exception.getMessage(), exception);

        } finally {
            pbe.clearPassword(); // Safely disposes of password.
        }
    }

    /**
     * Precondition: userID is a key in passwords
     *
     * Check if the inputted password matches the password stored in the map.
     *
     * @param userID    the id of the user trying to log in
     * @param password  the inputted password
     * @return true iff the hashed inputted password matches the stored password
     */
    public boolean authenticate(int userID, char[] password) {
        byte[] salt = credentialManager.getCredentials(userID).getSalt();

        byte[] actualHash = hash(password, salt);
        byte[] expectedHash = credentialManager.getCredentials(userID).getPassword();

        // Clear character array to dispose of data.
        Arrays.fill(password, '0');

        return Arrays.equals(actualHash, expectedHash);
    }

    /**
     * Add new credential to list of user credentials.
     *
     * @param credentials  the credential to be added
     */
    public void addCredentials(Credentials credentials) {
        credentialManager.addCredentials(credentials.getPassword(), credentials.getSalt());
    }
}
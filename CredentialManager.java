package security;

import java.util.ArrayList;
import java.util.List;

/**
 * Use case for Credential entities
 * that stores and keeps track of all Credentials.
 *
 * @author Justin
 * @author Linjia
 * @version 1.0
 */
public class CredentialManager {
    private final List<Credentials> credentials = new ArrayList<>();

    /**
     * @param id  id of the user
     * @return  the credentials of user with that id
     */
    public Credentials getCredentials(int id) {
        return credentials.get(id);
    }

    /**
     * Add new credential to list of user credentials.
     *
     * @param password  the hashed password
     * @param salt      the salt used in the hash
     */
    public void addCredentials(byte[] password, byte[] salt) {
        credentials.add(new Credentials(password, salt));
    }
}
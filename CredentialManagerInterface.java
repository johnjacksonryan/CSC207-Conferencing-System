package users.facade.manager_interface;

import security.CredentialManager;
import security.Credentials;

public interface CredentialManagerInterface {
    CredentialManager getCredentialManager();

    /**
     * @param id  id of the user
     * @return  the credentials of user with that id
     */
    default Credentials getCredentials(int id) {
        return getCredentialManager().getCredentials(id);
    }

    /**
     * Add new credential to list of user credentials.
     *
     * @param password  the hashed password
     * @param salt      the salt used in the hash
     */
    default void addCredentials(byte[] password, byte[] salt) {
        getCredentialManager().addCredentials(password, salt);
    }

}


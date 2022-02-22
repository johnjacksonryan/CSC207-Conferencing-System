package gateway.facade.system;

import gateway.CredentialUpdater;

/**
 * Interface for methods of CredentialUpdater.
 *
 * @author Justin
 * @version 1.0
 */
public interface CredentialSystem extends System {
    /**
     * @return the gateway that updates credentials
     */
    CredentialUpdater getCredentialUpdater();

    /**
     * Adds a new credential to the database.
     *
     * @param  password  the encrypted password
     * @param  salt      the salt used to encrypt
     */
    default void addCredentials(byte[] password, byte[] salt) {
        getCredentialUpdater().addCredentials(password, salt);
    }
}
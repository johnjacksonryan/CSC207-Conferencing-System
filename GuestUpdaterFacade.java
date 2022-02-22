package gateway.facade;

import gateway.CredentialUpdater;
import gateway.UserUpdater;
import gateway.facade.system.CredentialSystem;
import gateway.facade.system.UserSystem;

/**
 * Facade that combines interfaces for UserSystem and CredentialSystem.
 *
 * @author Justin
 * @version 2.0
 */
public class GuestUpdaterFacade implements UserSystem, CredentialSystem {

    private final UserUpdater userUpdater;
    private final CredentialUpdater credentialUpdater;

    /**
     * Default constructor.
     *
     * @param  userUpdater        gateway that updates users
     * @param  credentialUpdater  gateway that updates credentials
     */
    public GuestUpdaterFacade(UserUpdater userUpdater, CredentialUpdater credentialUpdater) {
        this.userUpdater = userUpdater;
        this.credentialUpdater = credentialUpdater;
    }

    /**
     * @return the gateway that updates users
     */
    @Override
    public UserUpdater getUserUpdater() {
        return userUpdater;
    }

    /**
     * @return the gateway that updates credentials
     */
    @Override
    public CredentialUpdater getCredentialUpdater() {
        return credentialUpdater;
    }

    public void load(int id) {
        userUpdater.loadUsers();
        credentialUpdater.loadCredentials();
    }
}
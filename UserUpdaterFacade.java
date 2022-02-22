package gateway.facade;

import gateway.MessageUpdater;
import gateway.UserUpdater;
import gateway.facade.system.MessageSystem;
import gateway.facade.system.UserSystem;

/**
 * Facade that has interfaces for UserSystem and MessageSystem.
 *
 * @author Justin
 * @version 2.0
 */
public class UserUpdaterFacade implements UserSystem, MessageSystem {
    private final UserUpdater userUpdater;
    private final MessageUpdater messageUpdater;

    /**
     * Default constructor.
     *
     * @param  userUpdater     gateway class that updates users
     * @param  messageUpdater  gateway class that updates messages
     */
    public UserUpdaterFacade(UserUpdater userUpdater, MessageUpdater messageUpdater) {
        this.userUpdater = userUpdater;
        this.messageUpdater = messageUpdater;
    }

    /**
     * @return the gateway that updates users
     */
    @Override
    public UserUpdater getUserUpdater() {
        return userUpdater;
    }

    /**
     * @return the gateway the updates messages
     */
    @Override
    public MessageUpdater getMessageUpdater() {
        return messageUpdater;
    }

    public void load(int id) {
        userUpdater.loadUsers();
        messageUpdater.loadMessages(id);
    }

}

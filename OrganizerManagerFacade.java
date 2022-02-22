package users.facade;

import messaging.MessageManager;
import scheduling.EventManager;
import security.CredentialManager;
import users.base.UserManager;
import users.facade.manager_interface.CredentialManagerInterface;
import users.organizer.OrganizerManager;

public class OrganizerManagerFacade extends UserManagerFacade implements CredentialManagerInterface {
    private CredentialManager credentialManager;
    private OrganizerManager userManager;

    public OrganizerManagerFacade(OrganizerManager userManager, MessageManager messageManager, EventManager eventManager, CredentialManager credentialManager) {
        super(userManager, messageManager, eventManager);
        this.credentialManager = credentialManager;
        this.userManager = userManager;
    }

    public CredentialManager getCredentialManager() {
        return credentialManager;
    }

    public int register(UserManager.UserType type, String name, boolean vip) {
        return userManager.register(type, name, vip);
    }
}

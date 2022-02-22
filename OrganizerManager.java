package users.organizer;

import security.CredentialManager;
import security.Credentials;
import security.PasswordSystem;
import users.base.UserManager;

import java.util.ArrayList;
import java.util.List;

/**
 * The use case managing the Organizer entities
 *
 * @author Linjia
 * @author Carl
 * @author Justin
 * @author Yukang
 * @author Janardan
 * @author John
 * @version 2.3
 */
public class OrganizerManager extends UserManager {

    public OrganizerManager(UserManager userManager) {
        receiveUsers(userManager);
    }

    /**
     * Register a new user.
     *
     * @param  type      type of user
     * @param  name      desired name
     * @return return user id, password, and salt, formatted as ArrayList {int id, byte[] password, byte[] salt}
     */
    public int register(UserManager.UserType type, String name, boolean vip) {
        return addUser(type, name, vip);
    }

}
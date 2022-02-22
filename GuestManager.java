package users.guest;

import security.CredentialManager;
import security.Credentials;
import security.PasswordSystem;
import users.base.UserManager;

import java.util.ArrayList;
import java.util.List;

/**
 * The use case for guests.
 *
 * @author Yukang
 * @author Justin
 * @version 1.2
 */
public class GuestManager {
    private final UserManager userManager;
    private final PasswordSystem passwordSystem;

    /**
     * Default constructor.
     *
     * @param  userManager        the system managing user
     * @param  credentialManager  the use case for credentials
     */
    public GuestManager(UserManager userManager, CredentialManager credentialManager) {
        this.userManager = userManager;
        this.passwordSystem = new PasswordSystem(credentialManager);
    }

    /**
     * Attempt to log in with the specified id and password.
     *
     * @param  id        the id of user
     * @param  password  the password of user
     * @return  UserType if log in is successful, otherwise return null
     */
    public UserManager.UserType logIn(int id, char[] password) {
        if (userManager.getUser(id) == null || !passwordSystem.authenticate(id, password)) {
            return null;
        } else {
            return userManager.getUserType(id);
        }
    }

    /**
     * Register a new user.
     *
     * @param  type      type of user
     * @param  name      desired name
     * @param  password  password, in plaintext
     * @return return user id, password, and salt, formatted as ArrayList {int id, byte[] password, byte[] salt}
     */
    public List<Object> register(UserManager.UserType type, String name, char[] password, boolean vip) {
        Credentials credential = passwordSystem.hash(password);
        passwordSystem.addCredentials(credential);

        List<Object> returnList = new ArrayList<>();
        returnList.add(userManager.addUser(type, name, vip));
        returnList.add(credential.getPassword());
        returnList.add(credential.getSalt());

        return returnList;
    }
}

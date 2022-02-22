package users.guest;

import display.GuestPresenter;
import gateway.facade.GuestUpdaterFacade;
import users.base.Controller;
import users.base.UserManager;

import java.util.List;

/**
 * The controller for guests.
 *
 * @author Yukang
 * @author Janardan
 * @version 2.7
 */
public class GuestController extends Controller {
    private final GuestManager guestManager;
    private final GuestPresenter guestPresenter;
    private final GuestUpdaterFacade updater;
    /**
     * Default constructor
     *
     * @param  guestManager  the system handling login
     * @param  updater       facade that combines required interfaces
     */
    public GuestController(GuestManager guestManager, GuestUpdaterFacade updater) {
        this.guestManager = guestManager;
        this.guestPresenter = new GuestPresenter();
        this.updater = updater;
    }

    /**
     * Main menu for Guest
     */
    public int displayMainMenu() {
        while (true) {
            int index = requestFromList(guestPresenter.getMenu(), true);
            if (index == -1) {
                return -1;
            }
            if (index == 0) {
                int id = logIn();
                if (id != -1) {
                    return id;
                }
            }
            if (index == 1) {
                register();
            }
        }
    }

    /**
     * Allow user to login.
     *
     * @return the user id.
     */
    public int logIn() {
        guestPresenter.printLogInMessage();
        int id = requestInt("id");
        char[] password = requestString("password").toCharArray();
        UserManager.UserType user = guestManager.logIn(id, password);

        if (user == null) {
            guestPresenter.logInFailure();
            return -1;
        } else {
            return id;
        }
    }

    /**
     * Register a new user.
     */
    public void register() {
        int index = requestFromList(getUserTypes(), true);
        if (index == -1) {
            guestPresenter.returnToMainMenu();
            return;
        }
        UserManager.UserType type;
        if (index == 0) {
            type = UserManager.UserType.ATTENDEE;
        } else if (index == 1) {
            type = UserManager.UserType.ORGANIZER;
        } else {
            type = UserManager.UserType.SPEAKER;
        }
        String name = requestString("name");
        char[] password = requestString("password").toCharArray();
        boolean vip = false;
        if(type == UserManager.UserType.ATTENDEE) {
            vip = requestBoolean("vip");
        }
        List<Object> list = guestManager.register(type, name, password, vip);
        int id = (int)list.get(0);
        updater.addCredentials((byte[])list.get(1), (byte[])list.get(2));
        updater.addUser(id, type, name, vip);
        guestPresenter.printSuccessRegistration(id);
    }

    /**
     * Load data from database.
     */
    public void load() {
        updater.load(-1); // -1 because Guest ID doesn't matter.
    }
}
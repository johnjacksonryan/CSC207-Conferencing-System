import factory.ControllerFactory;
import gateway.*;
import users.base.Controller;
import users.base.UserController;
import users.guest.GuestController;

/**
 * Builder class. Sets up the program.
 *
 * @author Janardan
 * @author Yukang
 * @author Justin
 * @author Dickson Li
 * @author Carl
 * @version 3.0
 */
public class ConferenceSystem {

    /**
     * Default constructor.
     */
    public ConferenceSystem() {
        Database database = new Database();
        ControllerFactory factory = new ControllerFactory(database);

        // Guest controller.
        Controller controller = factory.getGuestController();
        controller.load(); // -1 because Guest won't have an id.

        int id = ((GuestController)controller).displayMainMenu();

        if (id == -1) {
            return;
        }

        // Non-guest controller.
        controller = factory.getController(id);
        controller.load();
        ((UserController) controller).displayMainMenu();
    }
}

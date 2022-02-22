package display;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Presenter for guests
 *
 * @author Janardan
 * @author Yukang
 * @version 1.5
 */
public class GuestPresenter extends Presenter {

    private final ArrayList<String> MENU = new ArrayList<>(Arrays.asList("Login", "Registration"));

    public ArrayList<String> getMenu() {
        return MENU;
    }

    /**
     * Prints the welcome message when logging into the program
     */
    public void printLogInMessage(){
        System.out.println(
                "Hello there User! Please Log In with your Id and Password before you begin using the program.");
    }

    /**
     * Prints the notification of successfully creating an account with the account ID
     *
     * @param id id of created user
     */
    public void printSuccessRegistration(int id) {
        System.out.printf("\nAccount successfully created. Your ID is %s.\n", id);
    }

    public void logInFailure() {
        printFailure("There is no user in our database that has this ID and password. " +
                "Your password or ID may be incorrect");
    }
}

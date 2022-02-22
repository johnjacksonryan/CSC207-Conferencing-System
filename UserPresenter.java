package display;

import java.util.ArrayList;

/**
 * The Presenter for User
 */

public abstract class UserPresenter extends Presenter {

    /**
     * @return  the menu headers for the main menu
     */
    public abstract ArrayList<String> getMenu();

    /**
     * @return  the menu headers for the message menu
     */
    public abstract ArrayList<String> getMessageMenu();

    public void noConversation() {
        printFailure("no conversations found");
    }

    public void succeedSendMessage() {
        printSuccess("Message sent");
    }

    public void logOut() {
        System.out.println("Logged out. Have a nice day!");
    }

    public void noEventAvailable() {
        printFailure("no event available");
    }

    public void noAttendeeAvailable() {
        printFailure("no attendee available");
    }

    public void noUnArchivedMessage(){printFailure("no unarchived messages found");}

    public void noUnreadMesages(){printFailure("There has not been any new messages");}

    public void noArchivedMessage(){printFailure("There is no archived message");}

}
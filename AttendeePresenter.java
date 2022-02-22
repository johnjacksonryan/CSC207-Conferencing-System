package display;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The presenter for attendees.
 *
 * @author Justin
 * @author John
 * @author Janardan
 * @author Linjia
 * @version 1.1
 */
public class AttendeePresenter extends UserPresenter {

    private final ArrayList<String> MENU = new ArrayList<>(Arrays.asList(
            "View Schedule", "View Events", "Register for Event",
            "Unregister for Event", "Contacts", "Message"));

    private final ArrayList<String> CONTACT_MENU = new ArrayList<>(
            Arrays.asList("View Contacts", "Request Contact", "Accept Requested Contact"));

    private final ArrayList<String> MESSAGE_MENU = new ArrayList<>(
            Arrays.asList("View Messages", "View New Messages", "Message an attendee", "Message a speaker",
                    "Archive a Message", "Unarchive a Message", "Mark message as unread", "Delete Message"));

    /**
     * @return the menu headers for the main menu
     */
    @Override
    public ArrayList<String> getMenu() {
        return this.MENU;
    }

    /**
     * @return the menu headers for the message menu
     */
    @Override
    public ArrayList<String> getMessageMenu() {
        return this.MESSAGE_MENU;
    }

    /**
     * @return the menu headers for the contact menu
     */
    public ArrayList<String> getContactMenu() {
        return this.CONTACT_MENU;
    }

    //event messages
    public void noRegisteredEventAvailable() {
        printFailure("no registered event available");
    }

    public void succeedRegister() {
        printSuccess("successfully registered for event");
    }

    public void failedRegister() {
        printFailure("failed to register for event");
    }

    public void succeedUnregister() {
        printSuccess("unregistered from event");
    }

    public void noSpeakerAvailable() {
        printFailure("you're not attending any event or no speaker in events you are attending");
    }

    //contact messages
    public void succeedAddContact() {
        printSuccess("added contact");
    }

    public void succeedAcceptContact() {
        printSuccess("accepted contact");
    }

    public void noRequest() {
        printFailure("no request from other attendee");
    }

    public void noContact() {
        printFailure("no contact to view");
    }
}
package display;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * The presenter for attendees.
 *
 * @author Janardan
 * @version 1.1
 */
public class SpeakerPresenter extends UserPresenter {

    private final ArrayList<String> menu = new ArrayList<>(Arrays.asList(
            "View Talks", "Messages"));

    private final ArrayList<String> messageMenu = new ArrayList<>(Arrays.asList(
            "View Messages", "View New Messages", "Message One Attendee", "Message all Attendees at one Event",
            "Message all Attendees in all events", "Archive a Message","Unarchive a Message", "Delete Message"));

    /**
     * @return the menu headers for the main menu
     */
    @Override
    public ArrayList<String> getMenu() {
        return this.menu;
    }

    /**
     * @return the menu headers for the message menu
     */
    @Override
    public ArrayList<String> getMessageMenu() {
        return this.messageMenu;
    }

    public void printTalk(HashMap<Integer, String> talks) {
        printTitle("Talks");
        printMap(talks);
    }
}

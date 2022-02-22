package display;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Presenter for Organizer.
 *
 * @author Yukang
 * @author Janardan
 * @author Justin
 * @author Linjia
 * @version 2.6
 */
public class OrganizerPresenter extends UserPresenter {

    private final ArrayList<String> MENU = new ArrayList<>(Arrays.asList(
            "Create New User", "View Events", "Schedule New Events", "Cancel Event",
            "Reschedule Event", "Register New Room", "Messages", "Change Event Capacity"));

    private final ArrayList<String> MESSAGE_MENU = new ArrayList<>(Arrays.asList(
            "View Messages", "View New Messages", "Message One User", "Message all Users of One Type",
            "Message all Attendees in an Event", "Archive a Message","Unarchive a Message", "Delete Message"));

    /**
     * @return  the menu headers for the main menu
     */
    @Override
    public ArrayList<String> getMenu() {
        return this.MENU;
    }

    /**
     * @return  the menu headers for the message menu
     */
    @Override
    public ArrayList<String> getMessageMenu() {
        return this.MESSAGE_MENU;
    }

    public void successCreateUser(int id) {
        printSuccess(String.format("created speaker with id: %s", id));
    }

    public void noSpeakerFailure() {
        printFailure("no speaker exists");
    }

    public void speakerAlreadyChosen() {
        printFailure("speaker already chosen");
    }

    public void noUserExistWithType() {
        printFailure("no user with such type exists");
    }


    // event messages
    public void failedScheduleEvent() {
        printFailure("room or speaker not available at time");
    }

    public void successScheduleEvent() {
        printSuccess("scheduled event");
    }

    public void successCancelEvent() {
        printSuccess("Event has successfully been cancelled");
    }

    public void successRescheduleEvent() {
        printSuccess("Event has successfully been rescheduled");
    }

    public void failedRescheduleEvent() {
        printFailure("Given time is unavailable");
    }

    public void successChangeCapacity() {
        printSuccess("Event capacity successfully updated!");
    }

    public void failChangeCapacity() {
        printFailure("Fail to add new attendee; current number of attendees exceeds this capacity.");
    }

    //room messages
    public void successRegisterRoom() {
        printSuccess("Room has successfully being registered");
    }

    public void failedRegisterRoom() {
        printFailure("room number already exist");
    }

    public void noRoomFailure() { printFailure("no room exists"); }






}
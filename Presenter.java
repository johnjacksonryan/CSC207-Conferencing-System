package display;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The general Presenter.
 *
 * @author Janardan
 * @author Justin
 * @author Linjia
 * @author Yukang
 * @version 1.2
 */
public class Presenter {

    public void requestName() {
        printRequest("Name");
    }

    public void requestId() {
        printRequest("ID");
    }

    public void requestPassword() {
        printRequest("Password");
    }

    public void requestTime() {
        printRequest("Time");
    }

    public void requestDuration() {
        printRequest("Duration");
    }

    public void requestRoomNum() {
        printRequest("Room Number");
    }

    public void requestCapacity() {
        printRequest("capacity");
    }

    public void requestMessage() {
        printRequest("Message");
    }

    public void requestIndex() {
        printRequest("Select index from above");
    }

    public void requestIdChoice() {
        printRequest("Select ID from above");
    }

    public void requestVip() {
        printRequest("Vip(true or false)");
    }

    public void requestNeedsProjector() {
        printRequest("Need projector(true or false)");
    }

    public void requestHasProjector() {
        printRequest("Has projector(true or false)");
    }

    public void requestNeedsTables() {
        printRequest("Need tables(true or false)");
    }

    public void requestHasTables() {
        printRequest("Has tables(true or false)");
    }

    public void negativeIntegerUnsupported() {
        printFailure("negative integer is unsupported");
    }

    public void nonIntegerInput() {
        printInvalidType("input", "integer");
    }

    public void unsupportedWord() {
        printFailure("unsupported word");
    }

    public void invalidNumber() {
        printFailure("invalid number");
    }

    public void returnToMainMenu() {
        System.out.println("Returned to main menu.");
    }

    public void addExitOption() {
        System.out.println("    -1 to exit\n\n");
    }

    public void succeedArchiveMessage() {
        System.out.println("Successfully archived message");
    }

    public void succeedUnreadMessage() {
        System.out.println("Successfully unread message");
    }

    public void succeeUnarchiveMessage() {
        System.out.println("Successfully unarchived message");
    }

    public void requestSpeaker() {
        printRequest("Do you want hosts in the event(true or false)");
    }

    /**
     * Prints the conversations of a user
     *
     * @param list The conversations of a user
     */
    public void presentViewMessages(List<String> list) {
        System.out.println("Messages:");

        for (String message : list)
            System.out.println(message);
    }

    public void presentIndexedViewMessages(List<String> list) {
        System.out.println("Messages:");

        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + ": " + list.get(i));
        }
    }

    /**
     *
     */
    public void presentArchiveMessages(int userId) {
        System.out.println("The messages between you and " + userId + " is currently archived.");
    }

    /**
     * Prints the exit statement with format "Exit state."
     *
     * @param state the state to exit from
     */
    public void printExit(String state) {
        System.out.printf("\nExit %s.", state);
    }

    public void printTitle(String title) {
        System.out.printf("---%s---\n", title);
    }

    /**
     * Prints the request statement with format "request: "
     *
     * @param request requesting input
     */
    public void printRequest(String request) {
        System.out.printf("%s: ", request);
    }

    /**
     * Prints a notification for the success of an activity
     * with format "Successfully activity"
     *
     * @param activity activity successfully completed
     */
    public void printSuccess(String activity) {
        System.out.printf("\nSuccessfully %s.\n", activity);
    }

    /**
     * Prints a notification for the failure on activity
     * with format "Fail, reason. Please try again."
     *
     * @param reason reason of failure
     */
    public void printFailure(String reason) {
        System.out.printf("\nFail, %s. Please try again.\n", reason);
    }

    /**
     * Prints a notification for an unexpected input with format "input must be type."
     *
     * @param input input with unexpected type
     * @param type  desired type
     */
    public void printInvalidType(String input, String type) {
        System.out.printf("\n%s must be %s.\n", input, type);
    }

    /**
     * Displays exit option with format "   'exitInput' to exit."
     *
     * @param exitInput input to exit
     */
    public void printExitOption(String exitInput) {
        System.out.printf("    '%s' to exit.\n\n", exitInput);
    }

    /**
     * Displays list with a selection number in front of each option with format
     * 0. option1
     * 1. option2
     * 2. option3
     * .
     * .
     * .
     *
     * @param list list to format
     */
    public void printList(ArrayList list) {
        StringBuilder result = new StringBuilder("\n");
        for (int i = 0; i < list.size(); i++) {
            result.append(String.format("    %s. %s\n", i, list.get(i)));
        }
        System.out.println(result);
    }

    /**
     * Displays key-value pairs of map with format
     * key0. value0
     * key1. value1
     * key2. value2
     * .
     * .
     * .
     *
     * @param map map to format
     */
    public void printMap(HashMap<Integer, String> map) {
        StringBuilder result = new StringBuilder("\n");
        for (int key : map.keySet()) {
            result.append(String.format("    %s. %s\n", key, map.get(key)));
        }
        System.out.println(result);
    }
}
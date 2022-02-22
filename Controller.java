package users.base;

import display.Presenter;
import gateway.UserUpdater;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public abstract class Controller {
    private final Scanner scanner;
    private final Presenter presenter;
    private final ArrayList<String> userTypes = new ArrayList<>(Arrays.asList("Attendee", "Organizer", "Speaker"));

    /**
     * default constructor
     */
    public Controller() {
        scanner = new Scanner(System.in);
        presenter = new Presenter();
    }
    /**
     * Get using scanner
     *
     * @return scanner
     */
    public Scanner getScanner() {
        return this.scanner;
    }
    /**
     * Get list of available user type
     *
     * @return list of user type
     */
    public ArrayList<String> getUserTypes() {
        return userTypes;
    }

    /**
     * Get valid string input from user
     *
     * @param request requesting input
     * @return string input by user
     */
    public String requestString(String request) {
        String input;
        while (true) {
            if (request.equals("name")) {
                presenter.requestName();
            }
            if (request.equals("password")) {
                presenter.requestPassword();
            }
            if (request.equals("message")) {
                presenter.requestMessage();
            }
            input = getScanner().nextLine();
            return input;
        }
    }
    /**
     * Get integer input from user
     *
     * @param request requesting input
     * @return integer input by user
     */
    public int requestInt(String request) {
        while (true) {
            try {
                if (request.equals("id")) {
                    presenter.requestId();
                }
                if (request.equals("time")) {
                    presenter.requestTime();
                }
                if (request.equals("duration")) {
                    presenter.requestDuration();
                }
                if (request.equals("roomNum")) {
                    presenter.requestRoomNum();
                }
                if (request.equals("capacity")) {
                    presenter.requestCapacity();
                }
                int num = Integer.parseInt(getScanner().nextLine());
                if (num >= 0) {
                    return num;
                }
                presenter.negativeIntegerUnsupported();
            } catch (NumberFormatException e) {
                presenter.nonIntegerInput();
            }
        }
    }
    /**
     * Get boolean input from user
     *
     * @return boolean input by user
     */
    public boolean requestBoolean(String request) {
        while (true) {
            if (request.equals("vip")) {
                presenter.requestVip();
            }
            if (request.equals("needs projector")) {
                presenter.requestNeedsProjector();
            }
            if (request.equals("has projector")) {
                presenter.requestHasProjector();
            }
            if (request.equals("needs tables")) {
                presenter.requestNeedsTables();
            }
            if (request.equals("has tables")) {
                presenter.requestHasTables();
            }
            if (request.equals("speakers")) {
                presenter.requestSpeaker();
            }
            String input = getScanner().nextLine();
            if (input.equals("true")) {
                return true;
            }
            if (input.equals("false")) {
                return false;
            } else {
                presenter.unsupportedWord();
            }
        }
    }
    /**
     * Get valid integer selection input from user
     *
     * @param list       list to select from
     * @param enableExit allowance of exiting option
     * @return valid integer selection by user
     */
    public int requestFromList(ArrayList list, boolean enableExit) {
        presenter.printList(list);
        if (enableExit) {
            presenter.addExitOption();
        }
        while (true) {
            try {
                presenter.requestIndex();
                int input = Integer.parseInt(getScanner().nextLine());
                if (enableExit && input == -1) {
                    return input;
                }
                Object testObject = list.get(input);
                if (!(testObject == null)) {
                    return input;
                }
            } catch (NumberFormatException e) {
                presenter.nonIntegerInput();
            } catch (IndexOutOfBoundsException e) {
                presenter.invalidNumber();
            }
        }
    }
    /**
     * Get valid integer selection input from user
     *
     * @param map        list to select from
     * @param enableExit allowance of exiting option
     * @return valid integer selection by user
     */
    public int requestFromMap(HashMap<Integer, String> map, boolean enableExit) {
        presenter.printMap(map);
        if (enableExit) {
            presenter.addExitOption();
        }
        while (true) {
            try {
                presenter.requestIdChoice();
                int input = Integer.parseInt(getScanner().nextLine());
                if (enableExit && input == -1) {
                    return input;
                }
                String value = map.get(input);
                if (value != null) {
                    return input;
                }
                presenter.invalidNumber();
            } catch (NumberFormatException e) {
                presenter.nonIntegerInput();
            }
        }
    }

    public abstract void load();
}

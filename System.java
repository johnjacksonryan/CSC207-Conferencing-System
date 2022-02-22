package gateway.facade.system;

/**
 * Defines which modules of the program the specific interface interacts with.
 * i.e: Facades implementing EventSystem will interact with Event module.
 *
 * @author Justin
 * @version 1.0
 */
public interface System {
    void load(int id);
}
package puzzles.hoppers.model;

/**
 * This class holds messages for the GUI and PTUI to display to the user following certain actions
 */
public class HoppersClientData {
    private String message;

    /**
     * Creates a String message
     *
     * @param message String message
     */
    public HoppersClientData(String message) {
        this.message = message;
    }

    /**
     * Returns the String message
     *
     * @return String message
     */
    public String getMessage() {
        return message;
    }
}

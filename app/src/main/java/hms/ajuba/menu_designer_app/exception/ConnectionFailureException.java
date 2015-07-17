package hms.ajuba.menu_designer_app.exception;

public class ConnectionFailureException extends Exception {

    public ConnectionFailureException() {
        super("Device is not connected to internet");
    }
}

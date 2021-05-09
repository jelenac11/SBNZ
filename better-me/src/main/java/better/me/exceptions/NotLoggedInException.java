package better.me.exceptions;

public class NotLoggedInException extends Exception {

	private static final long serialVersionUID = 1L;

	public NotLoggedInException() {
        super("You must login first. No logged in user found!");
    }
}

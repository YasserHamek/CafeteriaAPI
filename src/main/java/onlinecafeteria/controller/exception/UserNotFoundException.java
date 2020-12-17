package onlinecafeteria.controller.exception;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5778714043713457415L;

	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserNotFoundException(String message) {
		super(message);
	}

	public UserNotFoundException(Throwable cause) {
		super(cause);
	}
	
}

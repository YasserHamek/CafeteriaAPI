package onlinecafeteria.controller.exception;

public class ProductNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4756678386340891761L;

	public ProductNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProductNotFoundException(String message) {
		super(message);
	}

	public ProductNotFoundException(Throwable cause) {
		super(cause);
	}

}

package onlinecafeteria.controller.exception;

public class EmptyBasketExcpetion extends RuntimeException {

	private static final long serialVersionUID = 6997684170339810644L;

	public EmptyBasketExcpetion(String message, Throwable cause) {
		super(message, cause);
	}

	public EmptyBasketExcpetion(String message) {
		super(message);
	}

	public EmptyBasketExcpetion(Throwable cause) {
		super(cause);
	}

}

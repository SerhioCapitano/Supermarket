package supermarket.exception;

public class NotEnoughChangeException extends RuntimeException {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4081803643569073760L;

	public NotEnoughChangeException(String message) {
		super(message);
	}

}

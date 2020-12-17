package onlinecafeteria.controller.exception;

public class ErrorResponse {
	
	private int statues;
	private String message; 
	private long timeResponse;
	
	public ErrorResponse() {
		
	}

	public ErrorResponse(int statues, String message, long timeResponse) {
		super();
		this.statues = statues;
		this.message = message;
		this.timeResponse = timeResponse;
	}

	public int getStatues() {
		return statues;
	}

	public void setStatues(int statues) {
		this.statues = statues;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTimeResponse() {
		return timeResponse;
	}

	public void setTimeResponse(long timeResponse) {
		this.timeResponse = timeResponse;
	}
}

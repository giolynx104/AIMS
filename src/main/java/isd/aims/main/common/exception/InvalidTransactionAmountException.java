package isd.aims.main.common.exception;

public class InvalidTransactionAmountException extends PaymentException {
	public InvalidTransactionAmountException() {
		super("ERROR: Invalid Transaction Amount!");
	}
}

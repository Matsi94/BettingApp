package exception;

public class BetAlreadyExistsException extends RuntimeException {
    public BetAlreadyExistsException(String message){
        super(message);
    }
}

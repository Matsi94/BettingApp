package exception;

public class NoDataBaseException extends RuntimeException {
    public NoDataBaseException(String message){
        super(message);
    }
}

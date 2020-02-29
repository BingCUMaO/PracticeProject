package OrmMiddleware;

public class NotFoundXmlLabelException extends Exception {
    public NotFoundXmlLabelException(String message){
        super(message);
    }

    public NotFoundXmlLabelException() {
        super();
    }
}

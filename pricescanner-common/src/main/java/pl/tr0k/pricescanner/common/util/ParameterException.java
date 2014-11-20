package pl.tr0k.pricescanner.common.util;

public class ParameterException extends Exception {

    private static final long serialVersionUID = -8536701311638879665L;

    public ParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParameterException(String message) {
        super(message);
    }

    public ParameterException(Throwable cause) {
        super(cause);
    }
}

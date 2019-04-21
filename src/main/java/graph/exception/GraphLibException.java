package main.java.graph.exception;

public class GraphLibException extends RuntimeException {

    private String errorCode;

    public GraphLibException(String message, Throwable ex) {
        super(message, ex);
    }

    public GraphLibException(String errorCode) {
        this.setErrorCode(errorCode);
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

}

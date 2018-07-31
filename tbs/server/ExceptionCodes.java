package tbs.server;

public enum ExceptionCodes {
    /* ExceptionCodes enum: Stores specific error codes to reduce error handling repetition: This class is only extended
       by ExceptionEx class in order for TBSServer to class upon the stated errors and handling errors.
     */
    //Exception fields: will be used to define repeat ERROR messages
    INVALID_FORMAT("ERROR: String format is not valid!"),
    EMPTY_INPUT("ERROR: The Input is empty!"),
    ALREADY_EXISTS("ERROR: Result already exists!"),
    DOES_NOT_EXIST("ERROR: Following ID does not exist!"),
    INVALID_MINUTES("ERROR: Minutes Input is zero or negative!"),
    INVALID_TIME_FORMAT("ERROR: Time Input is not in ISO8601 format!"),
    INVALID_PRICE("ERROR: Price Input is not valid!"),
    INVALID_SEAT("ERROR: Row and Seat Input is not valid!");

    private final String msg;

    ExceptionCodes(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }
}
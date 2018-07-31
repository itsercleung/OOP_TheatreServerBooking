package tbs.server;

public class ExceptionEx extends Exception {
    /* ExceptionEX class: uses extend of Exception and is able to use ExceptionCodes enum class from TBSServer Class
     */
    private String errorMsg;

    //ExceptionEx constructor allows to set get message field.
    ExceptionEx(ExceptionCodes code) {
        this.errorMsg = code.getMsg();
    }

    //GetErrorMsg allows code to get a specific server
    public String getErrorMsg() {
        return errorMsg;
    }
}

package com.venusource.transfer.client.exception;

/**
 * 类名称：TransException
 * 类描述	出现异常交易时发生的异常
 * 创建人：hjt
 * 修改人：hjt
 * 修改时间：2013-8-22 下午2:26:00
 * 修改时间：
 *
 * @version 1.0.0
 */
public class TransException extends Exception {

    private String errorCode;
    private String errorMsg;

    /**
     * getErrorCode(获取交易错误码)
     *
     * @return e交易错误码
     * String
     * @throws
     * @since 1.0.0
     */
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * getErrorMsg(获取交易返回码错误信息)
     *
     * @return String
     * @throws
     * @since 1.0.0
     */
    public String getErrorMsg() {

        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public TransException() {
        super();
    }

    public TransException(String message) {
        super(message);
        this.errorMsg = message;
    }

    public TransException(String code, String message) {
        super(message);
        this.errorCode = code;
        this.errorMsg = message;
    }
}

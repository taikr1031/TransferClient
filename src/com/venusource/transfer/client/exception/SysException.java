package com.venusource.transfer.client.exception;

/**
 * 类名称：SysException
 * 类描述   FTP过程时抛出的异常
 * 创建人：hjt
 * 修改人：hjt
 * 修改时间：2013-8-28 下午2:02:59
 * 修改时间：
 *
 * @version 1.0.0
 */
public class SysException extends Exception {
    /**
     * 错误码
     */
    public String errCode;

    /**
     * 错误信息
     */
    public String errMsg;

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    /**
     * @param errCode
     * @param errMsg
     */
    public SysException(String errCode, String errMsg) {
        super();
        this.errCode = errCode;
        this.errMsg = errMsg;
    }


    /**
     * @param errMsg
     */
    public SysException(String errMsg) {
        super();
        this.errMsg = errMsg;
    }


    public SysException() {
        super();
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("{[errCode:");
        buffer.append(this.getErrCode());
        buffer.append("],");
        buffer.append("[errMsg:");
        buffer.append(this.getErrMsg());
        buffer.append("]}");

        return buffer.toString();
    }


}

package com.venusource.transfer.client.exception;

/**
 * 类名称：SendTimeOutException
 * 类描述   连接超时时抛出的异常
 * 创建人：hjt
 * 修改人：hjt
 * 修改时间：2013-8-23 下午1:13:56
 * 修改时间：
 *
 * @version 1.0.0
 */
public class SendTimeOutException extends Exception {
    public SendTimeOutException(String message) {
        super(message);
    }
}

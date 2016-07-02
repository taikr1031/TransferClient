// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.venusource.transfer.client.entity;

import com.venusource.transfer.client.tcp.TcpClient;


/**
 * 类名称：BankParameter
 * 类描述   银行javabean
 * 创建人：hjt
 * 修改人：hjt
 * 修改时间：2013-8-22 下午2:20:41
 * 修改时间：
 *
 * @version 1.0.0
 */
public class BankParameter {

    private String bank; // 银行代码
    private String corpCode;//单位代码
    private String operator;//操作员代码
    private String resultMsg;// 返回码
    private TcpClient client;

    public BankParameter() {
        resultMsg = "";
    }


    public BankParameter(String bank, String corpCode, String operator) {
        resultMsg = "";
        this.corpCode = corpCode;
        this.bank = bank;
        this.operator = operator;
    }

    /**
     * getBank(获取银行代码)
     *
     * @return String
     * @throws
     * @since 1.0.0
     */
    public String getBank() {
        return bank;
    }

    /**
     * setBank(存储银行代码)
     *
     * @param bank void
     * @throws
     * @since 1.0.0
     */
    public void setBank(String bank) {
        this.bank = bank;
    }

    /**
     * getCorpCode(获取单位代码)
     *
     * @return String
     * @throws
     * @since 1.0.0
     */
    public String getCorpCode() {
        return corpCode;
    }

    /**
     * setCorpCode(存储单位代码)
     *
     * @param corpCode void
     * @throws
     * @since 1.0.0
     */
    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode;
    }

    /**
     * getOperator(获取操作员代码)
     *
     * @return String
     * @throws
     * @since 1.0.0
     */
    public String getOperator() {
        return operator;
    }

    /**
     * setOperator(存储操作员代码)
     *
     * @param operator void
     * @throws
     * @since 1.0.0
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * getResultMsg(获取返回码)
     *
     * @return String
     * @throws
     * @since 1.0.0
     */
    public String getResultMsg() {
        return resultMsg;
    }

    /**
     * setResultMsg(存储返回码)
     *
     * @param resultMsg void
     * @throws
     * @since 1.0.0
     */
    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }


    public TcpClient getClient() {
        return client;
    }


    public void setClient(TcpClient client) {
        this.client = client;
    }


}

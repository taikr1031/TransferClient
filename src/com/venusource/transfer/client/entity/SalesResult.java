// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.venusource.transfer.client.entity;


/**
 * 类名称：SalesResult
 * 类描述    交易结果javabean
 * 创建人：hjt
 * 修改人：hjt
 * 修改时间：2013-8-22 下午2:21:28
 * 修改时间：
 *
 * @version 1.0.0
 */
public class SalesResult {

    private String amount;//单位交易数
    private String amountOfMoney;//单位交易总金额
    private String businessAmount;//客户交易数
    private String businessAmountofMoney;//客户交易总金额


    public SalesResult() {
        this.amount = "0";
        this.amountOfMoney = "0";
        this.businessAmount = "0";
        this.businessAmountofMoney = "0";
    }

    public SalesResult(String amount, String amountOfMoney) {
        this.amount = "0";
        this.amountOfMoney = "0";
        this.amount = amount;
        this.amountOfMoney = amountOfMoney;
        this.businessAmount = "0";
        this.businessAmountofMoney = "0";
        this.businessAmount = amount;
        this.businessAmountofMoney = amountOfMoney;
    }

    /**
     * getAmount(获取单位转账总笔数)
     *
     * @return String
     * @throws
     * @since 1.0.0
     */
    public String getAmount() {
        return amount;
    }

    /**
     * setAmount(存储单位转账总笔数)
     *
     * @param amount void
     * @throws
     * @since 1.0.0
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * getAmountOfMoney(获取单位转账总金额)
     *
     * @return String
     * @throws
     * @since 1.0.0
     */
    public String getAmountOfMoney() {
        return amountOfMoney;
    }

    /**
     * setAmountOfMoney(存储单位转账总金额)
     *
     * @param amountOfMoney void
     * @throws
     * @since 1.0.0
     */
    public void setAmountOfMoney(String amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    /**
     * getBusinessAmount(获取经营户转账总笔数)
     *
     * @return String
     * @throws
     * @since 1.0.0
     */
    public String getBusinessAmount() {
        return businessAmount;
    }

    /**
     * setBusinessAmount(存储经营户交易总笔数)
     *
     * @param businessAmount void
     * @throws
     * @since 1.0.0
     */
    public void setBusinessAmount(String businessAmount) {
        this.businessAmount = businessAmount;
    }

    /**
     * getBusinessAmountofMoney(获取经营户转账总笔数)
     *
     * @return String
     * @throws
     * @since 1.0.0
     */
    public String getBusinessAmountofMoney() {
        return businessAmountofMoney;
    }

    /**
     * setBusinessAmountofMoney(存储经营户转账总金额)
     *
     * @param businessAmountofMoney void
     * @throws
     * @since 1.0.0
     */
    public void setBusinessAmountofMoney(String businessAmountofMoney) {
        this.businessAmountofMoney = businessAmountofMoney;
    }
}

// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.venusource.transfer.client.entity;


// Referenced classes of package com.whicss.tobacco.callcenter.bank.api.valueobject:
//			BankClientQueryParameter

/**
 * 类名称：BankOrderSalesParameter
 * 类描述    交易javabean (money的单位为分，为大于等于0的正整数)
 * 创建人：hjt
 * 修改人：hjt
 * 修改时间：2013-8-22 下午2:21:07
 * 修改时间：
 *
 * @version 1.0.0
 */
public class BankOrderSalesParameter extends BankClientQueryParameter {

    private String money;//交易金额
    private String orderid;//交易流水号
    private String reversemark = "01";//冲正标志

    /**
     * getMoney(交易金额)
     *
     * @return String
     * @throws
     * @since 1.0.0
     */
    public String getMoney() {
        return money;
    }

    /**
     * setMoney(存储交易金额)
     *
     * @param money void
     * @throws
     * @since 1.0.0
     */
    public void setMoney(String money) {
        this.money = money;
    }

    /**
     * getOrderid(获取交易流水号)
     *
     * @return String
     * @throws
     * @since 1.0.0
     */
    public String getOrderid() {
        return orderid;
    }

    /**
     * setOrderid(存储交易流水号)
     *
     * @param orderid void
     * @throws
     * @since 1.0.0
     */
    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    /**
     * getReversemark(获取冲正标志)
     *
     * @return String
     * @throws
     * @since 1.0.0
     */
    public String getReversemark() {
        return reversemark;
    }

    /**
     * setReversemark(存储冲正标志)
     *
     * @param reversemark void
     * @throws
     * @since 1.0.0
     */
    public void setReversemark(String reversemark) {
        this.reversemark = reversemark;
    }


}

// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.venusource.transfer.client.entity;


// Referenced classes of package com.whicss.tobacco.callcenter.bank.api.valueobject:
//			BankParameter

/**
 * 类名称：BankClientQueryParameter
 * 类描述   经营户 javabean
 * 创建人：hjt
 * 修改人：hjt
 * 修改时间：2013-8-21 下午3:11:41
 * 修改时间：
 *
 * @version 1.0.0
 */
public class BankClientQueryParameter extends BankParameter {
    private String account;//余额
    private String cardid;//银行卡号
    private String clientCode;//客户号
    private String idNumber;// 身份证号

    /**
     * getAccount(获取余额)
     *
     * @return String
     * @throws
     * @since 1.0.0
     */
    public String getAccount() {
        return account;
    }

    /**
     * setAccount(存储金额)
     *
     * @param account void
     * @throws
     * @since 1.0.0
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * getCardid(获取银行卡号)
     *
     * @return String
     * @throws
     * @since 1.0.0
     */
    public String getCardid() {
        return cardid;
    }

    /**
     * setCardid(存储银行卡号)
     *
     * @param cardid void
     * @throws
     * @since 1.0.0
     */
    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    /**
     * getClientCode(获取客户号)
     *
     * @return String
     * @throws
     * @since 1.0.0
     */
    public String getClientCode() {
        return clientCode;
    }

    /**
     * setClientCode(存储客户号)
     *
     * @param clientCode void
     * @throws
     * @since 1.0.0
     */
    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    /**
     * getIdNumber(获取身份证号)
     *
     * @return String
     * @throws
     * @since 1.0.0
     */
    public String getIdNumber() {
        return idNumber;
    }

    /**
     * setIdNumber(存储身份证号)
     *
     * @param idNumber void
     * @throws
     * @since 1.0.0
     */
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }


}

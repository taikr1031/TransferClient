package com.venusource.transfer.client.op;

import java.io.IOException;
import java.net.UnknownHostException;

import com.venusource.transfer.client.entity.BankClientQueryParameter;
import com.venusource.transfer.client.entity.BankOrderSalesParameter;
import com.venusource.transfer.client.entity.BankParameter;
import com.venusource.transfer.client.entity.SalesResult;
import com.venusource.transfer.client.exception.CheckException;
import com.venusource.transfer.client.exception.MsglengthException;
import com.venusource.transfer.client.exception.ReciveTimeOutException;
import com.venusource.transfer.client.exception.SendTimeOutException;
import com.venusource.transfer.client.exception.SysException;
import com.venusource.transfer.client.exception.TransException;

/**
 * 类名称：IBankOperator
 * 类描述     交易interface
 * 创建人：hjt
 * 修改人：hjt
 * 修改时间：2013-8-21 下午2:24:40
 * 修改时间：
 *
 * @version 1.0.0
 */
public interface IBankOperator {


    public abstract String register(BankParameter paramBankParameter) throws CheckException, UnknownHostException, IOException, SendTimeOutException, ReciveTimeOutException, MsglengthException, TransException;

    public abstract String unregister(BankParameter paramBankParameter) throws CheckException, UnknownHostException, IOException, SendTimeOutException, ReciveTimeOutException, MsglengthException, TransException;

    public abstract String queryClientBalance(BankClientQueryParameter paramBankClientQueryParameter) throws CheckException, UnknownHostException, IOException, SendTimeOutException, ReciveTimeOutException, MsglengthException, TransException;

    public abstract boolean commerceToTransferAccounts(BankOrderSalesParameter paramBankOrderSalesParameter) throws CheckException, UnknownHostException, IOException, SendTimeOutException, ReciveTimeOutException, MsglengthException, TransException;

    public abstract String queryCorpBalance(BankParameter paramBankParameter) throws CheckException, UnknownHostException, IOException, SendTimeOutException, ReciveTimeOutException, MsglengthException, TransException;

    // public abstract String getBailAccountBal(BankParameter paramBankParameter) throws CheckException, UnknownHostException, IOException, SendTimeOutException, ReciveTimeOutException, MsglengthException, TransException;

    public abstract String getTransResult(BankOrderSalesParameter paramBankOrderSalesParameter) throws CheckException, UnknownHostException, IOException, SendTimeOutException, ReciveTimeOutException, MsglengthException, TransException;

    //  public abstract boolean avalilToCautionAccounts(BankOrderSalesParameter  paramBankOrderSalesParameter ) throws CheckException, UnknownHostException, IOException, SendTimeOutException, ReciveTimeOutException, MsglengthException, TransException;

    public abstract SalesResult banlanceOfAccount(BankParameter paramBankParameter) throws CheckException, UnknownHostException, IOException, SendTimeOutException, ReciveTimeOutException, MsglengthException, TransException;

    public abstract String downloadCheckFile(BankParameter paramBankParameter, String filedate, String filepath) throws CheckException, UnknownHostException, IOException, SendTimeOutException, ReciveTimeOutException, MsglengthException, TransException, SysException;

    public abstract void downloadFromJS(BankParameter bp, String filedate, String filepath) throws TransException, SysException, IOException, CheckException;
    //  public abstract void returnBailAccount(BankParameter paramBankParameter,String filedate,String filepath)throws CheckException, UnknownHostException, IOException, SendTimeOutException, ReciveTimeOutException, MsglengthException, TransException, SysException;
}

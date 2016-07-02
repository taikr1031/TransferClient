package jh.copy;

import java.io.IOException;
import java.net.UnknownHostException;

import com.venusource.transfer.client.entity.BankOrderSalesParameter;
import com.venusource.transfer.client.entity.BankParameter;
import com.venusource.transfer.client.exception.CheckException;
import com.venusource.transfer.client.exception.MsglengthException;
import com.venusource.transfer.client.exception.ReciveTimeOutException;
import com.venusource.transfer.client.exception.SendTimeOutException;
import com.venusource.transfer.client.exception.TransException;
import com.venusource.transfer.client.op.BankOperatorImpl;


public class CommerceToTransferAccountsTest {
    public static void main(String[] args) {
        BankOperatorImpl boi = new BankOperatorImpl();  //建立一个BankOperator对象，

        BankOrderSalesParameter bos = new BankOrderSalesParameter();

        bos.setBank("JH");
        bos.setCorpCode("422405");
        bos.setCardid("2665099980101025172");
        bos.setClientCode("4224050103010");
        bos.setIdNumber("422423196310230017");
        bos.setOperator("9220");
        bos.setOrderid("A202006");
        bos.setReversemark("0");
        bos.setMoney("6.01");

        try {
            boolean transresult = boi.commerceToTransferAccounts(bos);//经营户转账交易
            System.out.println(transresult);
            System.out.println(bos.getResultMsg());
        } catch (TransException e) {
            //交易失败抛出的异常
        } catch (CheckException e) {
            //传入的参数校验异常
        } catch (MsglengthException e) {
            //返回的报文长度异常
        } catch (UnknownHostException e) {
            //未知主机异常
        } catch (IOException e) {
            //IO处理异常
        } catch (SendTimeOutException e) {
            //发送时间超时异常
        } catch (ReciveTimeOutException e) {
        }   //接收报文时间异常
    }

}

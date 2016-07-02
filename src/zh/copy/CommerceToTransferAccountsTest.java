package zh.copy;

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

        bos.setBank("ZY");
        bos.setCorpCode("14211700");
        bos.setCardid("4563513000327160662");
        bos.setClientCode("4208120005");
        bos.setIdNumber("420102198101020005");
        bos.setOperator("9220");
        bos.setOrderid("A2000017");
        bos.setReversemark("0");
        bos.setMoney("14.67");

        try {
            boolean transresult = boi.commerceToTransferAccounts(bos);//经营户转账交易
            System.out.println(bos.getResultMsg());
        } catch (TransException e) {
            e.printStackTrace();
            //交易失败抛出的异常
        } catch (CheckException e) {
            e.printStackTrace();
            //传入的参数校验异常
        } catch (MsglengthException e) {
            e.printStackTrace();
            //返回的报文长度异常
        } catch (UnknownHostException e) {
            e.printStackTrace();
            //未知主机异常
        } catch (IOException e) {
            e.printStackTrace();
            //IO处理异常
        } catch (SendTimeOutException e) {
            //发送时间超时异常
        } catch (ReciveTimeOutException e) {
        }   //接收报文时间异常
    }

}

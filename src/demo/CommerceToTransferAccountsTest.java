package demo;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.concurrent.Callable;

import com.venusource.transfer.client.entity.BankOrderSalesParameter;
import com.venusource.transfer.client.entity.BankParameter;
import com.venusource.transfer.client.exception.CheckException;
import com.venusource.transfer.client.exception.MsglengthException;
import com.venusource.transfer.client.exception.ReciveTimeOutException;
import com.venusource.transfer.client.exception.SendTimeOutException;
import com.venusource.transfer.client.exception.TransException;
import com.venusource.transfer.client.op.BankOperatorImpl;

public class CommerceToTransferAccountsTest implements Callable<Object> {
    public static void main(String[] args) {
//        call();
    }

    public Object call() {
        BankOperatorImpl boi = new BankOperatorImpl();  //建立一个BankOperator对象，

        BankOrderSalesParameter bos = new BankOrderSalesParameter();

        bos.setBank("NH");
        bos.setCorpCode("420380");
        bos.setCardid("6228480050015575413");
        bos.setClientCode("4203000204092");
        bos.setIdNumber("420321197601064113");
        bos.setOperator("9220");
        bos.setOrderid("10000015");
        bos.setReversemark("0");
        bos.setMoney("0.01");
        boolean transresult = false;

        try {
            transresult = boi.commerceToTransferAccounts(bos);//经营户转账交易
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
        return transresult;
    }

}

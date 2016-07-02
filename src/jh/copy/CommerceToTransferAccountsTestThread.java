package jh.copy;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Random;

import com.venusource.transfer.client.entity.BankOrderSalesParameter;
import com.venusource.transfer.client.entity.BankParameter;
import com.venusource.transfer.client.exception.CheckException;
import com.venusource.transfer.client.exception.MsglengthException;
import com.venusource.transfer.client.exception.ReciveTimeOutException;
import com.venusource.transfer.client.exception.SendTimeOutException;
import com.venusource.transfer.client.exception.TransException;
import com.venusource.transfer.client.op.BankOperatorImpl;


public class CommerceToTransferAccountsTestThread {
    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            A a = new A();
            a.start();
        }
    }

}

class A extends Thread {
    public void run() {
        BankOperatorImpl boi = new BankOperatorImpl();  //建立一个BankOperator对象，

        BankOrderSalesParameter bos = new BankOrderSalesParameter();

        bos.setBank("JH");
        bos.setCorpCode("422405");
        bos.setCardid("2665099980101025172");
        bos.setClientCode("4224050103010");
        bos.setIdNumber("422423196310230017");
        bos.setOperator("9220JH");
        Random ran = new Random();
        int len = ran.nextInt(3);
        len = 10000000 + len;
        bos.setOrderid("A" + String.valueOf(len));
        bos.setReversemark("0");
        bos.setMoney("1");

        try {
            long start = System.currentTimeMillis();
            System.out.println("start:" + start);
            boi.commerceToTransferAccounts(bos);//经营户转账交易
            long end = System.currentTimeMillis();
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

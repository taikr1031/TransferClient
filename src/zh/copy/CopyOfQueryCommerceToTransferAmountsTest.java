package zh.copy;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.venusource.transfer.client.entity.BankClientQueryParameter;
import com.venusource.transfer.client.entity.BankOrderSalesParameter;
import com.venusource.transfer.client.exception.CheckException;
import com.venusource.transfer.client.exception.MsglengthException;
import com.venusource.transfer.client.exception.ReciveTimeOutException;
import com.venusource.transfer.client.exception.SendTimeOutException;
import com.venusource.transfer.client.exception.TransException;
import com.venusource.transfer.client.op.BankOperatorImpl;

public class CopyOfQueryCommerceToTransferAmountsTest {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(50);

        for (int i = 1; i <= 10; i++) {
            GHQ a = new GHQ(i);
            pool.execute(a);
        }

//		for (int i = 1; i <=10; i++) {
//			YZQ a = new YZQ(i);
//			pool.execute(a);
//		}
        pool.shutdown();
    }
}


class GHQ extends Thread {
    int a;

    public GHQ(int a) {
        this.a = a;
    }

    public void run() {
        long start = new Date().getTime();
        BankOrderSalesParameter bos = new BankOrderSalesParameter();
        BankOperatorImpl boi = new BankOperatorImpl();  //建立一个BankOperator对象，
        bos.setBank("ZY");
        bos.setCorpCode("14211700");
        bos.setCardid("4563513000327160662");
        bos.setClientCode("4208120005");
        bos.setIdNumber("420102198101020005");
        bos.setOperator("9220");
        for (int i = 0; i < 50; i++) {
            bos.setOrderid("h" + String.valueOf(a * 10000 + i));
            bos.setMoney(String.valueOf(i * 1 + 0.01 * i + 0.01));
            System.out.println(bos.getResultMsg());
            try {
                boi.commerceToTransferAccounts(bos);//经营户转账交易
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
                e.printStackTrace();
                //发送时间超时异常
            } catch (ReciveTimeOutException e) {
                e.printStackTrace();
            }   //接收报文时间异常

        }
    }
}

class YZQ extends Thread {
    int a;

    public YZQ(int a) {
        this.a = a;
    }

    public void run() {
        long start = new Date().getTime();
        System.out.println("start:" + start);
        BankOrderSalesParameter bos = new BankOrderSalesParameter();

        bos.setBank("YZ");
        bos.setCardid("605210789200013917");
        bos.setClientCode("4224050106028");
        bos.setIdNumber("420101196001011234");
        bos.setCorpCode("422405");
        bos.setOperator("9220");
        bos.setOrderid((10000001 + a) + "");
        bos.setReversemark("0");
        bos.setMoney("10");

        BankOperatorImpl boi = new BankOperatorImpl();  //建立一个BankOperator对象，

        try {
            boi.commerceToTransferAccounts(bos);//经营户转账交易
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
            e.printStackTrace();
            //发送时间超时异常
        } catch (ReciveTimeOutException e) {
            e.printStackTrace();
        }   //接收报文时间异常
        long stop = new Date().getTime();
        System.out.println("stop:" + stop);
        System.out.println(stop - start);

    }
}
package test;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.venusource.transfer.client.entity.BankClientQueryParameter;
import com.venusource.transfer.client.entity.BankParameter;
import com.venusource.transfer.client.exception.CheckException;
import com.venusource.transfer.client.exception.MsglengthException;
import com.venusource.transfer.client.exception.ReciveTimeOutException;
import com.venusource.transfer.client.exception.SendTimeOutException;
import com.venusource.transfer.client.exception.TransException;
import com.venusource.transfer.client.op.BankOperatorImpl;
import com.venusource.transfer.client.util.Datautil;

public class QueryClientBalanceTest extends Thread {


    public void run() {
        BankOperatorImpl boi = new BankOperatorImpl();  //建立一个BankOperator对象

        BankClientQueryParameter bcp = new BankClientQueryParameter();
        bcp.setBank("NH");
        bcp.setCardid("6225880149671753");
        bcp.setClientCode("00001");
        bcp.setCorpCode("222222");
        bcp.setIdNumber("411111190004050832");
        bcp.setOperator("9220NH");

        try {
            String money = boi.queryClientBalance(bcp);//经营户余额查询
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

    public static void main(String[] args) throws InterruptedException {
        QueryClientBalanceTest t = new QueryClientBalanceTest();
        for (int i = 0; i < 10; i++) {
            t.start();

            System.out.println(t.getSystemTimeString());
        }

    }


    private static String getSystemDateString() {
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(today);
    }

    /**
     * 获取系统当前时间
     *
     * @return 系统当前日期
     */
    private static String getSystemTimeString() {
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("HHmmss");
        return format.format(today);
    }
}


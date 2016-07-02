package demo;

import com.venusource.transfer.client.entity.BankParameter;
import com.venusource.transfer.client.exception.*;
import com.venusource.transfer.client.op.BankOperatorImpl;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class RegisterTest implements Callable<Object> {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        call();
    }

    public Object call() {
        //建立一个BankOperator对象，tcpclient对象可以通过构造方法或者set方法传入
        BankOperatorImpl boi = new BankOperatorImpl();

        BankParameter bp = new BankParameter();
        bp.setBank("NH");
        bp.setCorpCode("420380");
        bp.setOperator("9220");
        String msg = "";
        try {
            msg = boi.register(bp);//调用签到方法
        } catch (TransException e) {
            System.out.println(e.getMessage());
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
        return msg;
    }
}

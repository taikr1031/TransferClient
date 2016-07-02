package gh.copy;

import java.io.IOException;
import java.net.UnknownHostException;

import com.venusource.transfer.client.entity.BankClientQueryParameter;
import com.venusource.transfer.client.entity.BankParameter;
import com.venusource.transfer.client.exception.CheckException;
import com.venusource.transfer.client.exception.MsglengthException;
import com.venusource.transfer.client.exception.ReciveTimeOutException;
import com.venusource.transfer.client.exception.SendTimeOutException;
import com.venusource.transfer.client.exception.TransException;
import com.venusource.transfer.client.op.BankOperatorImpl;

public class QueryClientBalanceTest {
    public static void main(String[] args) {
        BankOperatorImpl boi = new BankOperatorImpl();  //建立一个BankOperator对象

        BankClientQueryParameter bcp = new BankClientQueryParameter();
        bcp.setBank("GH");
        bcp.setCorpCode("14211400");
        bcp.setCardid("3202010001003949963");
        bcp.setClientCode("420114103080");
        bcp.setIdNumber("420121195709042290");
        bcp.setOperator("9880");

        try {
            String money = boi.queryClientBalance(bcp);//经营户余额查询
            System.out.println(money);
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
    }
}

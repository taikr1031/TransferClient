package zh.copy;

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
        bcp.setBank("ZY");
        bcp.setCorpCode("420780");
//		bcp.setCardid("6221885200231480965");
//		bcp.setClientCode("4207041049620");
//		bcp.setIdNumber("420700196903216202");


        bcp.setOperator("9220");

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
}

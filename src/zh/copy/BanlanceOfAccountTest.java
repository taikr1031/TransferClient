package zh.copy;

import java.io.IOException;
import java.net.UnknownHostException;

import com.venusource.transfer.client.entity.BankParameter;
import com.venusource.transfer.client.exception.CheckException;
import com.venusource.transfer.client.exception.MsglengthException;
import com.venusource.transfer.client.exception.ReciveTimeOutException;
import com.venusource.transfer.client.exception.SendTimeOutException;
import com.venusource.transfer.client.exception.TransException;
import com.venusource.transfer.client.op.BankOperatorImpl;


public class BanlanceOfAccountTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        BankOperatorImpl boi = new BankOperatorImpl();  //建立一个BankOperator对象，tcpclient对象可以通过构造方法或者set方法传入

        BankParameter bp = new BankParameter();
        bp.setBank("ZY");
        bp.setCorpCode("14211700");
        bp.setOperator("9220");

        try {
            boi.banlanceOfAccount(bp);//对账查询交易
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

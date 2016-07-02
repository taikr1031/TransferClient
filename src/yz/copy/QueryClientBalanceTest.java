package yz.copy;

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
        bcp.setBank("YZ");
//		bcp.setCardid("605310022200000739");
//		bcp.setClientCode("4207041041490");
//		bcp.setIdNumber("350524197311026313");

//		bcp.setCardid("605310022200000722");
//		bcp.setClientCode("4207041041500");
//		bcp.setIdNumber("420704197502231559");


        bcp.setCardid("605360001260000004");
        bcp.setClientCode("4228231127251");
        bcp.setIdNumber("420111197203064050");//420111197203064050
//		bcp.setCardid("605310009200288526");
//		bcp.setClientCode("4207801516038");
//		bcp.setIdNumber("42070419660202435X");


        bcp.setCorpCode("422804");
        bcp.setOperator("7777");

        try {
            String money = boi.queryClientBalance(bcp);//经营户余额查询
            System.out.println(money);
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

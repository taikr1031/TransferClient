package jh.copy;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import com.venusource.transfer.client.entity.BankParameter;
import com.venusource.transfer.client.exception.CheckException;
import com.venusource.transfer.client.exception.MsglengthException;
import com.venusource.transfer.client.exception.ReciveTimeOutException;
import com.venusource.transfer.client.exception.SendTimeOutException;
import com.venusource.transfer.client.exception.SysException;
import com.venusource.transfer.client.exception.TransException;
import com.venusource.transfer.client.op.BankOperatorImpl;

public class DownloadCheckFileTest {
    public static void main(String[] args) {
        BankOperatorImpl boi = new BankOperatorImpl();  //建立一个BankOperator对象，

        BankParameter bp = new BankParameter();
        bp.setBank("JH");
        bp.setCorpCode("420780");
        bp.setOperator("9220");

        try {
            //String filepath = boi.downloadCheckFile(bp, "20140225", "D:\\");//对账下载文件交易,交易日期传入则下载当天的交易明细，若传入空 则默认为当天
            boi.downloadFromJS(bp, "20140225", "D:\\");
        } catch (TransException e) {
            e.printStackTrace();
            //交易失败抛出的异常
        } catch (CheckException e) {
            System.out.println("1111111111111111111111");
            e.printStackTrace();
            //传入的参数校验异常
        } catch (UnknownHostException e) {
            e.printStackTrace();
            //未知主机异常
        } catch (IOException e) {
            e.printStackTrace();
            //IO处理异常
        } catch (SysException e) {
            e.printStackTrace();
            // ftp 异常
            e.printStackTrace();
        }
    }

}

package yz.copy;


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


public class CopyOfCommerceToTransferAccountsTest {
    public static void main(String[] args) {
        BankOperatorImpl boi = new BankOperatorImpl();
        for (int i = 0; i < 5; i++) {

            BankOrderSalesParameter bos = new BankOrderSalesParameter();

            bos.setBank("YZ");
            bos.setCardid("605210789200013917");
            bos.setClientCode("4224050106028");
            bos.setIdNumber("420101196001011234");
            bos.setCorpCode("422405");
            bos.setOperator("9220YZ");
            Random ran = new Random();
            bos.setOrderid(String.valueOf(12282913 + i));
            bos.setReversemark("0");
            bos.setMoney("10");

            try {
                boi.commerceToTransferAccounts(bos);
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (CheckException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SendTimeOutException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ReciveTimeOutException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (MsglengthException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (TransException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }//缁忚惀鎴疯浆璐︿氦鏄�
        }
    }

}


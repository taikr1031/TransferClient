package test;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;

import com.venusource.transfer.client.exception.SysException;
import com.venusource.transfer.client.ftp.FtpClient;

public class FTPtest {
    public static void main(String[] args) {
        FTPClient ftpclient = new FTPClient();

        try {
            ftpclient.connect("127.0.0.1", 21);
            ftpclient.login("admin", "admin");
        } catch (SocketException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            int a = ftpclient.cwd("JH");
            System.out.println(a);
            if (550 == ftpclient.getReplyCode()) {
                System.out.println(ftpclient.getReplyCode());
                ftpclient.makeDirectory("JH");
                ftpclient.cwd("JH");

                ftpclient.cwd("222222");

                if (550 == ftpclient.getReplyCode()) {
                    ftpclient.makeDirectory("222222");
                    ftpclient.cwd("222222");

                    ftpclient.cwd("20130923");
                    if (550 == ftpclient.getReplyCode()) {
                        ftpclient.makeDirectory("20130923");
                        ftpclient.cwd("20130923");
                    }

                }

            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

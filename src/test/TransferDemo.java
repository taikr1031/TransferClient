package test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * 2013-8-23
 * 下午2:21:24
 * by mzw
 * 模拟 挡板程序
 */
public class TransferDemo extends Thread {
    private int port;
    public static Map<String, String> map = new HashMap<String, String>();
    public static String[] ret = new String[]{
            "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13",
            "14", "20", "96", "99"
    };

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    static {
        map.put("92000", "NH|222222    |9220NH    |00");
        map.put("93000", "NH|222222    |9220GH    |00");
        map.put("11100", "NH|222222    |9220NH    |00001     |6225880149671753              |411111190004050832  |0000000000100000|00");
        map.put("11101", "NH|222222    |9220NH    |0000000000100000|00");
        map.put("11102", "NH|222222    |9220NH    |0000000000100000|00");
        map.put("11103", "NH|222222    |9220NH    |    000028|00|00");
        map.put("12100", "NH|222222    |9220NH    |00001         |6225880149671753              |411111190004050832  |0000000000200000|    000028|00|1 ");
        map.put("12102", "NH|222222    |922000    |00|20130827");
        map.put("12103", "NH|222222    |9220NH    |0000000000200000|    000028|00|1 ");
        map.put("93100", "NH|222222    |9220NH    |00|0000000089|000000600000|0000000089|000000600000");
        map.put("93101", "NH|222222    |922000    |00|20130827");

    }

    public static void main(String[] args) {
        TransferDemo server = new TransferDemo();
        server.setPort(9999);
        server.start();
        System.out.println("模拟电子结算支付平台已启动！");
    }

    public void init() {
        ServerSocket s = null;
        try {
            s = new ServerSocket(port);
            s.setReuseAddress(true);
            while (true) {

                Socket socket = null;
                socket = s.accept();
                InputStream in = socket.getInputStream();

                byte[] b = new byte[10 * 1024];

                int len = 0;

                len = in.read(b);

                byte[] bb = new byte[len];
                System.arraycopy(b, 0, bb, 0, len);
                String str = new String(bb, "GBK");
                System.out.println("接收到的报文:[" + str + "]");
                String[] transmsg = str.split("\\|");
                String tranno = transmsg[0];

                String message = TransferDemo.map.get(tranno);
                //返回报文
                OutputStream out = socket.getOutputStream();
                out.write(message.getBytes());
                System.out.println("[" + tranno + "]  返回内容[" + message + "]");

                socket.close();
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void run() {
        init();
    }

    public String getStr(int seed) {
        Random ran = new Random();
        int len = ran.nextInt(seed);
        return ret[len];
    }

}

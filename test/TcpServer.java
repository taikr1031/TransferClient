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
public class TcpServer extends Thread {
    private int port;
    private String bankName;
    public static Map<String, String> map = new HashMap<String, String>();
    public static String[] ret = new String[]{
            "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13",
            "14", "20", "96", "99"
    };

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    static {
        map.put("92000", "0041222222    92000172327201308219220NH    00");
        map.put("93000", "0041222222    93000172327201308219220NH    00");
        map.put("11100", "0091222222    11100171327201308219220NH    0000001        0411111190004050832  0000000000100000");
        map.put("11101", "0057222222    11101172327201308219220NH    000000000000100000");
        map.put("11102", "0057222222    11102172327201308219220NH    000000000000100000");
        map.put("11103", "0056222222    11103172327201308219220GH    00       00002800");
        map.put("12100", "0090222222    12100071327201308219220GH    1 00       00002800001         411111190004050832  ");
        map.put("12103", "0071222222    12103172327201308219220GH    1 00        00002800001         ");
        map.put("93100", "0085222222    93100172327201308219220GH    0000000000890000006000000000000089000000600000");

    }

    public static void main(String[] args) {
        TcpServer server = new TcpServer();
        server.setPort(40000);
        server.setBankName("NH");
        server.start();

//        TcpServer server1 = new TcpServer();
//        server1.setPort(20000);
//        server1.setBankName("GH");
//        server1.start();
//
//        TcpServer server2 = new TcpServer();
//        server2.setPort(30000);
//        server2.setBankName("JH");
//        server2.start();
//
//
//        TcpServer server3 = new TcpServer();
//        server3.setPort(50000);
//        server3.setBankName("XH");
//        server3.start();
//
//        TcpServer server4 = new TcpServer();
//        server4.setPort(60000);
//        server4.setBankName("YZ");
//        server4.start();
//
//        TcpServer server5 = new TcpServer();
//        server5.setPort(20001);
//        server5.setBankName("ZH");
//        server5.start();
    }

    public void init() {
        ServerSocket s = null;
        try {
            s = new ServerSocket(port);
            s.setReuseAddress(true);
            System.out.println("bank:" + this.getBankName() + ";port:" + this.getPort());
            while (true) {
                Socket socket = null;
                socket = s.accept();
                InputStream in = socket.getInputStream();

                byte[] b = new byte[10 * 1024];

                int len = 0;

                len = in.read(b);

                byte[] bb = new byte[len];

                System.arraycopy(b, 0, bb, 0, len);

                //取交易码
                byte[] tranByte = new byte[5];
                System.arraycopy(bb, 14, tranByte, 0, 5);

                String tranno = new String(tranByte);
                System.out.println(this.getBankName() + " 交易码:" + tranno);

                String message = TcpServer.map.get(tranno.trim());
                String ret = getStr(18);
                System.out.println(ret);
                //返回报文
                OutputStream out = socket.getOutputStream();
                out.write(message.getBytes());
                System.out.println(this.getBankName() + "  返回内容[" + message + "]");

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

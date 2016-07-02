package com.venusource.transfer.client.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.venusource.transfer.client.exception.ConnectBreakException;
import com.venusource.transfer.client.exception.ReciveTimeOutException;
import com.venusource.transfer.client.exception.SendTimeOutException;

/**
 * 2013-8-16
 * 上午10:52:52
 * by mzw
 * tcp client 通讯类
 */
public class TcpClient {
    private Log log = LogFactory.getLog(TcpClient.class);
    private String host;
    private int port;
    private int connectTimeout;
    private int receiveTimeout;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getReceiveTimeout() {
        return receiveTimeout;
    }

    public void setReceiveTimeout(int receiveTimeout) {
        this.receiveTimeout = receiveTimeout;
    }

    /**
     * 发送消息
     *
     * @param mesg 发送内容
     * @return String
     * @throws ConnectBreakException
     */
    public String sendMsg(String mesg) throws UnknownHostException, IOException, SendTimeOutException, ReciveTimeOutException {
        log.error("send:[" + mesg + "]");
        String str;
        SocketAddress sa = new InetSocketAddress(host, port);
        Socket socket = new Socket();
        try {
//            socket.setSoLinger(true, 0);
            socket.connect(sa, connectTimeout);//设置链接超时时间
        } catch (SocketTimeoutException se) {
            se.printStackTrace();
            throw new SendTimeOutException("建立TCP连接超时:" + host);
        } catch (ConnectException e) {
            throw new IOException("建立TCP时抛出异常:" + e.getMessage());
        }
        socket.setTcpNoDelay(true);
        socket.getOutputStream().write(mesg.getBytes());
        socket.setSoTimeout(receiveTimeout);//设置接收超时时间
        byte[] old = new byte[10 * 1024];
        int len;
        try {
            InputStream input = socket.getInputStream();
            len = input.read(old);
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            throw new ReciveTimeOutException("返回超时");
        } catch (SocketException e) {
            throw new IOException("建立TCP时抛出异常:" + e.getMessage());
        }
        if (len == -1) {
            throw new IOException("未读取到文件 ");
        }
        byte[] newbyte = new byte[len];
        System.arraycopy(old, 0, newbyte, 0, len);

        str = new String(newbyte, "GBK");
        log.error("recive:[" + str + "]");
        return str;
    }

}


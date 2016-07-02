package com.venusource.transfer.client.ftp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.venusource.transfer.client.exception.SysException;


/**
 * 2013-8-14
 * 下午3:36:26
 * by mzw
 * 系统提供ftpclient  默认是ftp 被动模式传输文件
 */
public class FtpClient {
    private Log log = LogFactory.getLog(FtpClient.class);
    private String ftphost;//ip 地址
    private int ftpport;//ftp 端口
    private String ftpuser;//用户
    private String ftppassword;//密码
    private String ftppath;//路径


    public FtpClient() {

    }

    public FtpClient(String host, int port, String user, String password, String path) {
        this.ftphost = host;
        this.ftpport = port;
        this.ftpuser = user;
        this.ftppassword = password;
        this.ftppath = path;
    }

    public String getFtphost() {
        return ftphost;
    }

    public void setFtphost(String ftphost) {
        this.ftphost = ftphost;
    }

    public int getFtpport() {
        return ftpport;
    }

    public void setFtpport(int ftpport) {
        this.ftpport = ftpport;
    }

    public String getFtpuser() {
        return ftpuser;
    }

    public void setFtpuser(String ftpuser) {
        this.ftpuser = ftpuser;
    }

    public String getFtppassword() {
        return ftppassword;
    }

    public void setFtppassword(String ftppassword) {
        this.ftppassword = ftppassword;
    }

    public String getFtppath() {
        return ftppath;
    }

    public void setFtppath(String ftppath) {
        this.ftppath = ftppath;
    }

    /**
     * @return 登录失败返回null
     * FTPClient
     * @throws SysException
     */
    public FTPClient connectServer() throws SysException {
        try {
            int replyCode;
            FTPClient client = new FTPClient();
            if (this.getFtpport() > 0) {
                client.connect(this.getFtphost(), this.getFtpport());
            } else {
                client.connect(this.getFtphost());
            }
            client.login(this.getFtpuser(), this.getFtppassword());

            client.setFileType(FTP.BINARY_FILE_TYPE);//二进制流的方式传输

            client.setDataTimeout(120000);

            replyCode = client.getReplyCode();


            if (FTPReply.isPositiveCompletion(replyCode)) {
                return client;
            } else {
                log.info("登录失败！");
                throw new SysException("FTP_001", "登录ftp服务器异常发生:[" + this.getFtphost() + ":" + this.getFtpport() + "] 登录失败");
            }
        } catch (Exception e) {
            log.error("登录ftp服务器发生错误:" + e.getMessage());
            throw new SysException("FTP_001", "登录ftp服务器异常发生:[" + this.getFtphost() + ":" + this.getFtpport() + "]");
        }
    }

    /**
     * 释放ftp连接
     *
     * @param client void
     */
    public void releaseConnect(FTPClient client) {
        try {
            boolean flag = client.logout();
            log.info("logout:[" + flag + "]");
            client.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取文件流
     * 调用此方法 当处理数据完成必须调用  completePendingCommand()
     * 告知ftp服务器已经读取完成
     *
     * @param client
     * @param remoteFile
     * @return
     * @throws IOException  InputStream
     * @throws SysException
     */
    public InputStream getFile(FTPClient client, String remoteFile) throws IOException, SysException {
        InputStream in = client.retrieveFileStream(remoteFile);
        if (in == null) {
            throw new SysException("FTP_002", "[文件不存在]");
        }
        return in;
    }

    /**
     * 上传文件
     *
     * @param client
     * @param remoteFile
     * @param output
     * @throws IOException void
     */
    public void getFile(FTPClient client, String remoteFile, OutputStream output) throws IOException {
        client.retrieveFile(remoteFile, output);
    }


    /**
     * 上传文件
     *
     * @return boolean
     */
    public boolean uploadFile(FTPClient client, String remoteFile, InputStream input) throws IOException {
        return client.storeFile(remoteFile, input);
    }


}

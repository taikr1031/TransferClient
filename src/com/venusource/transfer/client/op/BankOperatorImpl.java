package com.venusource.transfer.client.op;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;


import com.venusource.transfer.client.codec.DefaultDecode;
import com.venusource.transfer.client.entity.BankClientQueryParameter;
import com.venusource.transfer.client.entity.BankOrderSalesParameter;
import com.venusource.transfer.client.entity.BankParameter;
import com.venusource.transfer.client.entity.SalesResult;
import com.venusource.transfer.client.exception.CheckException;
import com.venusource.transfer.client.exception.MsglengthException;
import com.venusource.transfer.client.exception.ReciveTimeOutException;
import com.venusource.transfer.client.exception.SendTimeOutException;
import com.venusource.transfer.client.exception.SysException;
import com.venusource.transfer.client.exception.TransException;
import com.venusource.transfer.client.ftp.FtpClient;
import com.venusource.transfer.client.tcp.TcpClient;
import com.venusource.transfer.client.util.CheckUtil;
import com.venusource.transfer.client.util.Datautil;

/**
 * 类名称：BankOperatorImpl
 * 类描述     API对外接口，根据交易不同调用不同的方法
 * 创建人：hjt
 * 修改人：hjt
 * 修改时间：2013-8-21 上午11:28:01
 * 修改时间：
 *
 * @version 1.0.0
 */
public class BankOperatorImpl implements IBankOperator {
    private static Log log = LogFactory.getLog(BankOperatorImpl.class);

    private static String ftphost;
    private static int ftpport;
    private static String ftpuser;
    private static String ftppassword;
    private static String ftppath;
    private static String tcphost;
    private static int tcpport;
    private static int tcpconnecttimeout;
    private static int tcprecivetimeout;
    private static int tcpothertimeout;

    public BankOperatorImpl() {

    }

    private static Map<String, String> errorCodeMap = new HashMap<String, String>();

    static {
        errorCodeMap.put("00", "交易成功");
        errorCodeMap.put("01", "交易失败");
        errorCodeMap.put("02", "无效单位");
        errorCodeMap.put("03", "无效交易");
        errorCodeMap.put("04", "客户代码与账号不对应");
        errorCodeMap.put("05", "客户不属于本单位");
        errorCodeMap.put("06", "无效账号");
        errorCodeMap.put("07", "交易部分成功");
        errorCodeMap.put("08", "余额不足");
        errorCodeMap.put("09", "交易报文格式错");
        errorCodeMap.put("10", "账号已冻结");
        errorCodeMap.put("11", "账号已挂失");
        errorCodeMap.put("12", "代销户");
        errorCodeMap.put("13", "已销户");
        errorCodeMap.put("14", "通讯验证码错");
        errorCodeMap.put("20", "大机未处理");
        errorCodeMap.put("21", "贷记卡状态不正常");
        errorCodeMap.put("22", "贷记卡已过期");
        errorCodeMap.put("23", "大机超时，可能已转账");
        errorCodeMap.put("24", "解密错误");
        errorCodeMap.put("96", "系统故障");
        errorCodeMap.put("99", "其它错误");
        errorCodeMap.put("aa", "未签到");
        errorCodeMap.put("bb", "输入参数不合协议");
        errorCodeMap.put("cc", "网络故障");
        errorCodeMap.put("33", "创建FTP目录时发生异常");
        errorCodeMap.put("34", "文件不存在");
        errorCodeMap.put("35", "连接超时");
        errorCodeMap.put("36", "连接已关闭");
        errorCodeMap.put("37", "银行返回的消息为空");
        errorCodeMap.put("38", "接收超时");
        errorCodeMap.put("39", "通讯IO异常");
        errorCodeMap.put("40", "其他未知异常");

        try {
            InputStream in = BankOperatorImpl.class.getClassLoader().getResourceAsStream("client.properties");
            Properties p = new Properties();
            p.load(in);

            ftphost = p.getProperty("ftp.host");
            ftpport = Integer.parseInt(p.getProperty("ftp.port").toString());
            ftpuser = p.getProperty("ftp.user");
            ftppassword = p.getProperty("ftp.password");
            log.error("ftphost:[" + ftphost + "]");
            System.out.println("ftphost:[" + ftphost + "]");
            log.error("ftpport:[" + ftpport + "]");
            System.out.println("ftpport:[" + ftpport + "]");
            log.error("ftpuser:[" + ftpuser + "]");
            System.out.println("ftpuser:[" + ftpuser + "]");
            log.error("ftppassword:[" + ftppassword + "]");
            System.out.println("ftppassword:[" + ftppassword + "]");
            tcphost = p.getProperty("tcp.server");
            tcpport = Integer.parseInt(p.getProperty("tcp.port").toString());
            tcpconnecttimeout = Integer.parseInt(p.getProperty("tcp.connect.timeout").toString());
            tcprecivetimeout = Integer.parseInt(p.getProperty("tcp.receive.timeout").toString());
            tcpothertimeout = Integer.parseInt(p.getProperty("tcp.other.timeout").toString());
            log.error("tcphost:[" + tcphost + "]");
            System.out.println("tcphost:[" + tcphost + "]");
            log.error("tcpport:[" + tcpport + "]");
            System.out.println("tcpport:[" + tcpport + "]");
            log.error("tcpconnecttimeout:[" + tcpconnecttimeout + "]");
            System.out.println("tcpconnecttimeout:[" + tcpconnecttimeout + "]");
            log.error("tcprecivetimeout:[" + tcprecivetimeout + "]");
            System.out.println("tcprecivetimeout:[" + tcprecivetimeout + "]");
            System.out.println("tcpothertimeout:[" + tcpothertimeout + "]");
        } catch (IOException e) {
            log.error("加载 FTP配置文件失败!" + e.getMessage());
        }
    }

    /**
     * register(签到交易)
     *
     * @param bp BankParameter对象;bank,corpcode,operator为必须传入的属性
     * @throws CheckException
     * @throws ReciveTimeOutException
     * @throws SendTimeOutException
     * @throws IOException
     * @throws UnknownHostException
     * @throws MsglengthException
     * @throws TransException
     * @throws
     * @since 1.0.0
     */
    public String register(BankParameter bp) throws CheckException, UnknownHostException, IOException, SendTimeOutException, ReciveTimeOutException, MsglengthException, TransException {
        TcpClient client = new TcpClient();
        client.setHost(tcphost);
        client.setPort(tcpport);
        client.setConnectTimeout(tcpconnecttimeout);
        client.setReceiveTimeout(tcprecivetimeout);

        if (bp.getBank() == null || bp.getCorpCode() == null || bp.getOperator() == null) {
            throw new TransException("92000", "签到交易：银行代码，操作员代码，单位代码不能为空");
        }
        String msg = Datautil.register + "|" + CheckUtil.checkBank(bp.getBank()) + "|" + CheckUtil.checkCorpcode(bp.getCorpCode()) + "|" + CheckUtil.checkOperator(bp.getOperator()) + CheckUtil.checkBank(bp.getBank()) + "|" + this.getSystemDateString() + "|" + this.getSystemTimeString();
        String returnMsg = client.sendMsg(msg);
        List msgList = DefaultDecode.decode(returnMsg);

        this.isMsgEnough(msgList.size(), 3);

        String tranretno = msgList.get(3).toString();
        this.errorcodeDcide(tranretno);
        return tranretno;
    }

    /**
     * unregister(签退交易)
     *
     * @param bp BankParameter对象;bank,corpcode,operator为必须传入的属性
     * @throws TransException
     * @throws CheckException
     * @throws TcpException
     * @throws MsglengthException void
     * @throws
     * @since 1.0.0
     */
    public String unregister(BankParameter bp) throws CheckException, UnknownHostException, IOException, SendTimeOutException, ReciveTimeOutException, MsglengthException, TransException {
        TcpClient client = new TcpClient();
        client.setHost(tcphost);
        client.setPort(tcpport);
        client.setConnectTimeout(tcpconnecttimeout);
        client.setReceiveTimeout(tcpothertimeout);
        if (bp.getBank() == null || bp.getCorpCode() == null || bp.getOperator() == null) {
            throw new TransException("93000", "签退交易：银行代码，操作员代码，单位代码不能为空");
        }
        String msg = Datautil.unregister + "|" + CheckUtil.checkBank(bp.getBank()) + "|" + CheckUtil.checkCorpcode(bp.getCorpCode()) + "|" + CheckUtil.checkOperator(bp.getOperator()) + CheckUtil.checkBank(bp.getBank()) + "|" + this.getSystemDateString() + "|" + this.getSystemTimeString();
        String returnMsg = client.sendMsg(msg);
        List msgList = DefaultDecode.decode(returnMsg);

        this.isMsgEnough(msgList.size(), 3);
        String tranretno = msgList.get(3).toString();
        this.errorcodeDcide(tranretno);
        return tranretno;
    }

    /**
     * queryClientBalance  经营户余额查询
     *
     * @param bcp BankClientQueryParameter对象;bank,corpcode,operator,clientcode,cardid,idnumber为必须传入的属性
     * @return 经营户余额
     * @throws TransException
     * @throws CheckException
     * @throws TcpException
     * @throws MsglengthException void
     * @throws
     * @since 1.0.0
     */
    public String queryClientBalance(
            BankClientQueryParameter bcp) throws CheckException, UnknownHostException, IOException, SendTimeOutException, ReciveTimeOutException, MsglengthException, TransException {

        TcpClient client = new TcpClient();
        client.setHost(tcphost);
        client.setPort(tcpport);
        client.setConnectTimeout(tcpconnecttimeout);
        client.setReceiveTimeout(tcprecivetimeout);
        if (bcp.getBank() == null || bcp.getCorpCode() == null || bcp.getOperator() == null || bcp.getClientCode() == null || bcp.getCardid() == null || bcp.getIdNumber() == null) {
            throw new TransException("11100", "经营户余额查询：银行代码，操作员代码，单位代码，客户代码，银行账号，身份证号不能为空，");
        }
        String msg = Datautil.queryClientBalance + "|" + CheckUtil.checkBank(bcp.getBank()) + "|" + CheckUtil.checkCorpcode(bcp.getCorpCode()) + "|" + CheckUtil.checkOperator(bcp.getOperator()) + CheckUtil.checkBank(bcp.getBank()) + "|" + this.getSystemDateString() + "|" + this.getSystemTimeString() + "|" +
                CheckUtil.checkClientcode(bcp.getClientCode()) + "|" + CheckUtil.checkCardid(bcp.getCardid()) + "|" + CheckUtil.checkIdnumber(bcp.getIdNumber());
        String returnMsg = client.sendMsg(msg);
        List msgList = DefaultDecode.decode(returnMsg);
        this.isMsgEnough(msgList.size(), 7);
        String tranretno = msgList.get(7).toString();
        this.errorcodeDcide(tranretno);
        String money = msgList.get(6).toString();

        return this.decodeMoney(money);
    }

    /**
     * commerceToTransferAccounts(经营户转账交易)
     *
     * @param bop BankOrderSalesParameter对象;bank,corpcode,operator,clientcode,cardid,idnumber,money,orderid,reversemark为必须传入的属性(money的单位为分，为大于等于0的正整数)
     * @return 转账交易结果   成功返回true   失败抛出异常
     * @throws TransException
     * @throws CheckException
     * @throws TcpException
     * @throws MsglengthException void
     * @throws
     * @since 1.0.0
     */
    public boolean commerceToTransferAccounts(
            BankOrderSalesParameter bop) throws CheckException, UnknownHostException, IOException, SendTimeOutException, ReciveTimeOutException, MsglengthException, TransException {

        TcpClient client = new TcpClient();
        client.setHost(tcphost);
        client.setPort(tcpport);
        client.setConnectTimeout(tcpconnecttimeout);
        client.setReceiveTimeout(tcprecivetimeout);
        if (bop.getBank() == null || bop.getCorpCode() == null || bop.getOperator() == null || bop.getClientCode() == null || bop.getCardid() == null || bop.getIdNumber() == null) {
            throw new TransException("12100", "经营户转账：银行代码，操作员代码，单位代码，客户代码，银行账号，身份证号不能为空，");
        }
        String money = bop.getMoney();
        BigDecimal a = new BigDecimal(money);
        BigDecimal b = new BigDecimal(100);
        money = a.abs().multiply(b).toString();
        money = money.substring(0, money.indexOf("."));
        String msg = Datautil.commerceToTransferAccounts + "|" + CheckUtil.checkBank(bop.getBank()) + "|" + CheckUtil.checkCorpcode(bop.getCorpCode()) + "|" + CheckUtil.checkOperator(bop.getOperator()) + CheckUtil.checkBank(bop.getBank()) + "|" + this.getSystemDateString() + "|" + this.getSystemTimeString() + "|" +
                CheckUtil.checkClientcode(bop.getClientCode()) + "|" + CheckUtil.checkCardid(bop.getCardid()) + "|" + CheckUtil.checkIdnumber(bop.getIdNumber()) + "|" + money + "|" + CheckUtil.checkOrderid(bop.getOrderid()) + "|01";
        String returnMsg = client.sendMsg(msg);
        List msgList = DefaultDecode.decode(returnMsg);
        this.isMsgEnough(msgList.size(), 9);
        String tranretno = msgList.get(8).toString();
        //this.errorcodeDcide(tranretno);
        if ("00".endsWith(tranretno)) {
            bop.setResultMsg(this.decodeErroecode("00"));
            return true;
        }
        bop.setResultMsg(this.decodeErroecode(tranretno));
        return false;
    }

    /**
     * queryCorpBalance(收入账户余额查询)
     *
     * @param bp BankParameter对象;bank,corpcode,operator为必须传入的属性
     * @return 返回收入账户余额
     * @throws TransException
     * @throws CheckException
     * @throws TcpException
     * @throws MsglengthException void
     * @throws
     * @since 1.0.0
     */
    public String queryCorpBalance(BankParameter bp) throws CheckException, UnknownHostException, IOException, SendTimeOutException, ReciveTimeOutException, MsglengthException, TransException {

        TcpClient client = new TcpClient();
        client.setHost(tcphost);
        client.setPort(tcpport);
        client.setConnectTimeout(tcpconnecttimeout);
        client.setReceiveTimeout(tcprecivetimeout);
        if (bp.getBank() == null || bp.getCorpCode() == null || bp.getOperator() == null) {
            throw new TransException("11101", "收入账户余额查询：银行代码，操作员代码，单位代码不能为空");
        }
        String msg = Datautil.queryCorpBalance + "|" + CheckUtil.checkBank(bp.getBank()) + "|" + CheckUtil.checkCorpcode(bp.getCorpCode()) + "|" + CheckUtil.checkOperator(bp.getOperator()) + CheckUtil.checkBank(bp.getBank()) + "|" + this.getSystemDateString() + "|" + this.getSystemTimeString();
        String returnMsg = client.sendMsg(msg);
        List msgList = DefaultDecode.decode(returnMsg);
        this.isMsgEnough(msgList.size(), 4);
        String tranretno = msgList.get(4).toString();
        this.errorcodeDcide(tranretno);
        String money = msgList.get(3).toString();

        return this.decodeMoney(money);
    }

    /**
     *
     * getBailAccountBal(保证金账户余额查询)
     * @return 返回保证金账户余额
     * @param bp   BankParameter对象;bank,corpcode,operator为必须传入的属性
     * @throws TransException
     * @throws CheckException
     * @throws TcpException
     * @throws MsglengthException
     *void
     * @exception
     * @since 1.0.0

    public String getBailAccountBal(BankParameter bp)  throws CheckException, UnknownHostException, IOException, SendTimeOutException, ReciveTimeOutException, MsglengthException, TransException {

    String msg = Datautil.getBailAccountBal+"|"+CheckUtil.checkBank(bp.getBank())+"|"+CheckUtil.checkCorpcode(bp.getCorpCode())+"|"+CheckUtil.checkOperator(bp.getOperator())+"|"+this.getSystemDateString()+"|"+this.getSystemTimeString();
    System.out.println(msg);
    String returnMsg = client.sendMsg(msg);
    List msgList = DefaultDecode.decode(returnMsg);
    this.isMsgEnough(msgList.size(),4);
    String tranretno = msgList.get(4).toString();
    this.errorcodeDcide(tranretno);

    return msgList.get(3).toString();
    }
     */


    /**
     * getTransResult(查询交易结果)
     *
     * @param bop BankOrderSalesParameter对象;bank,corpcode,operator,orderid为必须传入的属性
     * @return 返回指定流水号交易的交易返回码
     * @throws TransException
     * @throws CheckException
     * @throws TcpException
     * @throws MsglengthException void
     * @throws
     * @since 1.0.0
     */
    public String getTransResult(
            BankOrderSalesParameter bop) throws CheckException, UnknownHostException, IOException, SendTimeOutException, ReciveTimeOutException, MsglengthException, TransException {

        TcpClient client = new TcpClient();
        client.setHost(tcphost);
        client.setPort(tcpport);
        client.setConnectTimeout(tcpconnecttimeout);
        client.setReceiveTimeout(tcprecivetimeout);
        if (bop.getBank() == null || bop.getCorpCode() == null || bop.getOperator() == null) {
            throw new TransException("11103", "查询交易结果：银行代码，操作员代码，单位代码不能为空");
        }
        String msg = Datautil.getTransResult + "|" + CheckUtil.checkBank(bop.getBank()) + "|" + CheckUtil.checkCorpcode(bop.getCorpCode()) + "|" + CheckUtil.checkOperator(bop.getOperator()) + CheckUtil.checkBank(bop.getBank()) + "|" + this.getSystemDateString() + "|" + this.getSystemTimeString() + "|" + CheckUtil.checkOrderid(bop.getOrderid());
        String returnMsg = client.sendMsg(msg);
        List msgList = DefaultDecode.decode(returnMsg);
        this.isMsgEnough(msgList.size(), 5);
        String tranretno = msgList.get(4).toString();
        this.errorcodeDcide(tranretno);

        return msgList.get(5).toString();
    }

    /**
     *
     * avalilToCautionAccounts(公司转账)
     * @param  bop BankOrderSalesParameter对象;bank,corpcode,operator,money,orderid,reversemark为必须传入的属性(money的单位为分，为大于等于0的正整数)
     * @return 返回交易结果      成功返回true   失败抛出异常
     * @throws TransException
     * @throws CheckException
     * @throws TcpException
     * @throws MsglengthException
     *void
     * @exception
     * @since 1.0.0

    public boolean avalilToCautionAccounts(BankOrderSalesParameter  bop )  throws CheckException, UnknownHostException, IOException, SendTimeOutException, ReciveTimeOutException, MsglengthException, TransException {
    String msg = Datautil.avalilToCautionAccounts+"|"+CheckUtil.checkBank(bop.getBank())+"|"+CheckUtil.checkCorpcode(bop.getCorpCode())+"|"+CheckUtil.checkOperator(bop.getOperator())+"|"+this.getSystemDateString()+"|"+this.getSystemTimeString()+"|"+CheckUtil.checkMoney(bop.getMoney())+"|"+CheckUtil.checkOrderid(bop.getOrderid())+"|"+bop.getReversemark();
    System.out.println(msg);
    String returnMsg = client.sendMsg(msg);
    List msgList = DefaultDecode.decode(returnMsg);
    this.isMsgEnough(msgList.size(),6);
    String tranretno = msgList.get(5).toString();
    this.errorcodeDcide(tranretno);

    return true;
    }
     */


    /**
     * banlanceOfAccount(对账查询交易)
     * (这里描述这个方法适用条件 – 可选)
     *
     * @param bp BankParameter对象;bank,corpcode,operator为必须传入的属性
     * @throws TransException
     * @throws CheckException
     * @throws TcpException
     * @throws MsglengthException void
     * @throws
     * @since 1.0.0
     */
    public SalesResult banlanceOfAccount(BankParameter bp) throws CheckException, UnknownHostException, IOException, SendTimeOutException, ReciveTimeOutException, MsglengthException, TransException {

        TcpClient client = new TcpClient();
        client.setHost(tcphost);
        client.setPort(tcpport);
        client.setConnectTimeout(tcpconnecttimeout);
        client.setReceiveTimeout(tcpothertimeout);
        if (bp.getBank() == null || bp.getCorpCode() == null || bp.getOperator() == null) {
            throw new TransException("93100", "对账查询交易：银行代码，操作员代码，单位代码不能为空");
        }
        String msg = Datautil.banlanceOfAccount + "|" + CheckUtil.checkBank(bp.getBank()) + "|" + CheckUtil.checkCorpcode(bp.getCorpCode()) + "|" + CheckUtil.checkOperator(bp.getOperator()) + CheckUtil.checkBank(bp.getBank()) + "|" + this.getSystemDateString() + "|" + this.getSystemTimeString();
        String returnMsg = client.sendMsg(msg);
        List msgList = DefaultDecode.decode(returnMsg);
        this.isMsgEnough(msgList.size(), 7);
        String tranretno = msgList.get(3).toString();
        this.errorcodeDcide(tranretno);

        SalesResult sr = new SalesResult();
        String amount = "0";
        String amountofMoney = this.decodeMoney(msgList.get(5).toString());
        String businessAmount = "0";
        String businessAmountofMoney = this.decodeMoney(msgList.get(7).toString());
        ;
        try {
            amount = String.valueOf(Integer.parseInt(msgList.get(4).toString()));
            businessAmount = String.valueOf(Integer.parseInt(msgList.get(6).toString()));
        } catch (NumberFormatException e) {
            throw new TransException("NumberFormatException", "返回的金额出现异常");
        }
        sr.setAmount(amount);
        sr.setAmountOfMoney(amountofMoney);
        sr.setBusinessAmount(businessAmount);
        sr.setBusinessAmountofMoney(businessAmountofMoney);
        log.error("getAmount:[" + sr.getAmount() + "]");
        log.error("getAmountOfMoney:[" + sr.getAmountOfMoney() + "]");
        return sr;
    }

    /**
     * downloadCheckFile(对账下载文件交易，返回文件的路径)
     *
     * @param bp                                                           BankParameter对象;bank,corpcode,operator为必须传入的属性;
     * @param filedate参数若有值则为下载filedate指定日期(yyyyMMdd)的文件,若不传则默认下载当前日期的对账文件
     * @return String 返回文件的路径
     * @throws TransException
     * @throws CheckException
     * @throws TcpException
     * @throws MsglengthException void
     * @throws SysException
     * @throws
     * @since 1.0.0
     */
    public String downloadCheckFile(BankParameter bp, String filedate, String filepath) throws CheckException, UnknownHostException, IOException, SendTimeOutException, ReciveTimeOutException, MsglengthException, TransException, SysException {
        TcpClient client = new TcpClient();
        client.setHost(tcphost);
        client.setPort(tcpport);
        client.setConnectTimeout(tcpconnecttimeout);
        client.setReceiveTimeout(tcpothertimeout);

        FtpClient ftpclient = new FtpClient();
        ftpclient.setFtphost(ftphost);
        ftpclient.setFtpport(ftpport);
        ftpclient.setFtpuser(ftpuser);
        ftpclient.setFtppassword(ftppassword);


        if (bp.getBank() == null || bp.getCorpCode() == null || bp.getOperator() == null) {
            throw new TransException("93101", "对账下载文件交易：银行代码，操作员代码，单位代码不能为空");
        }
        if (filedate == null || "".equals(filedate)) {
            filedate = this.getSystemDateString();
        }
        String msg = Datautil.downloadCheckFile + "|" + CheckUtil.checkBank(bp.getBank()) + "|" + CheckUtil.checkCorpcode(bp.getCorpCode()) + "|" + CheckUtil.checkOperator(bp.getOperator()) + CheckUtil.checkBank(bp.getBank()) + "|" + this.getSystemDateString() + "|" + this.getSystemTimeString() + "|" + filedate;
        String filename = bp.getBank() + bp.getCorpCode() + filedate;
        String returnMsg = client.sendMsg(msg);
        List msgList = DefaultDecode.decode(returnMsg);
        this.isMsgEnough(msgList.size(), 4);
        String tranretno = msgList.get(3).toString();
        //this.errorcodeDcide(tranretno);

        if ("00".equals(tranretno)) {
            bp.setResultMsg(this.decodeErroecode("00"));
            return filename + "_ok";
        } else {
            bp.setResultMsg(this.decodeErroecode(tranretno));
            return "";
        }


    }


    public void downloadFromJS(BankParameter bp, String filedate, String filepath) throws TransException, SysException, IOException, CheckException {
        FileOutputStream out = null;
        FTPClient fc = null;
        FtpClient ftpclient = null;
        if (bp.getBank() == null || bp.getCorpCode() == null || bp.getOperator() == null) {
            throw new TransException("93101", "对账下载文件交易：银行代码，操作员代码，单位代码不能为空");
        }
        System.out.println("begin===========filepath:" + filepath + ";filedate:" + filedate);
        try {
            CheckUtil.isDir(filepath);

            ftpclient = new FtpClient();
            ftpclient.setFtphost(ftphost);
            ftpclient.setFtpport(ftpport);
            ftpclient.setFtpuser(ftpuser);
            ftpclient.setFtppassword(ftppassword);

            if (filedate == null || "".equals(filedate)) {
                filedate = this.getSystemDateString();
            }

            String filename = bp.getBank() + bp.getCorpCode() + filedate + "_ok";
            System.out.println("filename===============" + filename);
            fc = ftpclient.connectServer();

            String ftpPath = bp.getBank() + "/" + bp.getCorpCode() + "/" + filedate;
            fc.cwd(ftpPath);
            if (550 == fc.getReplyCode()) {
                log.error("文件不存在:" + filename);
                bp.setResultMsg(this.decodeErroecode("34"));
            }
            out = new FileOutputStream(new File(filepath + filename));
            System.out.println("filepath+filename==============" + filepath + filename);
            ftpclient.getFile(fc, filename, out);
            System.out.println("end==========" + fc.getReplyCode());

            bp.setResultMsg(this.decodeErroecode("00"));
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            bp.setResultMsg(this.decodeErroecode("40"));
            this.errorcodeDcide("40");
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (fc != null && ftpclient != null) {
                    ftpclient.releaseConnect(fc);
                }
            } catch (Exception e) {

            }
        }


    }

    /**
     *
     * returnBailAccount(返还保证金)
     * @param bp BankParameter对象;bank,corpcode,operator为必须传入的属性;
     * @param filedate参数若有值则传输filedate指定日期(yyyyMMdd)的文件,若不传值则默认传输当前日期的文件
     * @throws TransException
     * @throws CheckException
     * @throws TcpException
     * @throws MsglengthException
     *void
     * @throws SysException
     * @exception
     * @since 1.0.0

    public void returnBailAccount(BankParameter bp,String filedate,String filepath)throws CheckException, UnknownHostException, IOException, SendTimeOutException, ReciveTimeOutException, MsglengthException, TransException, SysException{
    if(filedate == null ||"".equals(filedate)){
    filedate = this.getSystemDateString();
    }

    String msg = Datautil.returnBailAccount+"|"+CheckUtil.checkBank(bp.getBank())+"|"+CheckUtil.checkCorpcode(bp.getCorpCode())+"|"+CheckUtil.checkOperator(bp.getOperator())+"|"+this.getSystemDateString()+"|"+this.getSystemTimeString()+"|"+filedate;


    String filename = bp.getBank()+"BZJ"+bp.getCorpCode()+filedate;
    FTPClient fc = ftpclient.connectServer();
    fc.cwd(ftpclient.getFtppath());

    boolean uploadresult = ftpclient.uploadFile(fc, filename, new FileInputStream(filepath+filename));

    log.info("replyCode:["+fc.getReplyCode()+"];replyMessage:["+fc.getReplyString()+"]");

    ftpclient.releaseConnect(fc);

    System.out.println(msg);
    String returnMsg = client.sendMsg(msg);
    List msgList = DefaultDecode.decode(returnMsg);
    this.isMsgEnough(msgList.size(),4);
    String tranretno = msgList.get(3).toString();
    this.errorcodeDcide(tranretno);

    }
     */
    /**
     * isMsgEnough(判断返回报文长度是否符合要求)
     *
     * @param size   报文解析后list集合的大小
     * @param enough 要求的大小
     * @throws TransException
     * @throws CheckException
     * @throws TcpException
     * @throws MsglengthException void
     * @throws
     * @since 1.0.0
     */
    private static void isMsgEnough(int size, int enough) throws MsglengthException {
        if ((size - 1) < enough) {
            throw new MsglengthException("返回的报文长度不对");
        }
    }

    /**
     * errorcodeDcide(对返回的报文中的返回码进行处理)
     *
     * @param str String类型
     * @throws TransException
     * @throws CheckException
     * @throws TcpException
     * @throws MsglengthException void
     * @throws
     * @since 1.0.0
     */
    private static void errorcodeDcide(String str) throws TransException {
        if (str.endsWith("00")) {
            log.info("交易成功");
        } else {
            if ("".equals(str)) {
                log.error("银行返回的返回码为空");
                throw new TransException(str, "银行返回的返回码为空");
            }
            String errormsg = errorCodeMap.get(str);
            if (errormsg == null) {
                errormsg = "现有的交易返回码中无此交易返回码:[" + str + "]";
                log.error("交易不成功，现有的交易返回码中无此返回码：[" + str + "]");
                throw new TransException(str, "错误信息为：" + str);
            } else {
                log.error("交易不成功，失败原因为：" + errorCodeMap.get(str));
                throw new TransException(str, "错误信息为：" + errormsg);
            }
        }
    }

    private static String decodeErroecode(String str) throws TransException {
        if ("".equals(str)) {
            log.error("银行返回的返回码为空");
            throw new TransException(str, "银行返回的返回码为空");
        }
        String errormsg = errorCodeMap.get(str);
        String resultStr = "";
        if (errormsg == null) {
            errormsg = "现有的交易返回码中无此交易返回码:[" + str + "]";
            log.error("交易不成功，现有的交易返回码中无此返回码：[" + str + "]");
            throw new TransException(str, "错误信息为：" + str);
        } else {
            resultStr = str + "_" + errormsg;
        }
        return resultStr;


    }

    /**
     * 获取系统当前日期
     *
     * @return 系统当前日期
     */
    private static String getSystemDateString() {
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(today);
    }

    /**
     * 获取系统当前时间
     *
     * @return 系统当前日期
     */
    private static String getSystemTimeString() {
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("HHmmss");
        return format.format(today);
    }

    private static String decodeMoney(String money) throws TransException {
        String transResult = "0";
        try {
            BigDecimal a = new BigDecimal(money);
            BigDecimal b = new BigDecimal(100);
            transResult = a.divide(b).toString();
        } catch (NumberFormatException e) {
            throw new TransException("NumberFormatException", "返回的金额出现异常");
        }
        return transResult;

    }

}

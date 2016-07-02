package com.venusource.transfer.client.util;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.venusource.transfer.client.exception.CheckException;


public class CheckUtil {
    private static Log log = LogFactory.getLog(CheckUtil.class);

    private static int corpcodelength = 10;
    private static int operatorlength = 10;
    private static int banklength = 2;
    private static int clientcodelength = 14;
    private static int cardidlength = 30;
    private static int idnumberlength = 20;
    private static int orderidlength = 13;
    private static int moneylength = 16;


    public static StringBuffer checkCorpcode(String str) throws CheckException {
        int i;
        if ((i = corpcodelength - str.length()) < 0) {
            log.error("单位代码长度超过" + corpcodelength + "位！");
            throw new CheckException("单位代码长度超过" + corpcodelength + "位！");
        } else {
            return paddingStr(str, i);
        }

    }

    public static StringBuffer checkOperator(String str) throws CheckException {
        int i;
        if ((i = operatorlength - str.length()) < 0) {
            log.error("操作员代码长度超过" + operatorlength + "位！");
            throw new CheckException("操作员代码长度超过" + operatorlength + "位！");
        } else {
            return new StringBuffer(str);
        }
    }

    public static StringBuffer checkBank(String str) throws CheckException {
        int i;
        if ((i = banklength - str.length()) < 0) {
            log.error("银行名称长度超过" + banklength + "位！");
            throw new CheckException("银行名称长度超过" + banklength + "位！");
        } else {
            return paddingStr(str, i);
        }
    }

    public static StringBuffer checkClientcode(String str) throws CheckException {
        int i;
        if ((i = clientcodelength - str.length()) < 0) {
            log.error("客户代码长度超过" + clientcodelength + "位！");
            throw new CheckException("客户代码长度超过" + clientcodelength + "位！");
        } else {
            return paddingStr(str, i);
        }
    }

    public static StringBuffer checkCardid(String str) throws CheckException {
        int i;
        if ((i = cardidlength - str.length()) < 0) {
            log.error("银行卡号长度超过" + cardidlength + "位！");
            throw new CheckException("银行卡号长度超过" + cardidlength + "位！");
        } else {
            return paddingStr(str, i);
        }
    }

    public static StringBuffer checkIdnumber(String str) throws CheckException {
        int i;
        if ((i = idnumberlength - str.length()) < 0) {
            log.error("身份证号长度超过" + idnumberlength + "位！");
            throw new CheckException("身份证号长度超过" + idnumberlength + "位！");
        } else {
            return paddingStr(str, i);
        }
    }

    public static StringBuffer checkOrderid(String str) throws CheckException {
        int i;
        String s = "";
        if ((i = orderidlength - str.length()) < 0) {
            log.error("交易流水号长度超过" + orderidlength + "位！");
            throw new CheckException("交易流水号长度超过" + orderidlength + "位！");
        } else {
            for (int j = 0; j < (orderidlength - str.length()); j++) {
                s = " " + s;
            }
            return new StringBuffer(s + str);
        }
    }


    public static StringBuffer checkMoney(float money) throws CheckException {
        int i;
        String s = "";
        int m = (int) money;
        String f = m + "";
        if (f.matches("^\\d+$")) {
            if ((i = moneylength - f.length()) < 0) {
                log.error("金额长度超过" + moneylength + "位！");
                throw new CheckException("金额长度超过" + moneylength + "位！");
            } else {
                for (int j = 0; j < moneylength - f.length(); j++) {
                    s = "0" + s;
                }
                return new StringBuffer(s + f);
            }
        } else {
            log.error("输入的金额格式不对");
            throw new CheckException("金额格式不对");
        }

    }

    private static StringBuffer paddingStr(String paramString, int paramInt) {
        StringBuffer param = new StringBuffer(paramString);
        for (int i = 0; i < paramInt; ++i) {
            param.append(" ");
        }
        return param;
    }

    public static boolean isDir(String filepath) throws CheckException {
        File file = new File(filepath);
        if (!file.exists()) {
            throw new CheckException("传入的路径：[" + filepath + "],不存在");
        }
        if (!file.isDirectory()) {
            throw new CheckException("传入的路径：[" + filepath + "],不是一个目录");
        }
        return true;
    }
}

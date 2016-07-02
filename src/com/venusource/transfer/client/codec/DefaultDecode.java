package com.venusource.transfer.client.codec;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名称：DefaultDecode
 * 类描述  对返回的报文进行解析
 * 创建人：hjt
 * 修改人：hjt
 * 修改时间：2013-8-21 下午3:05:25
 * 修改时间：
 *
 * @version 1.0.0
 */
public class DefaultDecode {
    /**
     * decode(对报文进行解析，返回一个list集合)
     * (这里描述这个方法适用条件 – 可选)
     *
     * @param str 字符串类型
     * @return List
     * @throws
     * @since 1.0.0
     */
    public static List decode(String str) {
        String[] a = str.split("\\|", -2);
        List list = new ArrayList();
        for (int i = 0; i < a.length; i++) {
            list.add(a[i]);
        }
        return list;
    }
}

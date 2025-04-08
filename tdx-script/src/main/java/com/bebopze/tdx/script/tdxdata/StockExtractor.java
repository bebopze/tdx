package com.bebopze.tdx.script.tdxdata;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;


/**
 * 解析通达信盘后数据 获取历史日线数据   -   https://blog.csdn.net/weixin_57522153/article/details/119992838
 */
public class StockExtractor {


    public static void main(String[] args) {
        try {

            // C:\soft\通达信\v_2024\跑数据专用\new_tdx\vipdoc\sh\lday\sh000001.day
            // C:\soft\通达信\v_2024\跑数据专用\new_tdx\vipdoc\ds\lday\31#00700.day
            // C:\soft\通达信\v_2024\跑数据专用\new_tdx\vipdoc\ds\lday\74#SPY.day


            String filePath__a = "C:\\soft\\通达信\\v_2024\\跑数据专用\\new_tdx\\vipdoc\\sh\\lday\\sh000001.day";
            String filePath_hk = "C:\\soft\\通达信\\v_2024\\跑数据专用\\new_tdx\\vipdoc\\ds\\lday\\31#00700.day";
            String filePath_us = "C:\\soft\\通达信\\v_2024\\跑数据专用\\new_tdx\\vipdoc\\ds\\lday\\74#SPY.day";


            List<String[]> stockDataList = exactStock(filePath_hk);
            for (String[] stockData : stockDataList) {
                System.out.println(String.join(", ", stockData));
            }


            System.out.println("---------------------------------- code：" + stockDataList.get(0)[0] + "     总数：" + stockDataList.size());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * tdx 盘后数据（xx.day）   -   解析器
     *
     * @param filePath 文件路径     -    \new_tdx\vipdoc\
     * @return
     * @throws IOException
     */
    public static List<String[]> exactStock(String filePath) throws IOException {

        // 股票代码
        String code = parseCode(filePath);


        FileInputStream fileInputStream = new FileInputStream(filePath.trim());
        byte[] buffer = new byte[fileInputStream.available()];
        fileInputStream.read(buffer);
        fileInputStream.close();

        int num = buffer.length;
        int no = num / 32;
        int b = 0, e = 32;


        List<String[]> items = new ArrayList<>();
        float preClose = 0.0f;


        for (int i = 0; i < no; i++) {
            byte[] slice = new byte[32];
            System.arraycopy(buffer, b, slice, 0, 32);


            // ---------------------------------------------------------------------------------------------------------


            // 在大多数公开资料和业内实践中，通达信个股日线数据的记录一般采用如下结构（每条记录共32字节，每个字段均按小端模式存储）：

            // 日期（int，4字节）
            // 存储格式为形如 YYYYMMDD 的整数。例如 20250408 表示 2025 年 4 月 8 日。


            // 开盘价（float，4字节）
            // 最高价（float，4字节）
            // 最低价（float，4字节）
            // 收盘价（float，4字节）
            //
            // 上述价格通常为浮点数（单精度），表示当日的价格。注意：在不同版本中可能存在“倍率”或“缩放因子”的情况，解析时需要结合实际的数值单位检查。


            // 成交额（float，4字节）
            // 表示当天的成交金额


            // 成交量（int，4字节）
            // 表示当天的成交股数（A港：手[x100]   /   美：股）


            // 保留字段/备用字段（int，4字节）
            // 用于预留或对一些扩展信息存放。部分版本中可能用于存放其他数据（如持仓量等），但多数情况下该字段为保留字段。


            // 这样，总计 4 + 4×4 + 4 + 4 + 4 = 32 字节


            // ---------------------------------------------------------------------------------------------------------


            ByteBuffer byteBuffer = ByteBuffer.wrap(slice).order(ByteOrder.LITTLE_ENDIAN);


            // 日期
            int date = byteBuffer.getInt();
            // 开盘价
            float open = byteBuffer.getFloat();
            // 最高价
            float high = byteBuffer.getFloat();
            // 最低价
            float low = byteBuffer.getFloat();
            // 收盘价
            float close = byteBuffer.getFloat();
            // 成交额（万）
            float amount = byteBuffer.getFloat() / 10000;
            // 成交量
            int vol = byteBuffer.getInt();
            // 保留字段
            int unUsed = byteBuffer.getInt();


            int year = date / 10000;
            int month = (date % 10000) / 100;
            int day = date % 100;
            String dd = String.format("%04d-%02d-%02d", year, month, day);


            if (i == 0) {
                preClose = close;
            }

            float ratio = Math.round((close - preClose) / preClose * 100 * 100.0f) / 100.0f;
            preClose = close;

            String[] item = {code, dd, String.format("%.2f", open), String.format("%.2f", high), String.format("%.2f", low), String.format("%.2f", close), String.format("%.2f", ratio), String.valueOf(amount), String.valueOf(vol)};
            items.add(item);


            b += 32;
            e += 32;
        }

        return items;
    }


    /**
     * 解析   股票代码
     *
     * @param filePath 文件路径
     * @return 股票代码
     */
    private static String parseCode(String filePath) {
        return filePath.split("lday")[1].split(".day")[0].split("sh|sz|bj|#")[1];
    }

}
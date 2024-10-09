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

            // \\Mac\Home\Desktop\win桌面
            // C:\soft\通达信\中信证券\zd_zxzq_gm\vipdoc\sh\lday\sh000001.day

            List<String[]> stockData = exactStock("C:\\soft\\通达信\\中信证券\\zd_zxzq_gm\\vipdoc\\sh\\lday\\sh000001.day", "000001");
            for (String[] data : stockData) {
                System.out.println(String.join(", ", data));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * tdx 盘后数据（xx.day）   -   解析器
     *
     * @param fileName 文件路径     -    \new_tdx\vipdoc\
     * @param code     股票代码
     * @return
     * @throws IOException
     */
    public static List<String[]> exactStock(String fileName, String code) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(fileName);
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

            ByteBuffer byteBuffer = ByteBuffer.wrap(slice).order(ByteOrder.LITTLE_ENDIAN);
            int date = byteBuffer.getInt();
            int openPrice = byteBuffer.getInt();
            int high = byteBuffer.getInt();
            int low = byteBuffer.getInt();
            int close = byteBuffer.getInt();
            float amount = byteBuffer.getFloat();
            int vol = byteBuffer.getInt();
            int unused = byteBuffer.getInt();

            int year = date / 10000;
            int month = (date % 10000) / 100;
            int day = date % 100;
            String dd = String.format("%04d-%02d-%02d", year, month, day);

            float open = openPrice / 100.0f;
            float h = high / 100.0f;
            float l = low / 100.0f;
            float c = close / 100.0f;

            if (i == 0) {
                preClose = c;
            }

            float ratio = Math.round((c - preClose) / preClose * 100 * 100.0f) / 100.0f;
            preClose = c;

            String[] item = {code, dd, String.format("%.2f", open), String.format("%.2f", h), String.format("%.2f", l), String.format("%.2f", c), String.format("%.2f", ratio), String.valueOf(amount), String.valueOf(vol)};
            items.add(item);

            b += 32;
            e += 32;
        }

        return items;
    }


}
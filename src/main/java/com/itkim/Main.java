package com.itkim;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itkim.mail.SendEmail2;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * @Description: 爬取代练通苹果微信的王者荣耀最新的订单(只爬了一页 ， 可以修改代码 ， 爬取多页)
 * @author: Vic
 * @date: 2019-08-10 下午6:59
 */
public class Main {
    public static String BaseUrl = "https://server.dailiantong.com/API/AppService.ashx?";

    //订单列表
    public static String Action = "LevelOrderList";

    public static String callback = "callback";

    public static String IsPub = "7";

    public static String GameID = "107";

    public static String ZoneID = "1033";

    public static String ServerID = "0";

    public static String SearchStr = "";

    public static String Sort_Str = "";

    public static String PageIndex = "1";

    public static String PageSize = "20";

    public static String Price_Str = "";

    public static String PubCancel = "0";

    public static String SettleHour = "0";

    public static String FilterType = "0";

    public static String PGType = "2";

    public static String UserID = "0";

    //时间戳
    public static String TimeStamp = "";

    public static String Ver = "1.0";

    public static String AppVer = "2.0.0";

    public static String AppOS = "webapp";

    public static String AppID = "webapp";

    //签名
    public static String Sign = "";

    public static HashMap<String, String> hashMap = new HashMap();

    /**
     * 构造请求的url
     *
     * @return url
     */
    public static String getUrl() {
        //请求的URL
        String url = BaseUrl + "Action=" + Action + "&callback=" + callback + "&IsPub=" + IsPub
                + "&GameID=" + GameID + "&ZoneID=" + ZoneID + "&ServerID=" + ServerID
                + "&SearchStr=" + SearchStr + "&Sort_Str=" + Sort_Str + "&PageIndex=" + PageIndex
                + "&PageSize=" + PageSize + "&Price_Str=" + Price_Str + "&PubCancel=" + PubCancel
                + "&SettleHour=" + SettleHour + "&FilterType=" + FilterType + "&PGType=" + PGType
                + "&UserID=" + UserID + "&TimeStamp=" + TimeStamp + "&Ver=" + Ver + "&AppVer=" + AppVer
                + "&AppOS=webapp&AppID=webapp&Sign=" + Sign;
        return url;
    }


    /**
     * 得到加密的签名
     *
     * @return 加密的签名
     */
    public static String enCode() {
        TimeStamp = (System.currentTimeMillis() + "").substring(0, 10);
        String code = "9c7b9399680658d308691f2acad58c0a" + Action + IsPub + GameID + ZoneID
                + ServerID + SearchStr + Sort_Str + PageIndex + PageSize + Price_Str
                + PubCancel + SettleHour + FilterType + PGType + UserID + TimeStamp
                + Ver + AppVer + AppOS + AppID;

        //得到加密签名
        return DigestUtils.md5Hex(code);
    }

    /**
     * 解析数据
     *
     * @param jsonStr
     */
    public static void jsonToEntity(String jsonStr, Boolean isFirst) {
        JSONObject arr = JSONArray.parseObject(jsonStr.substring(9, jsonStr.length() - 1));
        JSONArray LevelOrderList = (JSONArray) arr.get("LevelOrderList");

        //订单数
        int recordCount = (int) arr.get("RecordCount");
        int flag = 0;

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println(dateFormat.format(date) + " 订单数：" + recordCount);

        ArrayList<Order> entity = new ArrayList();
        if (recordCount > 0) {
            for (int i = 0; i < LevelOrderList.size(); i++) {
                JSONObject order = JSONObject.parseObject(LevelOrderList.get(i).toString());
                Order order1 = new Order();
                order1.setSerialNo(order.getString("SerialNo"));
//            order1.setStamp(numberDateFormatToDate(time, "yyyy-mm-dd  HH:mm:ss"));
                order1.setServer(order.getString("Server"));
                order1.setZone(order.getString("Zone"));
                order1.setIsPub((Integer) order.get("IsPub"));
                order1.setTitle(order.getString("Title"));
                order1.setZoneServerID(order.getString("ZoneServerID"));
                order1.setGame(order.getString("Game"));
                order1.setPrice(order.getIntValue("Price"));
                order1.setCreate(order.getString("Create"));
                order1.setEnsure2((BigDecimal) order.get("Ensure2"));
                order1.setEnsure((BigDecimal) order.get("Ensure"));
                order1.setEnsure1((BigDecimal) order.get("Ensure1"));
                order1.setSameCity(order.getString("SameCity"));
                order1.setTimeLimit((Integer) order.get("TimeLimit"));

                if (!hashMap.containsKey(order1.getSerialNo())) {
                    flag = 1;
                    hashMap.put(order1.getSerialNo(), "");
                    entity.add(order1);
                }
            }

            if (flag == 1 && !isFirst) {
                try {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("订单数：" + recordCount + "<br><br>");
                    for (int i = 0; i < entity.size(); i++) {
                        Order order = entity.get(i);
                        stringBuilder.append(order.toString() + "<br><br>");
                    }

                    SendEmail2.sendEmail(stringBuilder.toString());
                } catch (Exception e) {
                    System.out.println("邮箱出错");
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {

        //先进行一次预处理，不发邮件
        Sign = enCode();
        try {
            String jsonStr = HttpClient.get(getUrl());
            if (!jsonStr.equals("callback({\"Result\":\"-102\",\"Err\":\"签名错误.\"})")) {
                try {
                    jsonToEntity(jsonStr, true);
                } catch (Exception e) {
                    e.printStackTrace();
                    Thread.sleep(10000);
                }
            } else {
                Thread.sleep(20000);
            }
        } catch (Exception e) {
            Thread.sleep(10000);
        }

        Thread.sleep(10000);

        for (; ; ) {
            Sign = enCode();
            try {
                String jsonStr = HttpClient.get(getUrl());
                if (!jsonStr.equals("callback({\"Result\":\"-102\",\"Err\":\"签名错误.\"})")) {
                    try {
                        jsonToEntity(jsonStr, false);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Thread.sleep(10000);
                    }
                } else {
                    Thread.sleep(20000);
                }
            } catch (Exception e) {
                Thread.sleep(10000);
            }

            Thread.sleep(10000);
        }
    }
}
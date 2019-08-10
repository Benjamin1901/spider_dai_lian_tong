package com.itkim;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @Description: 爬取代练通苹果微信的王者荣耀最新的订单(只爬了一页，可以修改代码，爬取多页)
 * @author: Vic
 * @date: 2019-08-10 下午6:59
 */
public class Main {
    public static String BaseUrl = "https://server.dailiantong.com/API/AppService.ashx?";

    //订单列表
    public static String Action = "LevelOrderList";

    public static String callback = "callback";

    public static String IsPub = "1";

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

    /**
     * 构造请求的url
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
     * @return
     */
    public static String enCode(){
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
     * @param jsonStr
     */
    public static void jsonToEntity(String jsonStr) {
        JSONObject arr = JSONArray.parseObject(jsonStr.substring(9, jsonStr.length() - 1));
        JSONArray LevelOrderList = (JSONArray) arr.get("LevelOrderList");

        //订单数
        int recordCount = (int) arr.get("RecordCount");
        System.out.println("订单数：" + recordCount);
        for (int i = 0; i < LevelOrderList.size(); i++) {
            JSONObject order = JSONObject.parseObject(LevelOrderList.get(i).toString());
            Order order1 = new Order();
            order1.setSerialNo(order.getString("SerialNo"));
            order1.setStamp(order.getLong("Stamp"));
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

            System.out.println(order1.toString());
            System.out.println("");
        }
    }


    public static void main(String[] args) throws IOException {
        Sign = enCode();
        String jsonStr = HttpClient.get(getUrl());
        jsonToEntity(jsonStr);
    }
}
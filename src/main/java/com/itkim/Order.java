package com.itkim;

import java.math.BigDecimal;

/**
 * @Description: TODO
 * @author: Vic
 * @date: 2019-08-10 下午8:27
 */
public class Order {
    private String Create;

    private BigDecimal Ensure;

    private BigDecimal Ensure1;

    private BigDecimal Ensure2;

    private String Game;

    private int IsPub;

    private int Price;

    private String SameCity;

    private String SerialNo;

    private String Server;

    private long Stamp;

    private int TimeLimit;

    private String Title;

    private String Zone;

    private String ZoneServerID;

    public String getCreate() {
        return Create;
    }

    public void setCreate(String create) {
        Create = create;
    }

    public BigDecimal getEnsure() {
        return Ensure;
    }

    public void setEnsure(BigDecimal ensure) {
        Ensure = ensure;
    }

    public BigDecimal getEnsure1() {
        return Ensure1;
    }

    public void setEnsure1(BigDecimal ensure1) {
        Ensure1 = ensure1;
    }

    public BigDecimal getEnsure2() {
        return Ensure2;
    }

    public void setEnsure2(BigDecimal ensure2) {
        Ensure2 = ensure2;
    }

    public String getGame() {
        return Game;
    }

    public void setGame(String game) {
        Game = game;
    }

    public int getIsPub() {
        return IsPub;
    }

    public void setIsPub(int isPub) {
        IsPub = isPub;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getSameCity() {
        return SameCity;
    }

    public void setSameCity(String sameCity) {
        SameCity = sameCity;
    }

    public String getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(String serialNo) {
        SerialNo = serialNo;
    }

    public String getServer() {
        return Server;
    }

    public void setServer(String server) {
        Server = server;
    }

    public long getStamp() {
        return Stamp;
    }

    public void setStamp(long stamp) {
        Stamp = stamp;
    }

    public int getTimeLimit() {
        return TimeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        TimeLimit = timeLimit;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getZone() {
        return Zone;
    }

    public void setZone(String zone) {
        Zone = zone;
    }

    public String getZoneServerID() {
        return ZoneServerID;
    }

    public void setZoneServerID(String zoneServerID) {
        ZoneServerID = zoneServerID;
    }

    @Override
    public String toString() {
        return "Order{" +
                "Create='" + Create + '\'' +
                ", 总共的保证金=" + Ensure +
                ", 安全保证金=" + Ensure1 +
                ", 效率保证金=" + Ensure2 +
                ", 游戏='" + Game + '\'' +
                ", IsPub=" + IsPub +
                ", 价格=" + Price +
                ", SameCity='" + SameCity + '\'' +
                ", SerialNo='" + SerialNo + '\'' +
                ", 服='" + Server + '\'' +
                ", Stamp=" + Stamp +
                ", 代练时间限制(小时)=" + TimeLimit +
                ", 标题='" + Title + '\'' +
                ", 区='" + Zone + '\'' +
                ", ZoneServerID='" + ZoneServerID + '\'' +
                '}';
    }
}
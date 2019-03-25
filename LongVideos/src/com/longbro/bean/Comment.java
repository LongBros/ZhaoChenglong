package com.longbro.bean;
public class Comment {
    private Integer cId;

    private String cVideo;

    private String cUser;

    private String cContent;

    private Integer cZnum;

    private Integer cCnum;

    private String cIp;

    private String cAddress;

    private String cTime;

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    public String getcVideo() {
        return cVideo;
    }

    public void setcVideo(String cVideo) {
        this.cVideo = cVideo == null ? null : cVideo.trim();
    }

    public String getcUser() {
        return cUser;
    }

    public void setcUser(String cUser) {
        this.cUser = cUser == null ? null : cUser.trim();
    }

    public String getcContent() {
        return cContent;
    }

    public void setcContent(String cContent) {
        this.cContent = cContent == null ? null : cContent.trim();
    }

    public Integer getcZnum() {
        return cZnum;
    }

    public void setcZnum(Integer cZnum) {
        this.cZnum = cZnum;
    }

    public Integer getcCnum() {
        return cCnum;
    }

    public void setcCnum(Integer cCnum) {
        this.cCnum = cCnum;
    }

    public String getcIp() {
        return cIp;
    }

    public void setcIp(String cIp) {
        this.cIp = cIp == null ? null : cIp.trim();
    }

    public String getcAddress() {
        return cAddress;
    }

    public void setcAddress(String cAddress) {
        this.cAddress = cAddress == null ? null : cAddress.trim();
    }

    public String getcTime() {
        return cTime;
    }

    public void setcTime(String cTime) {
        this.cTime = cTime == null ? null : cTime.trim();
    }
}
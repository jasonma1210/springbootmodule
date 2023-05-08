package com.example.dto;

import java.io.Serializable;
import java.util.Date;

public class TNote implements Serializable {
    /**
    * 日志id
    */
    private Integer id;

    /**
    * 日志标题
    */
    private String noteTitle;

    /**
    * 日志天气
    */
    private String noteWeather;

    /**
    * 日志编写的时候所在地
    */
    private String noteArea;

    /**
    * 日志编写的时候的心情
    */
    private String noteMood;

    /**
    * 日志的内容
    */
    private String noteContent;

    /**
    * 用户id
    */
    private Integer uId;

    /**
    * 是否删除
    */
    private Byte isDelete;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteWeather() {
        return noteWeather;
    }

    public void setNoteWeather(String noteWeather) {
        this.noteWeather = noteWeather;
    }

    public String getNoteArea() {
        return noteArea;
    }

    public void setNoteArea(String noteArea) {
        this.noteArea = noteArea;
    }

    public String getNoteMood() {
        return noteMood;
    }

    public void setNoteMood(String noteMood) {
        this.noteMood = noteMood;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", noteTitle=").append(noteTitle);
        sb.append(", noteWeather=").append(noteWeather);
        sb.append(", noteArea=").append(noteArea);
        sb.append(", noteMood=").append(noteMood);
        sb.append(", noteContent=").append(noteContent);
        sb.append(", uId=").append(uId);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}
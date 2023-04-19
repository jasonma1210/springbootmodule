package com.example.vo;

public class ResultVO {
    private String resCode;
    private String resMsg;
    private Object resData;

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public Object getResData() {
        return resData;
    }

    public void setResData(Object resData) {
        this.resData = resData;
    }

    public ResultVO(String resCode, String resMsg, Object resData) {
        this.resCode = resCode;
        this.resMsg = resMsg;
        this.resData = resData;
    }

    public ResultVO(String resCode, String resMsg) {
        this.resCode = resCode;
        this.resMsg = resMsg;
    }

    public static ResultVO success(String resCode, String resMsg, Object resData){
        return new ResultVO( resCode,  resMsg,  resData);
    }

    public static ResultVO success(String resCode, String resMsg){
        return new ResultVO( resCode,  resMsg);
    }

    public static ResultVO failure(String resCode, String resMsg){
        return new ResultVO( resCode,  resMsg);
    }
    public static ResultVO failure(String resCode, String resMsg, Object resData){
        return new ResultVO( resCode,  resMsg,resData);
    }
}


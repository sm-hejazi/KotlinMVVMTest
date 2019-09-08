package com.ppp_smh.initlibrary.entity.ErrorStatus;

import com.google.gson.annotations.SerializedName;

import static com.ppp_smh.initlibrary.entity.ErrorStatus.ErrorStatus.NOT_DEFINED;


/**
 * Created by m.hejazi on 5/7/18.
 */

public class ErrorModel {
    private String path;
    @SerializedName("Description")
    private String message;
    @SerializedName("ErrorCode")
    private int code;
    private String status;

//    public ErrorModel(String status) {
//        this("", "", -1, status);
//    }

    public ErrorModel() {
        this("", "", -1, NOT_DEFINED);
    }

    public ErrorModel(String message) {
        this("", message, -1, NOT_DEFINED);
    }

    public ErrorModel(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private ErrorModel(String path, String message, int code, String status) {
        this.path = path;
        this.message = message;
        this.code = code;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

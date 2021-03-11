package com.ho.blogt.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Response<T> implements Serializable {
    private static final long serialVersionUID = -4505655308965878999L;

    //请求成功返回码为：0
    private static final String successCode = "0";
    //返回数据
    private T Data;
    //返回码
    private String ResultCode;
    //返回描述
    private String ErrorMsg;
    public Response(){
        this.ResultCode = successCode;
        this.ErrorMsg = "请求成功";
    }

    public Response(String code, String msg){
        this();
        this.ResultCode = code;
        this.ErrorMsg = msg;
    }
    public Response(String code, String msg, T data){
        this();
        this.ResultCode = code;
        this.ErrorMsg = msg;
        this.Data = data;
    }
    public Response(T data){
        this();
        this.Data = data;
    }
}

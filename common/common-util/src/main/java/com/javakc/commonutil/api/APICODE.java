package com.javakc.commonutil.api;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一数据结果返回
 * @author leesiyu
 * @version V1.0
 * @Copyright © 北京汇才同飞教育科技公司
 */
@Data
public class APICODE {

    private Integer code;

    private String message;

    private Boolean success;

    private Map<String, Object> data = new HashMap<String, Object>();

    private APICODE(){

    }

    public static APICODE OK(){
        APICODE apicode = new APICODE();
        apicode.setCode(ResultCode.SUCCESS);
        apicode.setMessage("调用成功");
        apicode.setSuccess(true);
        return apicode;
    }

    public static APICODE ERROR(){
        APICODE apicode = new APICODE();
        apicode.setCode(ResultCode.ERROR);
        apicode.setMessage("服务不可用");
        apicode.setSuccess(false);
        return apicode;
    }

    public APICODE code(Integer code){
        this.setCode(code);
        return this;
    }

    public APICODE message(String message){
        this.setMessage(message);
        return this;
    }

    public APICODE success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public APICODE data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public APICODE data(Map<String, Object> map){
        this.setData(map);
        return this;
    }

}
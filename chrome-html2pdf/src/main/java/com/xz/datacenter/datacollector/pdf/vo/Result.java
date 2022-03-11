package com.xz.datacenter.datacollector.pdf.vo;

import lombok.Data;

/**
 * @Description:
 * @Author: houyong
 * @Date: 2020/3/9
 */
@Data
public class Result {
    boolean success;
    String message;
    String code;

    public static Result sucess(String code,String msg){
        Result r=new Result();
        r.setSuccess(true);
        r.setCode(code);
        r.setMessage(msg);
        return r;
    }

    public static Result failure(String code,String msg){
        Result r=new Result();
        r.setSuccess(false);
        r.setCode(code);
        r.setMessage(msg);
        return r;
    }
}

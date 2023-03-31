package com.javaweb.hhjrp.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公共响应类
 * @param <T>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer code;
    private String massage;
    private T data;

    // 成功
    public static <T> Result<T> success(){
        return new Result<>(20000,"success",null);
    }
    public static <T> Result<T> success(T data){
        return new Result<>(20000,"success",data);
    }
    public static <T> Result<T> success(T data,String massage){
        return new Result<>(20000,massage,data);
    }
    public static <T> Result<T> success(String massage){
        return new Result<>(20000,massage,null);
    }
    // 失败
    public static<T>  Result<T> fail(){
        return new Result<>(20001,"fail",null);
    }

    public static<T>  Result<T> fail(Integer code){
        return new Result<>(code,"fail",null);
    }

    public static<T>  Result<T> fail(Integer code, String message){
        return new Result<>(code,message,null);
    }

    public static<T>  Result<T> fail( String message){
        return new Result<>(20001,message,null);
    }
}

package com.tulip.springbootelasticsearchdemo.util;

import com.tulip.springbootelasticsearchdemo.domain.Person;
import com.tulip.springbootelasticsearchdemo.domain.Result;

public class ResultUtil {

    public static Result<Object> success(Object object){
        Result<Object> result = new Result<>();
        result.setCode(1);
        result.setMessage("success");
        result.setData(object);
        return result;
    }

    public static Result<Object> succes(){
        return success(null);
    }

    public static Result<Object> error(){
        Result<Object> result = new Result<>();
        result.setCode(2);
        result.setMessage("error");
        return result;
    }



}

package com.tulip.springbootelasticsearchdemo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result<T> {

    private Integer code;

    private String message;

    private T data;



}

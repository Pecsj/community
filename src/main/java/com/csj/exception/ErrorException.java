package com.csj.exception;

public class ErrorException extends Exception {
    private String msg;

    public ErrorException(String msg){
        this.msg = msg;
    }

    public String getMsg(){
        return msg;
    }
}

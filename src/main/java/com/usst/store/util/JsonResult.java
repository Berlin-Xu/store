package com.usst.store.util;

import java.io.Serializable;

/**
 * json格式数据进行响应
 */
public class JsonResult<E> implements Serializable {

    // 响应状态码
    private Integer state;

    // 描述信息
    private String message;

    // 数据
    private E data;

    public JsonResult() {
    }

    public JsonResult(Integer state) {
        this.state = state;
    }

    public JsonResult(Integer state, E data) {
        this.state = state;
        this.data = data;
    }

    public JsonResult(Throwable e) {
        this.message = e.getMessage();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
}

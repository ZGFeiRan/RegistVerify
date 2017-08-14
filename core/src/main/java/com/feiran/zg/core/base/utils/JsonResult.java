package com.feiran.zg.core.base.utils;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Member;

/**
 * Created by zhangguangfei on 2017/1/3.
 */
@Setter@Getter
public class JsonResult {
    private boolean success = true;
    private String msg;

    public JsonResult(boolean success,String msg) {
        super();
        this.msg = msg;
        this.success = success;
    }

    public JsonResult(String msg) {
        super();
        this.msg = msg;
    }

    public JsonResult() {
        super();
    }
}

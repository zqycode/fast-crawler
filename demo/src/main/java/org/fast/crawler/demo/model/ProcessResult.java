package org.fast.crawler.demo.model;

/**
 * Created by xp017734 on 10/10/15.
 */
public class ProcessResult {

    private int code = 1;

    private Object data;

    public ProcessResult() {}

    public ProcessResult(Object data) {
        this.data = data;
    }

    public ProcessResult(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

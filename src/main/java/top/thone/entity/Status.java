package top.thone.entity;

import com.alibaba.fastjson.JSON;
import top.thone.config.JsonColumnFilter;

/**
 * @Author thone
 * @Description //TODO
 * @Date 5:31 PM-2019/8/31
 **/
public class Status {
    private Integer status;
    private Object data;
    public Object notice;
    private Object msg;

    public Status(){

    }

    public Status(int status, Object data, Object notice, Object msg) {
        this.status = status;
        this.data = data;
        this.notice = notice;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public Status setStatus(int status) {
        this.status = status;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Status setData(Object data) {
        this.data = data;
        return this;
    }

    public Object getNotice() {
        return notice;
    }

    public Status setNotice(Object notice) {
        this.notice = notice;
        return this;
    }

    public Object getMsg() {
        return msg;
    }

    public Status setMsg(Object msg) {
        this.msg = msg;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public Status notice(String notice) {
        this.setNotice(notice);
        return this;
    }
}

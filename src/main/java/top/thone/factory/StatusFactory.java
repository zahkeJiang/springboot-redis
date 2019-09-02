package top.thone.factory;

import top.thone.entity.Status;

import java.io.Serializable;

public enum StatusFactory {

    S_0_OK(0, "成功"),
    C_1404_CLIENT_REQUEST_DATA_ERROR(1404, "请求的资源不存在");

    private int status;
    private Serializable msg;

    StatusFactory(int status, Serializable msg) {
        this.status = status;
        this.msg = msg;
    }

    public Status report(Object data) {
        return new Status(status, data, msg, msg);
    }

    public Status notice(Object notice) {
        return new Status(status, null, notice, msg);
    }

}

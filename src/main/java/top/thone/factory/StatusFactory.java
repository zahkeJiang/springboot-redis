package top.thone.factory;

import top.thone.entity.Status;

import java.io.Serializable;

/**
 * 返回消息实体类生成工厂
 * 调用方式： return StatusFactory.S_0_OK.report(DataObject).notice("notice message");
 */
public enum StatusFactory {

    S_0_OK(0, "成功"),
    S_2000_SERVER_DATA_ERROR(2000, "网络异常"),
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

    public Status error(Object msg) {
        return new Status(status, null, this.msg, msg);
    }

}

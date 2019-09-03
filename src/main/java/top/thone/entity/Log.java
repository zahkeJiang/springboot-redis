package top.thone.entity;

import lombok.Data;

/**
 * @Author thone
 * @Description //TODO
 * @Date 3:30 PM-2019/9/3
 **/
@Data
public class Log {
    private String ip;

    private String url;

    private String method;

    private Long timestamp;

    private String response;

    private String session;
}

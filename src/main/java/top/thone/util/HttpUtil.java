package top.thone.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author thone
 * @Description //TODO
 * @Date 3:11 PM-2019/9/3
 **/
public class HttpUtil {

    /**
     * 获取用户的IP地址
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || !ip.contains(".")) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || !ip.contains(".")) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || !ip.contains(".")) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || !ip.contains(".")) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || !ip.contains(".")) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || !ip.contains(".")) {
            ip = request.getRemoteHost();
        }
        System.out.println(ip);
        return ip;
    }
}

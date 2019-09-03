package top.thone.util;

import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @Author thone
 * @Description code 工具类
 * @Date 5:08 PM-2019/9/2
 **/
public class CodeUtil {

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 将十进制转换为36进制
     * @param imIdentify
     * @return
     */
    public static final Long decode36C(String imIdentify) {
        Long result = Long.valueOf(imIdentify, 36);
        return result == null ? 0L : result;
    }

    /**
     * 将36进制转换为十进制
     * @param userId
     * @return
     */
    public static final String encode36C(Long userId) {
        return Long.toString(userId, 36);
    }

    /**
     * 目标字符串是不是数字
     * @param str 目标字符串
     * @return
     */
    public static boolean isInteger(String str) {
        if(str==null || str.equals("")){
            return false;
        }
        return Pattern.compile("^[-\\+]?[\\d]*$").matcher(str).matches();
    }
}

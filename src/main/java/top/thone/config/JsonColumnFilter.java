package top.thone.config;

import com.alibaba.fastjson.serializer.PropertyFilter;

/**
 * @Author thone
 * @Description 格式化返回的json数据
 * @Date 11:59 AM-2019/9/2
 **/
public class JsonColumnFilter implements PropertyFilter {
    @Override
    public boolean apply(Object o, String key, Object value) {
        return value != null; //如果字段值为null不返回此字段。
    }
}

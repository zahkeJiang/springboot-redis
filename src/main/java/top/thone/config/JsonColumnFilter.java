package top.thone.config;

import com.alibaba.fastjson.serializer.PropertyFilter;

/**
 * @Author thone
 * @Description //TODO
 * @Date 11:59 AM-2019/9/2
 **/
public class JsonColumnFilter implements PropertyFilter {
    @Override
    public boolean apply(Object o, String key, Object value) {
        return value != null;
    }
}

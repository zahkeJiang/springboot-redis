package top.thone.config;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author thone
 * @Description //TODO
 * @Date 12:28 PM-2019/9/2
 **/
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    /**
     * FastJson配置
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(fastJsonHttpMessageConverters());
    }

    @Bean
    public HttpMessageConverter fastJsonHttpMessageConverters() {
        //1. 需要定义一个converter转换消息的对象
        FastJsonHttpMessageConverter fasHttpMessageConverter = new FastJsonHttpMessageConverter();
        //2. 添加fastjson的配置信息，比如:是否需要格式化返回的json的数据
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializeFilters(new JsonColumnFilter());
        //3. 处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fasHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        //4. 在converter中添加配置信息
        fasHttpMessageConverter.setFastJsonConfig(fastJsonConfig);

        return fasHttpMessageConverter;
    }
}

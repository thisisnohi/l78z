package nohi.demo.web.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-04-16 12:58
 **/
@ComponentScan("nohi.demo")
@Configuration
@EnableWebMvc
public class AppConfig implements WebMvcConfigurer {
//    @Override
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//
//    }


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new FastJsonHttpMessageConverter());
    }
}

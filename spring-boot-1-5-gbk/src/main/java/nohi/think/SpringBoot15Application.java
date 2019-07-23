package nohi.think;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class SpringBoot15Application extends WebMvcConfigurerAdapter {

  @Bean
  public HttpMessageConverter<String> responseBodyConverter() {
	StringHttpMessageConverter converter = new StringHttpMessageConverter( Charset.forName( "GBK" ) );
	return converter;
  }

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
	super.configureMessageConverters( converters );
	converters.add( responseBodyConverter() );
  }

  @Override
  public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
	configurer.favorPathExtension( false );
  }
//  @Override
//  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//	super.configureMessageConverters( converters );
//	FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//	FastJsonConfig fastJsonConfig = new FastJsonConfig();
//	fastJsonConfig.setSerializerFeatures( SerializerFeature.PrettyFormat);
//	fastJsonConfig.setCharset( Charset.forName( "GBK" ) );
//	fastConverter.setFastJsonConfig(fastJsonConfig);
//	converters.add(fastConverter);
//  }
//
//  @Bean
//  public FilterRegistrationBean filterRegistrationBean() {
//	FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//	CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//	characterEncodingFilter.setForceEncoding(true);
//	characterEncodingFilter.setEncoding("GBK");
//	registrationBean.setFilter(characterEncodingFilter);
//	return registrationBean;
//  }

  public static void main(String[] args) {
	SpringApplication.run( SpringBoot15Application.class, args );
  }
}

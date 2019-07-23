package nohi.think.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * Created by nohi on 2018/5/16.
 */
public class XssFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		//打印请求Url
		System.out.println("this is MyFilter,url :" + request.getRequestURI());
		String value = getBody( request );
		System.out.println("value1:" + value);
		request = (HttpServletRequest) getRequest(request,value);
		value = getBody( request );
		System.out.println("value2:" + value);
		filterChain.doFilter(request, servletResponse);
	}

	private String getBody(HttpServletRequest request) throws IOException {
		String body = null;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}
		} catch (IOException ex) {
			throw ex;
		}finally {
			if(null != bufferedReader){
				//bufferedReader.close();
			}
		}
		body = stringBuilder.toString();
		return body;
	}
	private ServletRequest getRequest(ServletRequest request ,String body){
		String enctype = request.getContentType();
		System.out.println("enctype:" + enctype);
		if (StringUtils.isNotEmpty(enctype) && enctype.contains("application/json")){
			return new PostServletRequest((HttpServletRequest) request,body);
		}
		return request;
	}
}

package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
/**
 * 1.用过滤器方法解决乱码问题 需在web.xml中配置此过滤器
 * 2.Servlet过滤器可以当做一个只需要在web.xml文件中配置就可以灵活运用，可以重用的模块化组件。它能够对jsp，HTML，servlet文件进行过滤
 * @author Administrator
 *
 */
public class EncodingFilter implements Filter{
    @Override
    public void init(FilterConfig config) throws ServletException {
    	// TODO Auto-generated method stub
    	
    }
    @Override
    public void destroy() {
    	// TODO Auto-generated method stub
    	
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
    		FilterChain chain) throws IOException, ServletException {
    	// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");//
		
		response.setCharacterEncoding("UTF-8");//设置浏览器响应的编码方式，即控制浏览器的编码
		response.setContentType("text/html;charset=UTF-8");//
		chain.doFilter(request, response);

    }

}

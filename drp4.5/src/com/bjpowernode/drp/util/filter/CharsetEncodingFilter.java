package com.bjpowernode.drp.util.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
/**
 * 采用filter统一处理字符集
 *
 * @author cnwl
 *
 */
public class CharsetEncodingFilter implements Filter {

	private String endcoding;
	
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//设置字符集
		request.setCharacterEncoding(endcoding);
		//继续执行
		chain.doFilter(request, response);
		
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.endcoding = filterConfig.getInitParameter("encoding");
	}

}

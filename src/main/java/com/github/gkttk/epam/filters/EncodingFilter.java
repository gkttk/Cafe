package com.github.gkttk.epam.filters;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {

    private String encoding;
    private final static String ENCODING_INIT_PARAM = "encoding";
    private final static String DEFAULT_ENCODING = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) {
        this.encoding = filterConfig.getInitParameter(ENCODING_INIT_PARAM);
        if (this.encoding == null) {
            this.encoding = DEFAULT_ENCODING;
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(encoding);
        filterChain.doFilter(servletRequest, servletResponse);
        servletResponse.setCharacterEncoding(encoding);

    }

    @Override
    public void destroy() {}
}

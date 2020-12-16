package com.github.gkttk.epam.filters;

import com.github.gkttk.epam.model.entities.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class BlockFilter implements Filter {

    private final static String AUTH_USER_ATTR = "authUser";
    private final static String START_PAGE = "index.jsp";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpServletRequest.getSession();
        User authUser = (User) session.getAttribute(AUTH_USER_ATTR);

        if (authUser != null && !authUser.isActive()) {
            session.invalidate();
            httpServletRequest.setAttribute("blockedUserMessage", "Current user is blocked"); //todo resourceBundle
            httpServletRequest.getRequestDispatcher(START_PAGE).forward(servletRequest, servletResponse);
        }

        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {

    }
}

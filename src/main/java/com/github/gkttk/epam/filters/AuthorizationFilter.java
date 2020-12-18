package com.github.gkttk.epam.filters;

import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.enums.UserRole;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class AuthorizationFilter implements Filter {

    private final static String AUTH_USER_ATTRIBUTE = "authUser";
    private final static String COMMAND_PARAMETER = "command";
    private final static String START_PAGE = "index.jsp";
    private final static String CURRENT_PAGE_PARAMETER = "currentPage";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    private boolean isAuthenticated(UserRole role, String currentCommand) {
        List<String> availableCommandNames = role.getAvailableCommandNames();
        return availableCommandNames.stream()
                .anyMatch(commandName -> commandName.equals(currentCommand));
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();

        String command = request.getParameter(COMMAND_PARAMETER);

        User authUser = (User) session.getAttribute(AUTH_USER_ATTRIBUTE);

        boolean isAuthenticated;
        UserRole role = UserRole.GUEST;

        if (authUser != null) {
            role = authUser.getRole();
        }

        isAuthenticated = isAuthenticated(role, command);

        if (isAuthenticated) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            redirect(servletRequest, servletResponse);
        }
    }


    private void redirect(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String currentPage = (String)request.getSession().getAttribute(CURRENT_PAGE_PARAMETER);
        if (currentPage != null){
            request.getRequestDispatcher(currentPage).forward(servletRequest, servletResponse);
        }else {
            request.getRequestDispatcher(START_PAGE).forward(servletRequest, servletResponse);
        }
    }
}

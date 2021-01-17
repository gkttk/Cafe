package com.github.gkttk.epam.filters;

import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.enums.UserRole;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class AuthorizationFilter implements Filter {

    private final static String AUTH_USER_ATTR = "authUser";
    private final static String COMMAND_PARAM = "command";
    private final static String CURRENT_PAGE_PARAM = "currentPage";
    private final static String START_PAGE = "index.jsp";

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();

        String command = request.getParameter(COMMAND_PARAM);

        UserRole role = UserRole.GUEST;
        User authUser = (User) session.getAttribute(AUTH_USER_ATTR);
        if (authUser != null) {
            role = authUser.getRole();
        }

        boolean isAuthenticated;
        isAuthenticated = isAuthenticated(role, command);

        if (!isAuthenticated) {
            forward(servletRequest, servletResponse);

        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private boolean isAuthenticated(UserRole role, String currentCommand) {
        List<String> availableCommandNames = role.getAvailableCommandNames();
        return availableCommandNames.stream()
                .anyMatch(commandName -> commandName.equals(currentCommand));
    }

    private void forward(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String currentPage = (String) request.getSession().getAttribute(CURRENT_PAGE_PARAM);
        if (currentPage != null) {
            request.getRequestDispatcher(currentPage).forward(servletRequest, servletResponse);
        } else {
            request.getRequestDispatcher(START_PAGE).forward(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }
}

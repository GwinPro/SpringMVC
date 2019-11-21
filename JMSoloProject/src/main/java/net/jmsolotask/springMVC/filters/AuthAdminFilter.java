package net.jmsolotask.springMVC.filters;

import net.jmsolotask.springMVC.controller.UserController;
import net.jmsolotask.springMVC.model.User;
import net.jmsolotask.springMVC.service.UserService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.util.Objects.nonNull;

@WebFilter(urlPatterns = {"/admin", "/delete", "/update", "/userAdd", "/user"})
public class AuthAdminFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;
        final HttpSession session = req.getSession();

        User user = (User) session.getAttribute("getUser");

        String path = req.getRequestURI().substring(req.getContextPath().length());

        if (nonNull(session) &&
                nonNull(session.getAttribute("getUser"))) {
            if (user.getRole().equals("admin")) {
                filterChain.doFilter(request, response);
            } else if (path.contains("user")) {
                filterChain.doFilter(request, response);
            } else {
                //UserController userController=new UserController();
                //serController.noRigts();
                req.getRequestDispatcher("WEB-INF/view/noRights.jsp").forward(req, res);
            }
        } else {
            //UserController userController=new UserController();
            //userController.noRigts();
            req.getRequestDispatcher("WEB-INF/view/noRights.jsp").forward(req, res);
        }
    }

    @Override
    public void destroy() {

    }
}

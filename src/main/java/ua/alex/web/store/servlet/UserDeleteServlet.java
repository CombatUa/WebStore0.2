package ua.alex.web.store.servlet;

import ua.alex.web.store.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class UserDeleteServlet extends HttpServlet {
//    http://localhost:8080/users/delete?id=4
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String paramId = req.getParameter("id");
        userService.delete(Long.valueOf(paramId));
        resp.sendRedirect("/users");
//        resp.setContentType("text/html;charset=utf-8");
//        resp.setStatus(HttpServletResponse.SC_OK);
    }

}



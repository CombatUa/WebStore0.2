package ua.alex.web.store.servlet;


import ua.alex.web.store.entity.User;
import ua.alex.web.store.service.UserService;
import ua.alex.web.store.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsersServlet extends HttpServlet {
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        List<User> all = userService.getAll();
//        resp.getWriter().println(all);
        Map<String, Object> pageVariables = createPageVariablesMap();

        resp.getWriter().println(PageGenerator.instance().getPage("index.html", pageVariables));

        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private  Map<String, Object> createPageVariablesMap() {

        Map<String, Object> pageVariables = new HashMap<>();
        List<User> userList = userService.getAll();
        pageVariables.put("userList", userList);

        return pageVariables;
    }
}

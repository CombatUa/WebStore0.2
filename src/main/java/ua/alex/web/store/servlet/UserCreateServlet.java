package ua.alex.web.store.servlet;

import ua.alex.web.store.entity.User;
import ua.alex.web.store.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class UserCreateServlet extends HttpServlet {
//    localhost:8080/users/create?id=44&first_name=fnm&last_name=lnm&salary=900&dob=2011-07-07
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String paramId = req.getParameter("id");
        String first_name = req.getParameter("first_name");
        String last_name = req.getParameter("last_name");
        String salary = req.getParameter("salary");
        String dob = req.getParameter("dob");
        User user = new User(Long.valueOf(paramId), first_name, last_name, Double.valueOf(salary), LocalDate.parse(dob, DateTimeFormatter.ISO_DATE));
        userService.create(user);
        resp.sendRedirect("/users");
//        resp.setContentType("text/html;charset=utf-8");
//        resp.setStatus(HttpServletResponse.SC_OK);
    }

}



package ua.alex.web.store.starter;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ua.alex.web.store.dao.UserDao;
import ua.alex.web.store.dao.ojdbc.UserDaoImpl;
import ua.alex.web.store.service.UserService;
import ua.alex.web.store.servlet.UserCreateServlet;
import ua.alex.web.store.servlet.UserDeleteServlet;
import ua.alex.web.store.servlet.UserUpdateServlet;
import ua.alex.web.store.servlet.UsersServlet;

public class Starter {
    public static void main(String[] args) throws Exception {
        UserDao userDao = new UserDaoImpl();
        UserService userService = new UserService();
        userService.setUserDao(userDao);
        UsersServlet userServlet = new UsersServlet();
        userServlet.setUserService(userService);
        UserDeleteServlet userDeleteServlet = new UserDeleteServlet();
        userDeleteServlet.setUserService(userService);
        UserCreateServlet userCreateServlet = new UserCreateServlet();
        userCreateServlet.setUserService(userService);
        UserUpdateServlet userUpdateServlet = new UserUpdateServlet();
        userUpdateServlet.setUserService(userService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setResourceBase("webapp");
        context.addServlet(new ServletHolder(userServlet), "/users");
        context.addServlet(new ServletHolder(userDeleteServlet), "/users/delete");
        context.addServlet(new ServletHolder(userCreateServlet), "/users/create");
        context.addServlet(new ServletHolder(userUpdateServlet), "/users/update");
        DefaultServlet defaultServlet = new DefaultServlet();

        context.addServlet(new ServletHolder(defaultServlet), "/*");


        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
    }
}

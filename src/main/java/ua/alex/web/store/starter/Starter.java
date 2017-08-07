package ua.alex.web.store.starter;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ua.alex.web.store.dao.UserDao;
import ua.alex.web.store.dao.jdbc.UserDaoImpl;
import ua.alex.web.store.service.UserService;
import ua.alex.web.store.servlet.*;

import java.net.URI;
import java.net.URL;

public class Starter {
    public static void main(String[] args) throws Exception {
        UserDao userDao = new UserDaoImpl();
        UserService userService = new UserService();
        userService.setUserDao(userDao);
        UsersServlet usersServlet = new UsersServlet();
        usersServlet.setUserService(userService);

        UserServlet userServlet = new UserServlet();
        userServlet.setUserService(userService);

        UserDeleteServlet userDeleteServlet = new UserDeleteServlet();
        userDeleteServlet.setUserService(userService);
        UserCreateServlet userCreateServlet = new UserCreateServlet();
        userCreateServlet.setUserService(userService);
        UserUpdateServlet userUpdateServlet = new UserUpdateServlet();
        userUpdateServlet.setUserService(userService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        ClassLoader classLoader = Starter.class.getClassLoader();
        URL f = classLoader.getResource("webapp");
        if (f == null)
        {
            throw new RuntimeException("Unable to find resource directory");
        }

        System.err.println("WebRoot is " + f.getPath());

        context.setResourceBase(f.getPath());
        context.addServlet(new ServletHolder(usersServlet), "/users");
        context.addServlet(new ServletHolder(userServlet), "/user/*");
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

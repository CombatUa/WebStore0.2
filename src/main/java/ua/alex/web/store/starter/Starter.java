package ua.alex.web.store.starter;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ua.alex.web.store.dao.UserDao;
import ua.alex.web.store.dao.ojdbc.UserDaoImpl;
import ua.alex.web.store.service.UserService;
import ua.alex.web.store.servlet.UserServlet;

public class Starter {
    public static void main(String[] args) throws Exception {
        UserDao userDao = new UserDaoImpl();
        UserService userService = new UserService();
        userService.setUserDao(userDao);
        UserServlet userServlet = new UserServlet();
        userServlet.setUserService(userService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setResourceBase("webapp");
        context.addServlet(new ServletHolder(userServlet), "/users");
        DefaultServlet defaultServlet = new DefaultServlet();

        context.addServlet(new ServletHolder(defaultServlet), "/*");


        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
    }
}

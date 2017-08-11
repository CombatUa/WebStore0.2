package ua.alex.web.store.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ua.alex.web.store.entity.User;
import ua.alex.web.store.json.GsonByteArrayHelper;
import ua.alex.web.store.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;


public class UserServlet extends HttpServlet {
    private static final Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(byte[].class,
            new GsonByteArrayHelper()).create(); //new Gson();
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);

        Long id = getIdFromURI(req.getRequestURI());

        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader reader = req.getReader();
        while (reader.ready()) {
            stringBuffer.append(reader.readLine());
        }
        System.out.println(stringBuffer);
        User user = gson.fromJson(stringBuffer.toString(), User.class);
        user.setId(id);
        boolean result = false;

        if (id != null) {
            result = userService.update(user.getId(), user);
        }
        writeResponse(resp, result, user);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);

        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader reader = req.getReader();
        while (reader.ready()) {
            stringBuffer.append(reader.readLine());
        }
        User user = gson.fromJson(stringBuffer.toString(), User.class);
        Long id = userService.create(user);

        boolean result = false;

        if (id != null) {
            result = true;
            user.setId(id);
        }
        writeResponse(resp, result, user);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        Long id = getIdFromURI(req.getRequestURI());
        boolean result = false;
        User user = null;
        if (id != null) {
            result = true;
            user = userService.getEntityByKey(id);
        }
        writeResponse(resp, result, user);

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        Long id = getIdFromURI(req.getRequestURI());
        boolean result = false;

        if (id != null) {
            result = userService.delete(id);
        }
        writeResponse(resp, result, null);

    }

    private <E> void writeResponse(HttpServletResponse resp, boolean result, E entity) throws IOException {
        Respose respose = new Respose();
        if (result) {
            respose.setStatus("" + HttpServletResponse.SC_OK);
            respose.setMessage("OK");
            if (entity != null) {
                resp.getWriter().print(gson.toJson(entity));
            } else {
                resp.getWriter().print(gson.toJson(respose));
            }
        } else {
            respose.setStatus("" + HttpServletResponse.SC_NOT_MODIFIED);
            respose.setMessage("NOT_MODIFIED");
            resp.getWriter().print(gson.toJson(respose));
        }
    }

    private Long getIdFromURI(String requestURI) {
        Long aLong = null;
        try {
            aLong = Long.valueOf(requestURI.substring(requestURI.lastIndexOf("/") + 1));
        } catch (NumberFormatException e) {
            //not good request
        }
        return aLong;
    }
}

package ua.alex.web.store.servlet;

import com.google.gson.Gson;
import ua.alex.web.store.entity.User;
import ua.alex.web.store.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;


public class UserServlet extends HttpServlet {
    private final Gson gson = new Gson();
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String paramId = req.getParameter("id");
//        String first_name = req.getParameter("first_name");
//        String last_name = req.getParameter("last_name");
//        String salary = req.getParameter("salary");
//        String dob = req.getParameter("dob");
//        User user = new User(Long.valueOf(paramId), first_name, last_name, Double.valueOf(salary), LocalDate.parse(dob, DateTimeFormatter.ISO_DATE));
//        userService.update(Long.valueOf(paramId), user);
////        resp.sendRedirect("/users");
//        resp.setContentType("text/html;charset=utf-8");
//        resp.setStatus(HttpServletResponse.SC_OK);

        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        Respose respose = new Respose();

        Long id = getIdFromURI(req.getRequestURI());

        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader reader = req.getReader();
        while (reader.ready()) {
            stringBuffer.append(reader.readLine());
        }
        System.out.println(stringBuffer);
        Gson gson = new Gson();
        User user = gson.fromJson(stringBuffer.toString(), User.class);
        user.setId(id);
        boolean result = false;

        if (id != null) {
            result = userService.update(user.getId(), user);
        }
        writeResponse(resp, result, user);

//        if (result) {
//            respose.setStatus("" + HttpServletResponse.SC_OK);
//            respose.setMessage("OK");
//        } else if (id == null) {
//            respose.setStatus("" + HttpServletResponse.SC_BAD_REQUEST);
//            respose.setMessage("BAD_REQUEST");
//        } else {
//            respose.setStatus("" + HttpServletResponse.SC_NOT_MODIFIED);
//            respose.setMessage("NOT_MODIFIED");
//
//        }
//        respose.setStatus("" + HttpServletResponse.SC_OK);
//        respose.setMessage("OK");
//        resp.getWriter().print(gson.toJson(respose));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        Respose respose = new Respose();

        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader reader = req.getReader();
        while (reader.ready()) {
            stringBuffer.append(reader.readLine());
        }
        System.out.println(stringBuffer);
        Gson gson = new Gson();
        User user = gson.fromJson(stringBuffer.toString(), User.class);
        Long id = userService.create(user);
        user.setId(id);

        respose.setStatus("" + HttpServletResponse.SC_OK);
        respose.setMessage("OK");

        resp.getWriter().print(gson.toJson(user));
        resp.getWriter().print(gson.toJson(respose));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        Respose respose = new Respose();
        Gson gson = new Gson();
        Long id = getIdFromURI(req.getRequestURI());
        boolean result = false;
        User user = null;
        if (id != null) {
            user = userService.getEntityByKey(id);
        }
        writeResponse(resp, result, user);
//        if (user != null) {
//            respose.setStatus("" + HttpServletResponse.SC_OK);
//            respose.setMessage("OK");
//            resp.getWriter().print(gson.toJson(user));
//        } else if (id == null) {
//            respose.setStatus("" + HttpServletResponse.SC_BAD_REQUEST);
//            respose.setMessage("BAD_REQUEST");
//        } else {
//            respose.setStatus("" + HttpServletResponse.SC_NOT_MODIFIED);
//            respose.setMessage("NOT_MODIFIED");
//        }
//        resp.getWriter().print(gson.toJson(respose));

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        Respose respose = new Respose();
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
            }
//        } else if (id == null) {
//            respose.setStatus("" + HttpServletResponse.SC_BAD_REQUEST);
//            respose.setMessage("BAD_REQUEST");
//
//
//
        } else {
            respose.setStatus("" + HttpServletResponse.SC_NOT_MODIFIED);
            respose.setMessage("NOT_MODIFIED");

        }
        resp.getWriter().print(gson.toJson(respose));
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

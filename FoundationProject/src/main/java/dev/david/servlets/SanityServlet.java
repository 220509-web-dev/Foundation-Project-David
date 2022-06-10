package dev.david.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class SanityServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("[LOG] - Sanity Servlet received a request at " + LocalDateTime.now());
        System.out.println("[LOG] - Request URI " + req.getRequestURI());
        System.out.println("[LOG] - Request method " + req.getMethod());
        System.out.println("[LOG] - Request header, example: " + req.getHeader("example"));

        resp.setStatus(200); //default
        resp.setHeader("Content-type", "text/plain");
        resp.setHeader("example-response-header", "some-example-value");
        resp.getWriter().write("This is the response payload YES");

        //super.doGet(req, resp);
    }


}
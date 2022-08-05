package com.example.demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/get-attribute")
public class GetAtributeExample extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String appMessage = (String) req.getServletContext().getAttribute("message");
        String sessionMessage = (String) req.getSession().getAttribute("message");
        String requestMessage = (String) req.getAttribute("message");
        resp.setContentType("text/html");

        PrintWriter printWriter = resp.getWriter();
        printWriter.write(appMessage);
        printWriter.write(sessionMessage);
        printWriter.write(requestMessage);
    }
}

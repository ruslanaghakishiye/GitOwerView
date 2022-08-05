package com.example.demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "setAttribute", value = "/set-attribute")
public class SetAtributeExample extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getServletContext().setAttribute("message", "Aplication scope");

        HttpSession session = req.getSession();
        session.setAttribute("message", "Session scope");

        req.setAttribute("mesage","Request scope");

        req.getRequestDispatcher("get-attribute").forward(req,resp);

    }
}

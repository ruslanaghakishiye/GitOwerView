package com.example.demo.registration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "register", value = "/register")
public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        PrintWriter out = resp.getWriter();
//        out.print("Working");
        String uname = req.getParameter("name");
        String uemail = req.getParameter("email");
        String upwd = req.getParameter("pass");
        String Reupwd = req.getParameter("re_pass");
        String umobile = req.getParameter("contact");

        RequestDispatcher dispatcher = null;
        Connection connection = null;
//
//        PrintWriter out = resp.getWriter();
//        out.print(uname);
//        out.print(uemail);
//        out.print(upwd);
//        out.print(umobile);
        if (uname==null|| uname.equals("")){
            req.setAttribute("status","invalidName");
            dispatcher = req.getRequestDispatcher("registration.jsp");
            dispatcher.forward(req,resp);
        }
        if (uemail==null|| uemail.equals("")){
            req.setAttribute("status","invalidEmail");
            dispatcher = req.getRequestDispatcher("registration.jsp");
            dispatcher.forward(req,resp);
        }
        if (upwd==null|| upwd.equals("")){
            req.setAttribute("status","invalidPassword");
            dispatcher = req.getRequestDispatcher("registration.jsp");
            dispatcher.forward(req,resp);
        }else if (!upwd.equals(Reupwd)){
            req.setAttribute("status","invalidConfirmPassword");
            dispatcher = req.getRequestDispatcher("registration.jsp");
            dispatcher.forward(req,resp);
        }
        if (umobile==null|| umobile.equals("")){
            req.setAttribute("status","invalidPhone");
            dispatcher = req.getRequestDispatcher("registration.jsp");
            dispatcher.forward(req,resp);
        }
        else if (umobile.length()>10){
            req.setAttribute("status","invalidPhoneLenght");
            dispatcher = req.getRequestDispatcher("registration.jsp");
            dispatcher.forward(req,resp);
        }
        try {
            String driverUrl = "jdbc:mysql://localhost:3306/youtube?useSSL=false";
            String username = "root";
            String password = "admin";
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(driverUrl, username, password);
            PreparedStatement pst = connection.prepareStatement(
                    "insert into users (uname,upwd,uemail,umobile) values (?,?,?,?)"
            );
            pst.setString(1, uname);
            pst.setString(2, upwd);
            pst.setString(3, uemail);
            pst.setString(4, umobile);

            int rowCount = pst.executeUpdate();
            dispatcher = req.getRequestDispatcher("registration.jsp");
            if (rowCount > 0) {
                req.setAttribute("status", "success");
            } else {
                req.setAttribute("status", "failed");
            }
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}

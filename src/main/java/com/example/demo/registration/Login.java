package com.example.demo.registration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uemail = req.getParameter("username");
        String upwd = req.getParameter("password");

        HttpSession session = req.getSession();
        Connection connection = null;
        RequestDispatcher dispatcher = null;

        if (uemail==null|| uemail.equals("")){
            req.setAttribute("status","invalidEmail");
            dispatcher = req.getRequestDispatcher("login.jsp");
            dispatcher.forward(req,resp);
        }
        if (upwd==null|| upwd.equals("")){
            req.setAttribute("status","invalidPassword");
            dispatcher = req.getRequestDispatcher("login.jsp");
            dispatcher.forward(req,resp);
        }


        try {
            String driverUrl = "jdbc:mysql://localhost:3306/youtube?useSSL=false";
            String username = "root";
            String password = "admin";
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(driverUrl, username, password);
            PreparedStatement pst = connection.prepareStatement(
                    "select * from users where uemail=? and upwd =?"
            );
            pst.setString(1, uemail);
            pst.setString(2, upwd);

            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                session.setAttribute("name", resultSet.getString("uname"));
                dispatcher = req.getRequestDispatcher("index.jsp");
            }else {
                req.setAttribute("status","failed");
                dispatcher = req.getRequestDispatcher("login.jsp");
            }
            dispatcher.forward(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package controllers;

import composition.Composition;
import connecting.Connecting;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;

@WebServlet(name = "Sorti", value = "/Sorti")
public class Sorti extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn;
        Connecting connecting = new Connecting();
        conn = connecting.connection();
        int id = Integer.parseInt(request.getParameter("option2"));
        int headcount = Integer.parseInt(request.getParameter("number"));
        Date date = Date.valueOf(request.getParameter("date"));
        Composition composition = new Composition();
        try {
            composition.makeProduction(conn, headcount, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect("list.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

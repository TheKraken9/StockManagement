package controllers;

import connecting.Connecting;
import history.History;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import stock.Stock;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;

@WebServlet(name = "Entre", value = "/Entre")
public class Entre extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn;
        Connecting connecting = new Connecting();
        conn = connecting.connection();
        int id = Integer.parseInt(request.getParameter("option1"));
        long number = Long.parseLong(request.getParameter("number"));
        double price = Double.parseDouble(request.getParameter("price"));
        Date date = Date.valueOf(request.getParameter("date"));
        String type = request.getParameter("type");
        History history = new History();
        System.out.println(id + " - " + number + " - " + price + " - " + date + " - " + type);
        try {
            Stock stock = new Stock();
            history.setIdProduct(id);
            history.setHeadcount(number);
            history.setDate(date);
            history.setAction(type);
            history.setPrice(price);
            stock.executes(conn, history);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("mety");
        }
        /*request.setAttribute("gg",stock);
        RequestDispatcher link = request.getRequestDispatcher("list.jsp");
        link.forward(request, response);*/
        response.sendRedirect("list.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}

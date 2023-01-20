<%--
  Created by IntelliJ IDEA.
  User: thekraken
  Date: 1/20/23
  Time: 8:55 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="connecting.Connecting" %>
<%@ page import="products.Products" %>

<%@ page import="java.util.Vector" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="composition.Composition" %>
<%@ page import="stock.Stock" %>
<%
    Connection conn;
    Connecting connecting = new Connecting();
    conn = connecting.connection();
    Products products = new Products();
    Composition composition = new Composition();
    Stock stock = new Stock();
    double priceStock = stock.getPrice(conn);
    Vector<Products> productsVector = new Vector<Products>();
    Vector<Products> productVector = new Vector<Products>();
    Vector<Products> stockVector = new Vector<Products>();
    productsVector = products.allSubProducts(conn);
    productVector = products.allFinalProducts(conn);
    stockVector = composition.allStock(conn);
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <div>
        <h1>Description sur les matieres premieres</h1>
        <table>
            <th>
                <td>Name</td>
                <td>Price</td>
                <td>Unit</td>
            </th>
            <% for (int i = 0; i < productsVector.size(); i++) { %>
            <tr>
                <td>---></td>
                <td><%= productsVector.get(i).getName() %></td>
                <td><%= productsVector.get(i).getPrice() %></td>
                <td><%= productsVector.get(i).getUnit() %></td>
            </tr>
            <% } %>
        </table>
    </div>
<br><br><br><br>
     <div>
        <h1>Description sur les produits finis</h1>
        <table>
            <th>
                <td>Name</td>
                <td>Price</td>
                <td>Unit</td>
            </th>
            <% for (int i = 0; i < productVector.size(); i++) { %>
            <tr>
                <td>---></td>
                <td><%= productVector.get(i).getName() %></td>
                <td><%= productVector.get(i).getPrice() %></td>
                <td><%= productVector.get(i).getUnit() %></td>
            </tr>
            <% } %>
        </table>
    </div>
<br><br><br><br>
     <div>
        <h1>Description sur les stocks</h1>
        <table>
            <th>
                <td>Name</td>
                <td>Headcount</td>
                <td>Price</td>
            </th>
            <% for (int i = 0; i < stockVector.size(); i++) { %>
            <tr>
                <td>---></td>
                <td><%= stockVector.get(i).getName() %></td>
                <td><%= stockVector.get(i).getHeadcount() %></td>
                <td><%= stockVector.get(i).getPrice() %></td>
            </tr>
            <% } %>
        </table>
         <h3>Prix total des stocks: <%= priceStock %> Ariary</h3>
    </div>

</body>
</html>

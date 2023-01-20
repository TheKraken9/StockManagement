<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="connecting.Connecting" %>
<%@ page import="products.Products" %>

<%@ page import="java.util.Vector" %>
<%@ page import="java.sql.Connection" %>
<%
    Connection conn;
    Connecting connecting = new Connecting();
    conn = connecting.connection();
    Products products = new Products();
    Vector<Products> productsVector = new Vector<Products>();
    Vector<Products> productVector = new Vector<Products>();
    productsVector = products.allSubProducts(conn);
    productVector = products.allFinalProducts(conn);
%>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>

<br/>
<a href="list.jsp">Plus d'info sur les produits</a>
<div>
    <div style="">
         <h1>Entré de stock</h1>
        <form method="GET" action="http://localhost:8080/gestionStock_war_exploded/Entre">
            <label for="option1">Produit : </label>
            <select id="option1" name="option1">
                <% for (int i = 0; i < productsVector.size(); i++) { %>
                    <option value="<%= productsVector.get(i).getId() %>"><%= productsVector.get(i).getName() %></option>
                <% } %>
            </select><br>
            <label for="number1">Headcount : </label><input type="number" name="number" id="number1"><br>
            <label for="number1">Price : </label><input type="number" name="price" id="price1"><br>
            <label for="date1">Date : </label><input type="date" name="date" id="date1"><br>
            <input type="hidden" value="entre" name="type" id="type1"><br>
            <input type="submit" value="Inserer">
        </form>
    </div>

    <div style="">
         <h1>Fabrication</h1>
            <form method="GET" action="http://localhost:8080/gestionStock_war_exploded/Sorti">
            <label for="option2">Produit : </label>
            <select id="option2" name="option2">
                <% for (int i = 0; i < productVector.size(); i++) { %>
                    <option name="option2" value="<%= productVector.get(i).getId() %>"><%= productVector.get(i).getName() %></option>
                <% } %>
            </select><br>
            <label for="number">Headcount : </label><input type="number" name="number" id="number"><br>
            <label for="date">Date : </label><input type="date" name="date" id="date"><br>
            <input type="hidden" value="sorti" name="date" id="type2"><br>
            <input type="submit" value="Produire">
        </form>
    </div>
</div>
</body>
</html>
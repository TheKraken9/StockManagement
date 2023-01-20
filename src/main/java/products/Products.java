package products;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class Products {
    private int id;
    private String name;
    private double price;
    private boolean isPrimary;
    private String unit;
    private double proportion;
    private double headcount;
    private int idProduct;

    public Products() {
    }

    public Products(int id, String name, double price, boolean isPrimary, String unit) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.isPrimary = isPrimary;
        this.unit = unit;
    }

    public Products(int id, String name, double price, String unit, double proportion) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.proportion = proportion;
    }

    public Products(int idProduct, double headcount, double price) {
        this.price = price;
        this.headcount = headcount;
        this.idProduct = idProduct;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getProportion() {
        return proportion;
    }

    public void setProportion(double proportion) {
        this.proportion = proportion;
    }

    public double getHeadcount() {
        return headcount;
    }

    public void setHeadcount(double headcount) {
        this.headcount = headcount;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public Vector<Products> allProducts(Connection co) throws SQLException {
        Vector<Products> products = new Vector<Products>();
        String sql = "SELECT * FROM products";
        Statement statement = co.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()) {
            Products product = new Products();
            product.setId(resultSet.getInt("id"));
            product.setName(resultSet.getString("name"));
            product.setPrice(resultSet.getDouble("price"));
            product.setPrimary(resultSet.getBoolean("isPrimary"));
            product.setUnit(resultSet.getString("unit"));
            //System.out.println(resultSet.getInt("id"));
            products.add(product);
        }
        return products;
    }

    public Vector<Products> allSubProducts(Connection co) throws SQLException {
        Vector<Products> products = new Vector<Products>();
        String sql = "SELECT * FROM products where isPrimary = true";
        Statement statement = co.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        System.out.println(sql);
        while(resultSet.next()) {
            Products product = new Products();
            product.setId(resultSet.getInt("id"));
            product.setName(resultSet.getString("name"));
            product.setPrice(resultSet.getDouble("price"));
            product.setPrimary(resultSet.getBoolean("isPrimary"));
            product.setUnit(resultSet.getString("unit"));
            //System.out.println(resultSet.getInt("id"));
            products.add(product);
        }
        return products;
    }

     public Vector<Products> allFinalProducts(Connection co) throws SQLException {
        Vector<Products> products = new Vector<Products>();
        String sql = "SELECT * FROM products where isPrimary = false";
        Statement statement = co.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        System.out.println(sql);
        while(resultSet.next()) {
            Products product = new Products();
            product.setId(resultSet.getInt("id"));
            product.setName(resultSet.getString("name"));
            product.setPrice(resultSet.getDouble("price"));
            product.setPrimary(resultSet.getBoolean("isPrimary"));
            product.setUnit(resultSet.getString("unit"));
            //System.out.println(resultSet.getInt("id"));
            products.add(product);
        }
        return products;
    }
}

package stock;

import composition.Composition;
import history.History;
import products.Products;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Date;
import java.util.Vector;

public class Stock {

    private int id;
    private int idProduct;
    private double headcount;
    private double price;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public double getHeadcount() {
        return headcount;
    }

    public void setHeadcount(double headcount) {
        this.headcount = headcount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Stock() {
    }

    public Stock(int idProduct, double headcount, double price) {
        this.idProduct = idProduct;
        this.headcount = headcount;
        this.price = price;
    }

    public Stock(double headcount, String name, double price) {
        this.headcount = headcount;
        this.price = price;
        this.name = name;
    }

    public void newPrice(Connection co, History history) throws Exception {
        String sql = "insert into historyComponent (idProduct, price, headcount, date, action) values (" + history.getIdProduct() + ",'" + history.getPrice() + "'," + history.getHeadcount() + ",'" + history.getDate()+ "','" + history.getAction() + "')";
        Statement statement = co.createStatement();
        int resultSet = statement.executeUpdate(sql);
        System.out.println(sql);
        if(resultSet != 0) {
            System.out.println("success");
        }
        System.out.println(sql);
    }

    public boolean stockExist(Connection co, Stock stock) throws Exception {
        String sql = "select * from stock where idProduct = " + stock.getIdProduct();
        Statement statement = co.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if(resultSet.isBeforeFirst()) {
            System.out.println("Update du produit");
            return true;
        } else {
            System.out.println("Insertion d'un stock pour la premiere fois");
            return false;
        }
    }

    public void updateStock(Connection co, Stock stock) throws Exception {
        boolean exist = this.stockExist(co, stock);
        if(exist) {
             String sql = "update stock set price = " + stock.getPrice() + ", headcount = " + stock.getHeadcount() + " where idProduct = " + stock.getIdProduct();
            Statement statement = co.createStatement();
            int resultSet = statement.executeUpdate(sql);
            if(resultSet != 0) {
                System.out.println("success update");
            }
            System.out.println(sql);
        } else {
            String sql = "insert into stock(idProduct,headcount, price) values(" + stock.getIdProduct() + "," + stock.getHeadcount() + "," + stock.getPrice() +")";
            Statement statement = co.createStatement();
            int res = statement.executeUpdate(sql);
            if(res != 0) {
                System.out.println("successfully !!!");
            }
        }

    }

    public void updateProduct(Connection co, Stock stock) throws Exception {
        String sql = "update products set price = " + stock.getPrice() + " where id = " + stock.getIdProduct();
        Statement statement = co.createStatement();
        int resultSet = statement.executeUpdate(sql);
        if(resultSet != 0) {
            System.out.println("success update");
        }
        System.out.println(sql);
    }

    public Stock getStock(Connection co, History history) throws Exception {
        String sql = "select * from stock where idProduct = " + history.getIdProduct();
        Stock stock = new Stock();
        Statement statement = co.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()) {
            stock.setIdProduct(resultSet.getInt("idProduct"));
            stock.setHeadcount(resultSet.getDouble("headcount"));
            stock.setPrice(resultSet.getDouble("price"));
        }
        return stock;
    }

    public void executes(Connection co, History history) throws Exception {
        Stock stock = new Stock();
        Stock stock2 = new Stock();
        Stock lastStock = new Stock();
        double price = 0;
        if(history.getAction().equals("entre")) {
            stock.newPrice(co, history);
            lastStock = stock2.getStock(co, history);
            price = ((history.getPrice()* history.getHeadcount())+(lastStock.getPrice()* lastStock.getHeadcount()))/(history.getHeadcount()+ lastStock.getHeadcount());
            stock.setIdProduct(history.getIdProduct());
            stock.setPrice(price);
            stock.setHeadcount(history.getHeadcount()+ lastStock.getHeadcount());
            this.updateStock(co, stock);
            this.updateProduct(co, stock);
        } else if(history.getAction().equals("sorti")) {
            stock.newPrice(co, history);
        }
    }

    public double getPrice(Connection co) throws Exception {
        Products product = new Products();
        Composition composition = new Composition();
        Vector<Products> products = new Vector<>();
        Vector<Products> stock = new Vector<>();
        stock = composition.allStock(co);
        products = product.allProducts(co);
        double lastPrice = 0;
        for (int i = 0; i < products.size(); i++) {
            for (int j = 0; j < stock.size(); j++) {
                if(products.get(i).getName().equals(stock.get(j).getName())) {
                    lastPrice += (products.get(i).getPrice()*stock.get(j).getPrice());
                }
            }
        }
        System.out.println(lastPrice);
        return lastPrice;
    }

}

package composition;

import history.History;
import products.Products;
import stock.Stock;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class Composition {
    private int id;
    private int idProduct;
    private int idComponent;
    private double proportion;

    public Composition() {
    }

    public Composition(int id, int idProduct, int idComponent, double proportion) {
        this.id = id;
        this.idProduct = idProduct;
        this.idComponent = idComponent;
        this.proportion = proportion;
    }

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

    public int getIdComponent() {
        return idComponent;
    }

    public void setIdComponent(int idComponent) {
        this.idComponent = idComponent;
    }

    public double getProportion() {
        return proportion;
    }

    public void setProportion(double proportion) {
        this.proportion = proportion;
    }

    public Vector<Composition> getCompositions(Connection co, int id) throws SQLException {
        String sql = "SELECT * from composition where idProduct = " + id;
        Vector<Composition> result = new Vector<Composition>();
        Statement statement = co.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            Composition composition = new Composition();
            composition.setId(resultSet.getInt("id"));
            composition.setIdProduct(resultSet.getInt("idProduct"));
            composition.setIdComponent(resultSet.getInt("idComponent"));
            composition.setProportion(resultSet.getDouble("proportion"));
            //System.out.println(resultSet.getInt("idComponent") + " - " + resultSet.getDouble("proportion"));
            result.add(composition);
        }
        return result;
    }

    public Vector<Products> compoResult(Connection co, double qtt, int id) throws Exception {
        Vector<Products> products = new Vector<>();
        String sql = "select * from compo where idProduct =" + id;
        Statement statement = co.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()) {
            Products product = new Products();
            product.setId(resultSet.getInt("id"));
            product.setName(resultSet.getString("name"));
            product.setPrice(resultSet.getDouble("price"));
            product.setUnit(resultSet.getString("unit"));
            product.setProportion(resultSet.getDouble("proportion")*qtt);
            products.add(product);
        }
        return products;
    }

    public Vector<Products> allStock(Connection co) throws Exception {
        Vector<Products> products = new Vector<>();
        String sql = "select * from stocks";
        Statement statement = co.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            Products product = new Products();
            product.setId(resultSet.getInt("id"));
            product.setName(resultSet.getString("name"));
            product.setHeadcount(resultSet.getDouble("headcount"));
            product.setPrice(resultSet.getDouble("price"));
            products.add(product);
        }
        return products;
    }

    public boolean verifyInsert(Connection co, int nb, int id) throws Exception {
        Vector<Products> products = new Vector<>();
        Vector<Products> stock = new Vector<>();
        products = this.compoResult(co, nb, id);
        stock = this.allStock(co);
        for (int i = 0; i < products.size(); i++) {
            for (int j = 0; j < stock.size(); j++) {
                if(stock.get(j).getName().equals(products.get(i).getName())) {
                    System.out.println(stock.get(j).getHeadcount() + " stock");
                    System.out.println(products.get(i).getProportion() + " proportion");
                    if(stock.get(j).getHeadcount()-products.get(i).getProportion() < 0) {
                        System.out.println("stock de " + stock.get(j).getName() + " insuffisant : ajouter " + (products.get(i).getProportion()-stock.get(j).getHeadcount()));
                        return false;
                        //throw new Exception("Stock insuffisant : ajouter " + (products.get(i).getProportion()-stock.get(j).getHeadcount()));
                    } else {
                        System.out.println(stock.get(j).getHeadcount()-products.get(i).getProportion());
                    }
                }
            }
        }
        return true;
    }

    public void makeProduction(Connection co, int nb, int id) throws Exception {
        if(verifyInsert(co, nb, id)) {
            double price = 0;
            double proportion = 0;
            Vector<Products> products = new Vector<Products>();
            products = this.compoResult(co, nb, id);
            for (int i = 0; i < products.size(); i++) {
                price += (products.get(i).getProportion()*products.get(i).getPrice());
                proportion += (products.get(i).getProportion());
            }
            System.out.println(price + " - " + proportion);
            this.update(co, nb, id, price, proportion);
        } else {
            System.out.println("impossible");
        }
    }

    public void update(Connection co, int nb, int id, double price, double proportion) throws Exception {
        History history = new History();
        history.setIdProduct(id);
        history.setPrice(price);
        history.setHeadcount(proportion);
        Stock stock2 = new Stock();
        Stock stock = new Stock();
        stock = stock2.getStock(co, history);
        double headcount = stock.getHeadcount()+nb;
        System.out.println(headcount+nb + " io eee");
        String sql = "update stock set headcount =" + headcount + " where idProduct = " + id ;
        Statement statement = co.createStatement();
        int result = statement.executeUpdate(sql);
        System.out.println(sql + " ito ny sql");
        if(result != 0) {
            System.out.println("success modification");
        }
        this.updateToo(co, nb, id);
    }

    public void updateToo(Connection co, int nb, int id) throws Exception{
        Vector<Products> compositions = new Vector<>();
        Vector<Products> products = new Vector<>();
        compositions = this.compoResult(co, nb, id);
        products = this.allStock(co);
        for (int i = 0; i < compositions.size(); i++) {
            for (int j = 0; j < products.size(); j++) {

                double stock = 0;
                int result = 0;
                if(compositions.get(i).getName().equals(products.get(j).getName())) {
                    Statement statement = co.createStatement();
                    stock = (products.get(j).getHeadcount()-(compositions.get(i).getHeadcount()+nb));
                    String sql = "update stock set headcount = " + stock + " where idProduct = " + products.get(j).getId() + "";
                    result = statement.executeUpdate(sql);
                    System.out.println(sql);
                    if(result != 0) {
                        System.out.println("success modif");
                    }
                }
                stock = 0;
                result = 0;
            }

        }
    }

    public void sellProduction(Connection co, Products products, double qtt) throws Exception {
        Products product = this.allFinalProducts(co, products);
        double stock = this.getStock(co, product);
        String sql = "update stock set headcount = " + (stock-qtt) + " where idProduct = " + product.getId() + "";
        Statement statement = co.createStatement();
        int res = statement.executeUpdate(sql);
        if(res != 0) {
            System.out.println("success for sell production");
        }
    }

    public Products allFinalProducts(Connection co, Products products) throws Exception {
        String sql = "select * from products where isPrimary = false and name = '" + products.getName() +"'";
        Products finalProduct = new Products();
        Statement statement = co.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        System.out.println(sql);
        while (resultSet.next()) {
            finalProduct.setId(resultSet.getInt("id"));
            finalProduct.setName(resultSet.getString("name"));
            finalProduct.setPrice(resultSet.getDouble("price"));
            finalProduct.setPrimary(resultSet.getBoolean("isPrimary"));
            finalProduct.setUnit(resultSet.getString("unit"));
        }
        return finalProduct;
    }

    public double getStock(Connection co, Products products) throws Exception {
        double stock = 0;
        String sql = "select * from stock where idProduct = " + products.getId();
        Statement statement = co.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            stock += resultSet.getDouble("headcount");
        }
        return stock;
    }

}

import composition.Composition;
import history.History;
import products.Products;
import stock.Stock;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    private final String url = "jdbc:postgresql://localhost/stock";
    private final String user = "postgres";
    private final String password = "postgres";
    public static void main(String[] args) {

        try {
            Main main = new Main();
            Connection co = main.connection();
            /*Vector<Products> products = new Vector();
            Products product = new Products();
            Composition composition = new Composition();
            products = product.getFinalProducts(co);
            for (int i = 0; i < products.size(); i++) {
                composition.getCompositions(co, products.get(i).getId());
            }*/


            Stock stock = new Stock();
            //History history = new History(7, 88, 88, Date.valueOf("2022-01-16"), "entre");
            //history.newPrice(co, history);
            //stock.setIdProduct(6);
            //stock.stockExist(co, stock);

            //stock.executes(co, history);

            //stock.getPrice(co); -- prix de stock

            /*Products products = new Products();
            products.allSubProducts(co);*/
            //products.setName("Limonade");

            Composition composition = new Composition();
            //composition.compoResult(co, 10, 1);
            composition.makeProduction(co, 5, 1);
            //composition.sellProduction(co, products,5);

        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public Connection connection() {
        Connection conn = null;
        try {
            String url = "jdbc:postgresql://localhost/stock";
            String user = "postgres";
            String password = "postgres";
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
package history;

import java.sql.Date;

public class History {

    private int id;
    private int idProduct;
    private double price;
    private double headcount;
    private Date date;
    private String action;
    private String name;

    public History() {
    }

    public History(int idProduct, double price, double headcount, Date date, String action) {
        this.idProduct = idProduct;
        this.price = price;
        this.headcount = headcount;
        this.date = date;
        this.action = action;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getHeadcount() {
        return headcount;
    }

    public void setHeadcount(double headcount) {
        this.headcount = headcount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}

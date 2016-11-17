package vvme.eleme_demo.bean;

/**
 * Created by VV on 2016/11/15.
 */

public class Dish {
    private String name;
    private double price;
    private int count;

    public Dish(String name, double price, int count) {
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public Dish() {
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", count=" + count +
                '}';
    }
}

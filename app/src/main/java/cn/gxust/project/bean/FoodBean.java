package cn.gxust.project.bean;

import java.io.Serializable;

public class FoodBean implements Serializable {
    private int id;
    private String name;    // 菜品名称
    private String type;    // 菜品类别
    private int sales;      // 菜品销量
    private double price;   // 菜品价格
    private int num;        // 菜品数量
    private String image;   // 菜品图片

    public FoodBean() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}

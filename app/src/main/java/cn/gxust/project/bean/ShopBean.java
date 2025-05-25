package cn.gxust.project.bean;

import java.io.Serializable;

public class ShopBean implements Serializable {
    private int id;             // 商店id
    private String name;        // 商店名称
    private double score;       // 商店评分
    private int sales;          // 商店销量
    private String addr;        // 商店地址
    private String phone;         // 商家电话
    private String image;         // 商店图片

    public ShopBean() {
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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}

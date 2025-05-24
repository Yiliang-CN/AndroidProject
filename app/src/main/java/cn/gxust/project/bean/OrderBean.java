package cn.gxust.project.bean;

import java.io.Serializable;

public class OrderBean implements Serializable {
    private Long id;
    private int shopId;        // 订单商家编号       这是用于店铺评论处请求数据用
    private String shopName;
    private int userId;       // 订单用户编号       这是用于用户订单处请求数据用
    private String userName;
    private String content;    // 订单内容             这是记录用户的订单内容
    private double price;      // 订单价格             这是记录用户的订单价格
    private String time;
    private String addr;       // 订单地址             这是记录用户的订单地址
    private String phone;        // 订单电话             这是记录用户的订单电话
    private String state;

    public OrderBean() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

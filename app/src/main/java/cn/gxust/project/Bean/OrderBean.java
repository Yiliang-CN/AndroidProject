package cn.gxust.project.Bean;

import java.io.Serializable;

public class OrderBean implements Serializable {
    private int orderID;            // 订单编号             这只是用于视图显示
    private int orderShopID;        // 订单商家编号       这是用于店铺评论处请求数据用
    private String orderShopName;   // 订单商家名称       这只是用于视图显示
    private Long orderUserID;       // 订单用户编号       这是用于用户订单处请求数据用
    private String orderUserName;   // 订单用户名称       这只是用于视图显示
    private String orderContent;    // 订单内容             这是记录用户的订单内容
    private double orderPrice;      // 订单价格             这是记录用户的订单价格
    private String orderTime;       // 订单时间             这是记录用户的订单时间
    private String orderAddr;       // 订单地址             这是记录用户的订单地址
    private Long orderPhone;        // 订单电话             这是记录用户的订单电话
    private String orderState;      // 订单状态             这是记录用户的订单状态
    private int orderScore;         // 评分                这是记录用户下单后的订单评分
    private String orderComment;    // 订单评论             这是记录用户下单后的订单评论
    private String orderCommentTime;// 评论时间             这是记录用户下单后的评论时间

    public OrderBean(int orderID, int orderShopID, String orderShopName, Long orderUserID, String orderUserName, String orderContent, double orderPrice, String orderTime, String orderAddr, Long orderPhone, String orderState, int orderScore, String orderComment, String orderCommentTime) {
        this.orderID = orderID;
        this.orderShopID = orderShopID;
        this.orderShopName = orderShopName;
        this.orderUserID = orderUserID;
        this.orderUserName = orderUserName;
        this.orderContent = orderContent;
        this.orderPrice = orderPrice;
        this.orderTime = orderTime;
        this.orderAddr = orderAddr;
        this.orderPhone = orderPhone;
        this.orderState = orderState;
        this.orderScore = orderScore;
        this.orderComment = orderComment;
        this.orderCommentTime = orderCommentTime;
    }


    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getOrderShopID() {
        return orderShopID;
    }

    public void setOrderShopID(int orderShopID) {
        this.orderShopID = orderShopID;
    }

    public String getOrderShopName() {
        return orderShopName;
    }

    public void setOrderShopName(String orderShopName) {
        this.orderShopName = orderShopName;
    }

    public Long getOrderUserID() {
        return orderUserID;
    }

    public void setOrderUserID(Long orderUserID) {
        this.orderUserID = orderUserID;
    }

    public String getOrderUserName() {
        return orderUserName;
    }

    public void setOrderUserName(String orderUserName) {
        this.orderUserName = orderUserName;
    }

    public String getOrderContent() {
        return orderContent;
    }

    public void setOrderContent(String orderContent) {
        this.orderContent = orderContent;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderAddr() {
        return orderAddr;
    }

    public void setOrderAddr(String orderAddr) {
        this.orderAddr = orderAddr;
    }

    public Long getOrderPhone() {
        return orderPhone;
    }

    public void setOrderPhone(Long orderPhone) {
        this.orderPhone = orderPhone;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public int getOrderScore() {
        return orderScore;
    }

    public void setOrderScore(int orderScore) {
        this.orderScore = orderScore;
    }

    public String getOrderComment() {
        return orderComment;
    }

    public void setOrderComment(String orderComment) {
        this.orderComment = orderComment;
    }

    public String getOrderCommentTime() {
        return orderCommentTime;
    }

    public void setOrderCommentTime(String orderCommentTime) {
        this.orderCommentTime = orderCommentTime;
    }
}

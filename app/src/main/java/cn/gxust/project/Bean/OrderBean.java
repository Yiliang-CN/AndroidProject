package cn.gxust.project.Bean;

import java.io.Serializable;

public class OrderBean implements Serializable {
    private int orderID;            // 订单编号
    private int orderShopID;        // 订单商家编号
    private String orderShopName;   // 订单商家名称
    private int orderUserID;        // 订单用户编号
    private String orderUserName;   // 订单用户名称
    private String orderContent;    // 订单内容
    private int orderNum;           // 订单数量
    private String orderPrice;      // 订单价格
    private String orderTime;       // 订单时间
    private String orderAddr;       // 订单地址
    private String orderPhone;      // 订单电话
    private String orderState;      // 订单状态
    private String orderComment;    // 菜品评论
    private String orderCommentTime;// 评论时间

    public OrderBean(int orderID, int orderShopID, String orderShopName, int orderUserID, String orderUserName, String orderContent, int orderNum, String orderPrice, String orderTime, String orderAddress, String orderPhone, String orderState, String orderComment, String orderCommentTime) {
        this.orderID = orderID;
        this.orderShopID = orderShopID;
        this.orderShopName = orderShopName;
        this.orderUserID = orderUserID;
        this.orderUserName = orderUserName;
        this.orderContent = orderContent;
        this.orderNum = orderNum;
        this.orderPrice = orderPrice;
        this.orderTime = orderTime;
        this.orderAddr = orderAddress;
        this.orderPhone = orderPhone;
        this.orderState = orderState;
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

    public int getOrderUserID() {
        return orderUserID;
    }

    public void setOrderUserID(int orderUserID) {
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

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
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

    public String getOrderPhone() {
        return orderPhone;
    }

    public void setOrderPhone(String orderPhone) {
        this.orderPhone = orderPhone;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
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

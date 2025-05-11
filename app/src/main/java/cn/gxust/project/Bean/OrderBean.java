package cn.gxust.project.Bean;

public class OrderBean {
    private int orderId;            // 订单编号
    private int orderUserId;        // 订单用户编号
    private int orderShopId;        // 订单商家编号
    private int orderFoodId;        // 订单菜品编号
    private int orderNum;           // 订单数量
    private String orderPrice;      // 订单价格
    private String orderTime;       // 订单时间
    private String orderAddress;    // 订单地址
    private String orderPhone;      // 订单电话
    private String orderState;      // 订单状态
    private String orderFoodComment;// 菜品评论
    private String orderShopComment;// 商家评论
    private String orderCommentTime;// 评论时间

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderUserId() {
        return orderUserId;
    }

    public void setOrderUserId(int orderUserId) {
        this.orderUserId = orderUserId;
    }

    public int getOrderShopId() {
        return orderShopId;
    }

    public void setOrderShopId(int orderShopId) {
        this.orderShopId = orderShopId;
    }

    public int getOrderFoodId() {
        return orderFoodId;
    }

    public void setOrderFoodId(int orderFoodId) {
        this.orderFoodId = orderFoodId;
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

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
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

    public String getOrderFoodComment() {
        return orderFoodComment;
    }

    public void setOrderFoodComment(String orderFoodComment) {
        this.orderFoodComment = orderFoodComment;
    }

    public String getOrderShopComment() {
        return orderShopComment;
    }

    public void setOrderShopComment(String orderShopComment) {
        this.orderShopComment = orderShopComment;
    }

    public String getOrderCommentTime() {
        return orderCommentTime;
    }

    public void setOrderCommentTime(String orderCommentTime) {
        this.orderCommentTime = orderCommentTime;
    }


}

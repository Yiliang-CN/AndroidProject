package cn.gxust.project.Bean;

import java.io.Serializable;

public class ShopBean implements Serializable {
    private int shopId;             // 商店id
    private String shopName;        // 商店名称
    private String shopSales;       // 商店销量
    private String shopPrice;       // 商店价格
    private String shopPhone;          // 商家电话
    private String shopAddr;     // 商店地址
    private String shopImg;         // 商店图片

    public ShopBean(int shopId, String shopName, String shopSales, String shopPrice, String shopPhone, String shopAddress, String shopImg) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.shopSales = shopSales;
        this.shopPrice = shopPrice;
        this.shopPhone = shopPhone;
        this.shopAddr = shopAddress;
        this.shopImg = shopImg;
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

    public String getShopSales() {
        return shopSales;
    }

    public void setShopSales(String shopSales) {
        this.shopSales = shopSales;
    }

    public String getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(String shopPrice) {
        this.shopPrice = shopPrice;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public String getShopAddr() {
        return shopAddr;
    }

    public void setShopAddr(String shopAddr) {
        this.shopAddr = shopAddr;
    }

    public String getShopImg() {
        return shopImg;
    }

    public void setShopImg(String shopImg) {
        this.shopImg = shopImg;
    }

}

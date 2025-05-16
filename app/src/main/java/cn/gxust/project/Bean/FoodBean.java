package cn.gxust.project.Bean;

import java.io.Serializable;

public class FoodBean implements Serializable {
    private int foodID;         // 菜品ID
    private int foodShopID;     // 菜品所属商家ID
    private String foodName;    // 菜品名称
    private String foodType;    // 菜品类别
    private int foodSales;      // 菜品销量
    private double foodPrice;   // 菜品价格
    private int foodNum;        // 菜品数量
    private String foodImage;   // 菜品图片

    public FoodBean(int foodID,int foodShopID, String foodName, String foodType, int foodSales, double foodPrice, int foodNum, String foodImage) {
        this.foodID = foodID;
        this.foodShopID = foodShopID;
        this.foodName = foodName;
        this.foodType = foodType;
        this.foodSales = foodSales;
        this.foodPrice = foodPrice;
        this.foodNum = foodNum;
        this.foodImage = foodImage;
    }

    public int getFoodID() {
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }

    public int getFoodShopID() {
        return foodShopID;
    }

    public void setFoodShopID(int foodShopID) {
        this.foodShopID = foodShopID;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public int getFoodSales() {
        return foodSales;
    }

    public void setFoodSales(int foodSales) {
        this.foodSales = foodSales;
    }

    public double getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(double foodPrice) {
        this.foodPrice = foodPrice;
    }

    public int getFoodNum() {
        return foodNum;
    }

    public void setFoodNum(int foodNum) {
        this.foodNum = foodNum;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }


}

package cn.gxust.project.Bean;

public class FoodBean {
    private int foodID;         // 菜品ID
    private String foodName;    // 菜品名称
    private String foodType;    // 菜品类别
    private String foodSales;   // 菜品销量
    private String foodPrice;   // 菜品价格
    private String foodImage;   // 菜品图片

    public FoodBean(int foodID, String foodName, String foodType, String foodSales, String foodPrice, String foodImage) {
        this.foodID = foodID;
        this.foodName = foodName;
        this.foodType = foodType;
        this.foodSales = foodSales;
        this.foodPrice = foodPrice;
        this.foodImage = foodImage;
    }

    public int getFoodID() {
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
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

    public String getFoodSales() {
        return foodSales;
    }

    public void setFoodSales(String foodSales) {
        this.foodSales = foodSales;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }


}

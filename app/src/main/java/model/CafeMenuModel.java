package model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SungHyun on 2017-11-03.
 */

public class CafeMenuModel {
    @SerializedName("cafe_id")
    private String cafeId;
    @SerializedName("menu_name")
    private String menuName;
    @SerializedName("menu_price")
    private String menuPrice;

    public String getCafeId() {
        return cafeId;
    }

    public void setCafeId(String cafeId) {
        this.cafeId = cafeId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(String menuPrice) {
        this.menuPrice = menuPrice;
    }
}

package api.response;

import java.util.ArrayList;

import model.NaverBlogModel;

/**
 * Created by SungHyun on 2017-11-02.
 * Naver Blog OPEN API 전체적인 구조
 * lastBuildDate
 * total
 * start
 * display
 * items
 */

public class NaverBlogResponse {

    private ArrayList<NaverBlogModel> items;

    public ArrayList<NaverBlogModel> getItems() {
        return items;
    }

    public void setItems(ArrayList<NaverBlogModel> items) {
        this.items = items;
    }
}

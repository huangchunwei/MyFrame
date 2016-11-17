package vvme.eleme_demo.bean;

import java.util.List;

/**
 * Created by VV on 2016/11/15.
 */

public class DishMenu {
    private String name;
    private List<Dish> mDishList;

    public DishMenu(String name, List<Dish> itemMenuBeanList) {
        this.name = name;
        mDishList = itemMenuBeanList;
    }

    public DishMenu() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Dish> getDishList() {
        return mDishList;
    }

    public void setDishList(List<Dish> dishList) {
        mDishList = dishList;
    }

    @Override
    public String toString() {
        return "DishMenu{" +
                "name='" + name + '\'' +
                ", mDishList=" + mDishList +
                '}';
    }
}

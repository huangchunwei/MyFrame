package vvme.eleme_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vvme.eleme_demo.adapter.DishMenuAdapter;
import vvme.eleme_demo.adapter.MenuAdapter;
import vvme.eleme_demo.bean.Dish;
import vvme.eleme_demo.bean.DishMenu;
import vvme.eleme_demo.bean.ShoppingCart;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mMenuRV;
    private RecyclerView mItemMenuRV;

    private List<DishMenu> mMenuBeanList;
    private List<Dish> mItemMenuBeanList;
    private ShoppingCart shopCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMenuRV = (RecyclerView) findViewById(R.id.left_menu);
        mItemMenuRV = (RecyclerView) findViewById(R.id.right_menu);
        initData();

        mMenuRV.setLayoutManager(new LinearLayoutManager(this));
        mMenuRV.setAdapter(new MenuAdapter(this, mMenuBeanList));
        mItemMenuRV.setLayoutManager(new LinearLayoutManager(this));
        mItemMenuRV.setAdapter(new DishMenuAdapter(this, mMenuBeanList, shopCart));
    }

    private void initData() {
        shopCart = new ShoppingCart();
        mMenuBeanList = new ArrayList<>();
        mItemMenuBeanList = new ArrayList<>();
        mItemMenuBeanList.add(new Dish("面包", 1.00, 10));
        mItemMenuBeanList.add(new Dish("蛋挞", 1.00, 10));
        mItemMenuBeanList.add(new Dish("肠粉", 1.00, 10));
        mItemMenuBeanList.add(new Dish("牛奶", 1.00, 10));
        mItemMenuBeanList.add(new Dish("绿茶饼", 1.00, 10));
        mItemMenuBeanList.add(new Dish("花卷", 1.00, 10));
        mItemMenuBeanList.add(new Dish("包子", 1.00, 10));
        mItemMenuBeanList.add(new Dish("豆浆", 1.00, 10));
        mItemMenuBeanList.add(new Dish("油条", 1.00, 10));
        DishMenu menuBean = new DishMenu("早餐", mItemMenuBeanList);

        mItemMenuBeanList = new ArrayList<>();
        mItemMenuBeanList.add(new Dish("粥", 1.00, 10));
        mItemMenuBeanList.add(new Dish("炒饭", 1.00, 10));
        mItemMenuBeanList.add(new Dish("炒米粉", 1.00, 10));
        mItemMenuBeanList.add(new Dish("炒粿条", 1.00, 10));
        mItemMenuBeanList.add(new Dish("炒牛河", 1.00, 10));
        mItemMenuBeanList.add(new Dish("炒菜", 1.00, 10));
        DishMenu menuBean1 = new DishMenu("午餐", mItemMenuBeanList);

        mItemMenuBeanList = new ArrayList<>();
        mItemMenuBeanList.add(new Dish("淋菜", 1.00, 10));
        mItemMenuBeanList.add(new Dish("川菜", 1.00, 10));
        mItemMenuBeanList.add(new Dish("湘菜", 1.00, 10));
        mItemMenuBeanList.add(new Dish("粤菜", 1.00, 10));
        mItemMenuBeanList.add(new Dish("豫菜", 1.00, 10));
        mItemMenuBeanList.add(new Dish("赣菜", 1.00, 10));
        mItemMenuBeanList.add(new Dish("东北菜", 1.00, 10));
        DishMenu menuBean2 = new DishMenu("晚餐", mItemMenuBeanList);

        mMenuBeanList.add(menuBean);
        mMenuBeanList.add(menuBean1);
        mMenuBeanList.add(menuBean2);
    }
}

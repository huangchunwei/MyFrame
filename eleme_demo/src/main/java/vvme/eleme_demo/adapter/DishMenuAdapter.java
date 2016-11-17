package vvme.eleme_demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import vvme.eleme_demo.R;
import vvme.eleme_demo.bean.Dish;
import vvme.eleme_demo.bean.DishMenu;
import vvme.eleme_demo.bean.ShoppingCart;
import vvme.eleme_demo.bean.ShoppingCartWatcher;

/**
 * Created by VV on 2016/11/15.
 */

public class DishMenuAdapter extends RecyclerView.Adapter {

    private final int MENU_TYPE = 0;
    private final int DISH_TYPE = 1;
    private final int HEAD_TYPE = 2;

    private Context mContext;
    private List<DishMenu> mDishMenuList;
    private ShoppingCart mShoppingCart;
    private int mItemCount;
    private ShoppingCartWatcher shopCartImp;

    public DishMenuAdapter(Context context, List<DishMenu> itemMenuBeanList, ShoppingCart shoppingCart) {
        mContext = context;
        mDishMenuList = itemMenuBeanList;
        mShoppingCart = shoppingCart;
        mItemCount = mDishMenuList.size();
        for (DishMenu dishMenu : mDishMenuList) {
            mItemCount += dishMenu.getDishList().size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        int sum = 0;
        for (DishMenu menu : mDishMenuList) {
            if (position == sum) {
                return MENU_TYPE;
            }
            sum += menu.getDishList().size() + 1;
        }
        return DISH_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MENU_TYPE) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.right_menu_layout, parent, false);
            MenuViewHolder viewHolder = new MenuViewHolder(view);
            return viewHolder;
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_item_menu, parent, false);
            MyItemMenuViewHolder myItemMenuViewHolder = new MyItemMenuViewHolder(view);
            return myItemMenuViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == MENU_TYPE) {
            MenuViewHolder menuholder = (MenuViewHolder) holder;
            if (menuholder != null) {
                menuholder.right_menu_title.setText(getMenuByPosition(position).getName());
                menuholder.right_menu_layout.setContentDescription(position + "");
            }
        } else {
            final MyItemMenuViewHolder dishholder = (MyItemMenuViewHolder) holder;
            if (dishholder != null) {

                final Dish dish = getDishByPosition(position);
                dishholder.right_dish_name_tv.setText(dish.getName());
                dishholder.right_dish_price_tv.setText(dish.getPrice() + "");
                dishholder.right_dish_layout.setContentDescription(position + "");

                int count = 0;
                if (mShoppingCart.getShoppingSingleMap().containsKey(dish)) {
                    count = mShoppingCart.getShoppingSingleMap().get(dish);
                }
                if (count <= 0) {
                    dishholder.right_dish_remove_iv.setVisibility(View.GONE);
                    dishholder.right_dish_account_tv.setVisibility(View.GONE);
                } else {
                    dishholder.right_dish_remove_iv.setVisibility(View.VISIBLE);
                    dishholder.right_dish_account_tv.setVisibility(View.VISIBLE);
                    dishholder.right_dish_account_tv.setText(count + "");
                }
                dishholder.right_dish_add_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mShoppingCart.addShoppingSingle(dish)) {
                            notifyItemChanged(position);
                            if (shopCartImp != null)
                                shopCartImp.add(view, position);
                        }
                    }
                });

                dishholder.right_dish_remove_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mShoppingCart.subShoppingSingle(dish)) {
                            notifyItemChanged(position);
                            if (shopCartImp != null)
                                shopCartImp.remove(view, position);
                        }
                    }
                });
            }
        }
    }

    public DishMenu getMenuByPosition(int position) {
        int sum = 0;
        for (DishMenu menu : mDishMenuList) {
            if (position == sum) {
                return menu;
            }
            sum += menu.getDishList().size() + 1;
        }
        return null;
    }

    public Dish getDishByPosition(int position) {
        for (DishMenu menu : mDishMenuList) {
            if (position > 0 && position <= menu.getDishList().size()) {
                return menu.getDishList().get(position - 1);
            } else {
                position -= menu.getDishList().size() + 1;
            }
        }
        return null;
    }

    public DishMenu getMenuOfMenuByPosition(int position) {
        for (DishMenu menu : mDishMenuList) {
            if (position == 0) return menu;
            if (position > 0 && position <= menu.getDishList().size()) {
                return menu;
            } else {
                position -= menu.getDishList().size() + 1;
            }
        }
        return null;
    }

    public ShoppingCartWatcher getShopCartImp() {
        return shopCartImp;
    }

    public void setShopCartImp(ShoppingCartWatcher shopCartImp) {
        this.shopCartImp = shopCartImp;
    }

    @Override
    public int getItemCount() {
        return mItemCount;
    }

    private class MenuViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout right_menu_layout;
        private TextView right_menu_title;

        public MenuViewHolder(View itemView) {
            super(itemView);
            right_menu_layout = (LinearLayout) itemView.findViewById(R.id.right_menu_item);
            right_menu_title = (TextView) itemView.findViewById(R.id.right_menu_tv);
        }
    }


    class MyItemMenuViewHolder extends RecyclerView.ViewHolder {

        private TextView right_dish_name_tv;
        private TextView right_dish_price_tv;
        private LinearLayout right_dish_layout;
        private ImageView right_dish_remove_iv;
        private ImageView right_dish_add_iv;
        private TextView right_dish_account_tv;

        public MyItemMenuViewHolder(View itemView) {
            super(itemView);
            right_dish_name_tv = (TextView) itemView.findViewById(R.id.right_dish_name);
            right_dish_price_tv = (TextView) itemView.findViewById(R.id.right_dish_price);
            right_dish_layout = (LinearLayout) itemView.findViewById(R.id.right_dish_item);
            right_dish_remove_iv = (ImageView) itemView.findViewById(R.id.right_dish_remove);
            right_dish_add_iv = (ImageView) itemView.findViewById(R.id.right_dish_add);
            right_dish_account_tv = (TextView) itemView.findViewById(R.id.right_dish_account);
        }
    }
}

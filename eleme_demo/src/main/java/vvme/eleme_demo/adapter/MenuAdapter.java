package vvme.eleme_demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vvme.eleme_demo.R;
import vvme.eleme_demo.bean.DishMenu;

/**
 * Created by VV on 2016/11/15.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {


    private Context mContext;
    private List<DishMenu> mDishMenuList;
    private int mSelectedNum;
    private List<MyOnItemSelectedListener> mSelectedListenerList;

    public interface MyOnItemSelectedListener {
        public void onLeftItemSelected(int postion, DishMenu menu);
    }

    public void addItemSelectedListener(MyOnItemSelectedListener listener) {
        if (mSelectedListenerList != null)
            mSelectedListenerList.add(listener);
    }

    public void removeItemSelectedListener(MyOnItemSelectedListener listener) {
        if (mSelectedListenerList != null && !mSelectedListenerList.isEmpty())
            mSelectedListenerList.remove(listener);
    }

    public MenuAdapter(Context context, List<DishMenu> menuBeanList) {
        mContext = context;
        mDishMenuList = menuBeanList;
        this.mSelectedNum = -1;
        this.mSelectedListenerList = new ArrayList<>();
        if (mDishMenuList.size() > 0)
            mSelectedNum = 0;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MenuViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        holder.tvMenuName.setText(mDishMenuList.get(position).getName());
        if(mSelectedNum==position){
            holder.mMenuLayout.setSelected(true);
        }else{
            holder.mMenuLayout.setSelected(false);
        }
    }

    @Override
    public int getItemCount() {
        return mDishMenuList == null ? 0 : mDishMenuList.size();
    }

    public void setSelectedNum(int selectedNum) {
        if(selectedNum<getItemCount() && selectedNum>=0 ) {
            this.mSelectedNum = selectedNum;
            notifyDataSetChanged();
        }
    }

    public int getSelectedNum() {
        return mSelectedNum;
    }


    class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView tvMenuName;
        LinearLayout mMenuLayout;

        public MenuViewHolder(View itemView) {
            super(itemView);
            tvMenuName = (TextView) itemView.findViewById(R.id.tv_menu_name);
            mMenuLayout = (LinearLayout) itemView.findViewById(R.id.left_menu_item);
            mMenuLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notifyItemSelected(getAdapterPosition());
                }
            });
        }
    }

    private void notifyItemSelected(int position) {
        if (mSelectedListenerList != null && !mSelectedListenerList.isEmpty()) {
            for (MyOnItemSelectedListener listener : mSelectedListenerList) {
                listener.onLeftItemSelected(position, mDishMenuList.get(position));
            }
        }
    }
}


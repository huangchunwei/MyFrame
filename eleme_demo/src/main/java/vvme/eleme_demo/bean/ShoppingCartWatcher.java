package vvme.eleme_demo.bean;

import android.view.View;

/**
 * Created by VV on 2016/11/15.
 */

public interface ShoppingCartWatcher {
    void add(View view, int postion);
    void remove(View view, int postion);
}

package vvme.bottomnavigationdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by VV on 2016/11/15.
 */

public class TestActivity extends AppCompatActivity {
    private ListView listView;
    private static Context sContext;
    private static final String[] strs = {"哈哈", "5665666", "你瞅啥", "23333", "瞅你咋地", "大地的花", "范德萨见风使舵", "跟太阳肩并肩", "看好了", "我要开始上天了"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sContext = this;
        setContentView(R.layout.activity_test);
        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(new MyAdapter());
    }


    static class MyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return strs.length;
        }

        @Override
        public Object getItem(int i) {
            return strs[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(sContext).inflate(R.layout.item, null);
            ((TextView)view.findViewById(R.id.tv_name)).setText(strs[i]);
            return view;
        }
    }
}

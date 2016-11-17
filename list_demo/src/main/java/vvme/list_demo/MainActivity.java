package vvme.list_demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements XListView.IXListViewListener {

    private XListView mXListView;
    private List<String> mStrings;
    private Context mContext;
    private MyAdapter mMyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        mXListView = (XListView) findViewById(R.id.xlistview);
        mXListView.setPullLoadEnable(true);
        initData();
        mMyAdapter = new MyAdapter();
        mXListView.setAdapter(mMyAdapter);
        mXListView.setXListViewListener(this);
        mXListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1){
                    mContext.startActivity(new Intent(mContext, TimeTextViewTestActivity.class));
                }
            }
        });
    }

    private void initData() {
        mStrings = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            mStrings.add("测试数据" + i);
        }
    }

    @Override
    public void onRefresh() {
        mXListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mStrings = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    mStrings.add("测试数据" + i);
                }
                mXListView.refreshComplete();
                mMyAdapter.notifyDataSetChanged();
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        mXListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mXListView.loadMoreComplete();
                for (int i = 0; i < 3; i++) {
                    mStrings.add("加载的数据" + i);
                }
                mMyAdapter.notifyDataSetChanged();
            }
        }, 2000);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mStrings.size();
        }

        @Override
        public String getItem(int i) {
            return mStrings.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder vh = null;
            if (view == null) {
                vh = new ViewHolder();
                view = LayoutInflater.from(mContext).inflate(R.layout.item_layout, viewGroup, false);
                vh.mTextView = (TextView) view.findViewById(R.id.tv_item_name);
                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }
            vh.mTextView.setText(mStrings.get(i));
            return view;
        }

        class ViewHolder {
            TextView mTextView;
        }
    }
}

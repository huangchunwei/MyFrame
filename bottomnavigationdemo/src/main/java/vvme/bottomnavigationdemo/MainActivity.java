package vvme.bottomnavigationdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private BottomNavigationView mBottomNavigationView;
    private static final String[] pagers = {"首页", "热点", "我的"};
    private static List<Fragment> sFragments;
    private int currentPosition ;
    private MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sFragments = new ArrayList<>();
        sFragments.add(FragmentOne.newInstance());
        sFragments.add(FragmentTwo.newInstance());
        sFragments.add(FragmentThree.newInstance());
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    mBottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                mBottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = mBottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item_home:
                        if (currentPosition != 0){
                            mViewPager.setCurrentItem(0);
                        }
                        break;
                    case R.id.menu_item_hot:
                        if (currentPosition != 1){
                            mViewPager.setCurrentItem(1);
                        }
                        break;
                    case R.id.menu_item_center:
                        if (currentPosition != 2){
                            mViewPager.setCurrentItem(2);
                        }
                        break;
                }
                return false;
            }
        });
    }

    static class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return sFragments.get(position);
        }

        @Override
        public int getCount() {
            return sFragments.size();
        }
    }

}

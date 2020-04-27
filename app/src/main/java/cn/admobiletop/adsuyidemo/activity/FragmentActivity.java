package cn.admobiletop.adsuyidemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import cn.admobiletop.adsuyidemo.R;
import cn.admobiletop.adsuyidemo.fragment.BannerAdFragment;
import cn.admobiletop.adsuyidemo.fragment.BaseFragment;
import cn.admobiletop.adsuyidemo.fragment.DrawVodAdFragment;
import cn.admobiletop.adsuyidemo.fragment.NativeAdFragment;

/**
 * @author ciba
 * @description 描述
 * @date 2020/4/20
 */
public class FragmentActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        initView();
        initData();
    }

    private void initView() {
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
    }

    private void initData() {
        final List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(new NativeAdFragment());
        fragmentList.add(new BannerAdFragment());
        fragmentList.add(new DrawVodAdFragment());

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return fragmentList.get(position).getTitle();
            }
        });
        tabLayout.setupWithViewPager(viewPager);
    }

}

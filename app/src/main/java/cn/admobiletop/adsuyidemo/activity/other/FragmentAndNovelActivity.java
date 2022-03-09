package cn.admobiletop.adsuyidemo.activity.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import cn.admobiletop.adsuyi.ADSuyiSdk;
import cn.admobiletop.adsuyidemo.R;
import cn.admobiletop.adsuyidemo.activity.base.BaseAdActivity;
import cn.admobiletop.adsuyidemo.fragment.BaseFragment;
import cn.admobiletop.adsuyidemo.fragment.OtherFragment;
import cn.admobiletop.adsuyidemo.fragment.TestFragment;

/**
 * @author ciba
 * @description 将小说放到fragment页面中进行展示案例
 * @date 2020/4/20
 */
public class FragmentAndNovelActivity extends BaseAdActivity {
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
        final List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new TestFragment());
        Fragment novelFragment = ADSuyiSdk.getInstance().getNovelFragment();
        if (novelFragment != null) {
            fragmentList.add(novelFragment);
        }
        fragmentList.add(new OtherFragment());


        viewPager.setOffscreenPageLimit(fragmentList.size() - 1);
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
                Fragment fragment = fragmentList.get(position);
                if (fragment instanceof BaseFragment) {
                    return ((BaseFragment) fragment).getTitle();
                }
                return "小说内容";
            }
        });
        tabLayout.setupWithViewPager(viewPager);
    }

}

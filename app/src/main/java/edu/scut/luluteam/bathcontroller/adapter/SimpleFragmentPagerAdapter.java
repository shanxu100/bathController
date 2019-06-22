package edu.scut.luluteam.bathcontroller.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author Guan
 * @date Created on 2019/4/24
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> pagerList;

    List<String> titleList;

    public SimpleFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList) {
        super(fm);
        this.pagerList = fragmentList;
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return pagerList.get(position);
    }

    @Override
    public int getCount() {
        return pagerList != null ? pagerList.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //通过自定义ItemView来显示
        return titleList.get(position);
    }


}

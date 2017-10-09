package com.my.zhou.group.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.my.zhou.group.fragment.NewWorkFragment;
import com.my.zhou.group.fragment.PutFragment;
import com.my.zhou.group.fragment.TakeFragment;
import com.my.zhou.group.fragment.WaitAgainFragment;
import com.my.zhou.group.fragment.WaitSendFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/3/2.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments.add(new NewWorkFragment());
        fragments.add(new WaitAgainFragment());
        fragments.add(new WaitSendFragment());
    }

    List<Fragment> fragments = new ArrayList<Fragment>();

    public void addFragment(Fragment fragment) {
//        if(fragment!=null){
////            this.fragments.add(fragment);
//        fragments.add(new NewWorkFragment());
//        fragments.add(new PutFragment());
//        fragments.add(new TakeFragment());
//        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}

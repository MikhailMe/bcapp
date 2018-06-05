package jp.co.soramitsu.iroha.android.sample.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;
import java.util.ArrayList;

import lombok.NonNull;

public class Adapter extends FragmentPagerAdapter {

    private final List<String> mTitles = new ArrayList<>();
    private final List<Fragment> mFragments = new ArrayList<>();

    Adapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    void addFragment(@NonNull Fragment fragment,
                     @NonNull String title) {
        mFragments.add(fragment);
        mTitles.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}

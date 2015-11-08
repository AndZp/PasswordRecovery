package ua.com.ukrelektro.passwordrec;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by User on 08.11.2015.
 */
public class TabsPagerFragmentAdapter extends FragmentPagerAdapter {
    private String[] tabsArr;

    public TabsPagerFragmentAdapter(FragmentManager fm) {
        super(fm);
        getTabs();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabsArr[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TabFragment.getInstanse();
            case 1:
                return TabFragment.getInstanse();
            case 2:
                return TabFragment.getInstanse();
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabsArr.length;
    }

    private void getTabs() {
        tabsArr = new String[]{"Checker", "History"};
    }
}
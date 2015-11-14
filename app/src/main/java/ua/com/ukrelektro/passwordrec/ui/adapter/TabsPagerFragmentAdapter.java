package ua.com.ukrelektro.passwordrec.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ua.com.ukrelektro.passwordrec.ui.fragment.CheckerFragment;
import ua.com.ukrelektro.passwordrec.ui.fragment.HistoryFragment;

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
                return CheckerFragment.getInstance();
            case 1:
                return HistoryFragment.getInstanse();

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
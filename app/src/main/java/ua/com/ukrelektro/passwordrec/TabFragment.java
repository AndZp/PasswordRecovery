package ua.com.ukrelektro.passwordrec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class TabFragment extends Fragment {
    public static final int LAYOUT = R.layout.tab_fragment;
    private View view;


    public static TabFragment getInstanse() {
        Bundle args = new Bundle();
        TabFragment fragment = new TabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(LAYOUT, container, false);
        return view;
    }
}

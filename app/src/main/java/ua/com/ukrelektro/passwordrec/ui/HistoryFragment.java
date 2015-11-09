package ua.com.ukrelektro.passwordrec.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.com.ukrelektro.passwordrec.R;


public class HistoryFragment extends Fragment {
    public static final int LAYOUT = R.layout.history_fragment;
    private View view;


    public static HistoryFragment getInstanse() {
        Bundle args = new Bundle();
        HistoryFragment fragment = new HistoryFragment();
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

package ua.com.ukrelektro.passwordrec.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.com.ukrelektro.passwordrec.R;
import ua.com.ukrelektro.passwordrec.ui.adapter.RecyclerViewAdapter;


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

        initRecyclerView();

        return view;
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(itemAnimator);
    }
}

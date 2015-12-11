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

import butterknife.Bind;
import butterknife.ButterKnife;
import ua.com.ukrelektro.passwordrec.R;
import ua.com.ukrelektro.passwordrec.ui.adapter.RecyclerViewAdapter;


public class HistoryFragment extends Fragment {
    public static final int LAYOUT = R.layout.history_fragment;
    private View view;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    private RecyclerViewAdapter adapter;


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
        ButterKnife.bind(this, view);
        initRecyclerView();

        return view;
    }



    private void initRecyclerView() {

        adapter = new RecyclerViewAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(itemAnimator);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void updateRV() {
        this.adapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
    }

}

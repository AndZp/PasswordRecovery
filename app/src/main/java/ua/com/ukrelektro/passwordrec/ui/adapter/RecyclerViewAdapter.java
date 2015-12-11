package ua.com.ukrelektro.passwordrec.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ua.com.ukrelektro.passwordrec.R;
import ua.com.ukrelektro.passwordrec.model.Code;
import ua.com.ukrelektro.passwordrec.model.State;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Code> historyList;


    public RecyclerViewAdapter() {
        this.historyList = new ArrayList<Code>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Code code = historyList.get(position);

        holder.code.setText(String.format("%04d", code.getCode()));

        holder.time.setText("Tested on " + new SimpleDateFormat("dd.MM.yyyy HH:mm").format(code.getDate()));

        int icon = code.getStatus() == State.PASS ? R.drawable.ic_check_circle : R.drawable.ic_highlight_remove;
        holder.icon.setImageResource(icon);
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvCodeItemHistory) TextView code;
        @Bind(R.id.tvTimeItemHistory) TextView time;
        @Bind(R.id.imageView) ImageView icon;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

package ua.com.ukrelektro.passwordrec.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import ua.com.ukrelektro.passwordrec.R;
import ua.com.ukrelektro.passwordrec.control.CodeChecker;
import ua.com.ukrelektro.passwordrec.model.Code;
import ua.com.ukrelektro.passwordrec.model.Status;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Code> historyList;


    public RecyclerViewAdapter() {
        this.historyList = CodeChecker.getHistoryList();
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

        int icon = code.getStatus() == Status.PASS ? R.drawable.ic_check_circle : R.drawable.ic_highlight_remove;
        holder.icon.setImageResource(icon);
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView code;
        private TextView time;
        private ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            this.code = (TextView) itemView.findViewById(R.id.tvCodeItemHistory);
            this.time = (TextView) itemView.findViewById(R.id.tvTimeItemHistory);
            this.icon = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}

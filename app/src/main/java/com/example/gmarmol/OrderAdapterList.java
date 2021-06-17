package com.example.gmarmol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OrderAdapterList extends RecyclerView.Adapter<OrderAdapterList.ViewHolder>{

    private List<Order> mData;
    private LayoutInflater mInflater;
    private Context context;
    private RecyclerViewClick listener;

    public OrderAdapterList(List<Order> mData, Context context, RecyclerViewClick listener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = mData;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public int getItemCount() { return mData.size(); }

    @Override
    public OrderAdapterList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.cardview_item, null);
        return new OrderAdapterList.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final OrderAdapterList.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));
    }

    public void setItems(List<Order> items) {
        mData = items;
    }

    public interface RecyclerViewClick {
        void onCLick(View v, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView docNum, docDate;

        ViewHolder(View itemView) {
            super(itemView);
            docNum = itemView.findViewById(R.id.doc_num);
            docDate = itemView.findViewById(R.id.doc_date);
            itemView.setOnClickListener(this);
        }

        void bindData(final Order item) {
            docNum.setText(Integer.toString(item.getDocNum()));
            docDate.setText(item.getDocDate());
        }

        @Override
        public void onClick(View v) {
            listener.onCLick(v, getAdapterPosition());
        }
    }
}

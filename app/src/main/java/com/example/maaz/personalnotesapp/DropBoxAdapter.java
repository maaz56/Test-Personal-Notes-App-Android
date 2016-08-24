package com.example.maaz.personalnotesapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by maaz on 8/8/16.
 */
public class DropBoxAdapter extends RecyclerView.Adapter<DropBoxAdapter.RecyclerViewHolder> {
    private LayoutInflater mInflater;
    private List<String> mData = Collections.emptyList();

    public DropBoxAdapter(Context context, List<String> mData) {
        mInflater = LayoutInflater.from(context);
        this.mData = mData;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.custom_dbx_adapter_layout, viewGroup, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.title.setText(mData.get(position));
    }

    public void setData(List<String> data) {
        this.mData = data;
    }

    public void add(String dirName) {
        mData.add(dirName);
        Collections.sort(mData, String.CASE_INSENSITIVE_ORDER);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView title;


        public RecyclerViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.drop_box_directory_name);
        }
    }
}

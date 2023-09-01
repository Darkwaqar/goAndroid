package com.growonline.gomobishop.control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.model.ConversationsModel;

import java.util.List;

public class ConversationRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int DATE = 0, YOU = 1, ME = 2;
    // The items to display in your RecyclerView
    private List<ConversationsModel> items;
    private Context mContext;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ConversationRecyclerView(Context context, List<ConversationsModel> items) {
        this.mContext = context;
        this.items = items;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public int getItemViewType(int position) {
        //More to come
//        if (items.get(position).getAlignLeft()) {
//            return DATE;
//        } else
        if (items.get(position).getAlignLeft()) {
            return YOU;
        } else if (!items.get(position).getAlignLeft()) {
            return ME;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case DATE:
                View v1 = inflater.inflate(R.layout.layout_holder_date, viewGroup, false);
                viewHolder = new HolderDate(v1);
                break;
            case YOU:
                View v2 = inflater.inflate(R.layout.layout_holder_you, viewGroup, false);
                viewHolder = new HolderYou(v2);
                break;
            default:
                View v = inflater.inflate(R.layout.layout_holder_me, viewGroup, false);
                viewHolder = new HolderMe(v);
                break;
        }
        return viewHolder;
    }

    public void addItem(List<ConversationsModel> item) {
        items.addAll(item);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case DATE:
                HolderDate vh1 = (HolderDate) viewHolder;
                configureViewHolder1(vh1, position);
                break;
            case YOU:
                HolderYou vh2 = (HolderYou) viewHolder;
                configureViewHolder2(vh2, position);
                break;
            default:
                HolderMe vh = (HolderMe) viewHolder;
                configureViewHolder3(vh, position);
                break;
        }
    }

    private void configureViewHolder3(HolderMe vh1, int position) {
        vh1.getTime().setText(GoMobileApp.ConvertUtcToTime(items.get(position).getCreatedOn()));
        vh1.getChatText().setText(items.get(position).getMessage());
    }

    private void configureViewHolder2(HolderYou vh1, int position) {
        vh1.getTime().setText(GoMobileApp.ConvertUtcToTime(items.get(position).getCreatedOn()));
        vh1.getChatText().setText(items.get(position).getMessage());
    }

    private void configureViewHolder1(HolderDate vh1, int position) {
        vh1.getDate().setText(GoMobileApp.ConvertUtcToTime(items.get(position).getCreatedOn()));
    }

}
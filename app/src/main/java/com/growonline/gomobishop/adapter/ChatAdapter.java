package com.growonline.gomobishop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.model.InboxModel;

import java.util.List;

public class ChatAdapter extends SelectableAdapter<ChatAdapter.ViewHolder> {

    private List<InboxModel> mArrayList;
    private Context mContext;
    private ViewHolder.ClickListener clickListener;


    public ChatAdapter(Context context, List<InboxModel> arrayList, ViewHolder.ClickListener clickListener) {
        this.mArrayList = arrayList;
        this.mContext = context;
        this.clickListener = clickListener;

    }

    // Create new views
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item_chat, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView, clickListener);

        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        if (mArrayList.get(position).getAlignLeft()) {
            viewHolder.tvName.setText(mArrayList.get(position).getCustomerFromName());
        } else {
            viewHolder.tvName.setText(mArrayList.get(position).getCustomerToName());
        }

        if (isSelected(position)) {
            viewHolder.checked.setChecked(true);
            viewHolder.checked.setVisibility(View.VISIBLE);
        } else {
            viewHolder.checked.setChecked(false);
            viewHolder.checked.setVisibility(View.GONE);
        }
        viewHolder.tvTime.setText(GoMobileApp.ConvertUtcToTime(mArrayList.get(position).getCreatedOn()));
//        viewHolder.userPhoto.setImageResource(mArrayList.get(position).getImage());
        if (mArrayList.get(position).getOnline()) {
            viewHolder.onlineView.setVisibility(View.VISIBLE);
        } else
            viewHolder.onlineView.setVisibility(View.INVISIBLE);

        viewHolder.tvLastChat.setText(mArrayList.get(position).getMessage());
        viewHolder.totalChat.setText(mArrayList.get(position).getTotalUnread().toString());
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private final View onlineView;
        public TextView tvName;
        public TextView totalChat;
        public TextView tvTime;
        public TextView tvLastChat;
        public ImageView userPhoto;
        public boolean online = false;
        public CheckBox checked;
        private ClickListener listener;
        //private final View selectedOverlay;


        public ViewHolder(View itemLayoutView, ClickListener listener) {
            super(itemLayoutView);

            this.listener = listener;

            tvName = (TextView) itemLayoutView.findViewById(R.id.tv_user_name);
            totalChat = (TextView) itemLayoutView.findViewById(R.id.total_chat);
            //selectedOverlay = (View) itemView.findViewById(R.id.selected_overlay);
            tvTime = (TextView) itemLayoutView.findViewById(R.id.tv_time);
            tvLastChat = (TextView) itemLayoutView.findViewById(R.id.tv_last_chat);
            userPhoto = (ImageView) itemLayoutView.findViewById(R.id.iv_user_photo);
            onlineView = (View) itemLayoutView.findViewById(R.id.online_indicator);
            checked = (CheckBox) itemLayoutView.findViewById(R.id.chk_list);

            itemLayoutView.setOnClickListener(this);

            itemLayoutView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClicked(getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (listener != null) {
                return listener.onItemLongClicked(getAdapterPosition());
            }
            return false;
        }

        public interface ClickListener {
            public void onItemClicked(int position);

            public boolean onItemLongClicked(int position);

            boolean onCreateOptionsMenu(Menu menu);
        }
    }
}
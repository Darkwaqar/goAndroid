package com.growonline.gomobishop.adapter;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.R;
import com.growonline.gomobishop.control.DetectHtml;
import com.growonline.gomobishop.control.ExpandableLayout;
import com.growonline.gomobishop.control.ExpandableLayoutListenerAdapter;
import com.growonline.gomobishop.control.ExpandableLinearLayout;
import com.growonline.gomobishop.model.ExpendableModel;

import java.util.List;

/**
 * Created by asifrizvi on 3/25/2019.
 */

public class ExpendableRecyclerAdapter extends RecyclerView.Adapter<ExpendableRecyclerAdapter.ViewHolder> {

    private final List<ExpendableModel> data;
    private Context context;
    private SparseBooleanArray expandState = new SparseBooleanArray();

    public ExpendableRecyclerAdapter(final List<ExpendableModel> data) {
        this.data = data;
        for (int i = 0; i < data.size(); i++) {
            expandState.append(i, data.get(i).Expended);
        }
    }

    public static String toTitleCase(String str) {

        if (str == null) {
            return null;
        }

        str = str.trim();
        boolean space = true;
        StringBuilder builder = new StringBuilder(str);
        final int len = builder.length();

        for (int i = 0; i < len; ++i) {
            char c = builder.charAt(i);
            if (space) {
                if (!Character.isWhitespace(c)) {
                    // Convert to title case and switch out of whitespace mode.
                    builder.setCharAt(i, Character.toTitleCase(c));
                    space = false;
                }
            } else if (Character.isWhitespace(c)) {
                space = true;
            } else {
                builder.setCharAt(i, Character.toLowerCase(c));
            }
        }

        return builder.toString();
    }

    public static String ToSentenceCase(String str) {

        if (str == null) return null;
        str = str.trim();
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.expendible_list_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ExpendableModel item = data.get(position);
        if (item.getName() == null || item.getValue() == null) {
            holder.buttonLayout.setVisibility(View.GONE);
            holder.expandableLayout.setVisibility(View.GONE);
            holder.title.setVisibility(View.GONE);
            return;
        }
        holder.setIsRecyclable(false);
        holder.title.setText(item.getName());
        if (item.Expended)
            holder.indicator.setImageResource(R.drawable.ic_remove_black);
        else
            holder.indicator.setImageResource(R.drawable.ic_add_black);


        if (DetectHtml.isHtml(item.getValue())) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                holder.description.setText(Html.fromHtml(ToSentenceCase(item.getValue()), Html.FROM_HTML_MODE_COMPACT));
            } else {
                holder.description.setText(Html.fromHtml(ToSentenceCase(item.getValue())));
            }
            holder.description.setMovementMethod(com.growonline.gomobishop.control.LinkMovementMethod.getInstance());
        } else {
            holder.description.setText(ToSentenceCase(item.getValue()));
        }

        holder.expandableLayout.setInRecyclerView(true);
        holder.expandableLayout.setExpanded(expandState.get(position));
        holder.expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                expandState.put(position, true);
                holder.indicator.setImageResource(R.drawable.ic_remove_black);
            }

            @Override
            public void onPreClose() {
                expandState.put(position, false);
                holder.indicator.setImageResource(R.drawable.ic_add_black);
            }
        });

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onClickButton(holder.expandableLayout);
            }
        });
    }

    private void onClickButton(final ExpandableLayout expandableLayout) {
        expandableLayout.toggle();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView description;
        public RelativeLayout buttonLayout;
        public ImageView indicator;
        /**
         * You must use the ExpandableLinearLayout in the recycler view.
         * The ExpandableRelativeLayout doesn't work.
         */
        public ExpandableLinearLayout expandableLayout;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            description = (TextView) v.findViewById(R.id.description);
            buttonLayout = (RelativeLayout) v.findViewById(R.id.button);
            expandableLayout = (ExpandableLinearLayout) v.findViewById(R.id.expandableLayout);
            indicator = (ImageView) v.findViewById(R.id.indicator);
        }
    }
}
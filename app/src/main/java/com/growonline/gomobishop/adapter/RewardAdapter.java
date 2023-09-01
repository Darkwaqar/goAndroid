package com.growonline.gomobishop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.R;
import com.growonline.gomobishop.model.RewardPointsHistory;

import java.util.List;

/**
 * Created by asifrizvi on 10/1/2018.
 */

public class RewardAdapter  extends RecyclerView.Adapter<RewardAdapter.MyViewHolder> {

    private List<RewardPointsHistory> rewardPoints;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Points, Message, PointsBalance;

        public MyViewHolder(View view) {
            super(view);
            Points = (TextView) view.findViewById(R.id.points);
            Message = (TextView) view.findViewById(R.id.message);
            PointsBalance = (TextView) view.findViewById(R.id.point_balance);
        }
    }


    public RewardAdapter(List<RewardPointsHistory> rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.loyality_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RewardPointsHistory rewardPoint = rewardPoints.get(position);
        holder.Points.setText(String.valueOf(rewardPoint.getPoints()));
        holder.Message.setText(rewardPoint.getMessage());
        holder.PointsBalance.setText(String.valueOf(rewardPoint.getPoints()));
    }

    @Override
    public int getItemCount() {
        return rewardPoints.size();
    }
}

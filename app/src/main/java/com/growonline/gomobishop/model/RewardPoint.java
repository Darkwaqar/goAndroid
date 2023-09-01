package com.growonline.gomobishop.model;

/**
 * Created by asifrizvi on 10/1/2018.
 */

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RewardPoint implements Parcelable
{

    @SerializedName("RewardPointsHistory")
    @Expose
    private List<RewardPointsHistory> rewardPointsHistory = null;
    @SerializedName("RewardPointsBalance")
    @Expose
    private Integer rewardPointsBalance;
    @SerializedName("rewardPointsAmount")
    @Expose
    private String rewardPointsAmount;
    @SerializedName("MinimumRewardPointsToUse")
    @Expose
    private Integer minimumRewardPointsToUse;
    @SerializedName("MinimumRewardPointsToUseAmount")
    @Expose
    private String minimumRewardPointsToUseAmount;
    public final static Parcelable.Creator<RewardPoint> CREATOR = new Creator<RewardPoint>() {


        @SuppressWarnings({
                "unchecked"
        })
        public RewardPoint createFromParcel(Parcel in) {
            return new RewardPoint(in);
        }

        public RewardPoint[] newArray(int size) {
            return (new RewardPoint[size]);
        }

    }
            ;

    protected RewardPoint(Parcel in) {
        in.readList(this.rewardPointsHistory, (RewardPointsHistory.class.getClassLoader()));
        this.rewardPointsBalance = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.rewardPointsAmount = ((String) in.readValue((String.class.getClassLoader())));
        this.minimumRewardPointsToUse = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.minimumRewardPointsToUseAmount = ((String) in.readValue((String.class.getClassLoader())));
    }

    public RewardPoint() {
    }

    public List<RewardPointsHistory> getRewardPointsHistory() {
        return rewardPointsHistory;
    }

    public void setRewardPointsHistory(List<RewardPointsHistory> rewardPointsHistory) {
        this.rewardPointsHistory = rewardPointsHistory;
    }

    public Integer getRewardPointsBalance() {
        return rewardPointsBalance;
    }

    public void setRewardPointsBalance(Integer rewardPointsBalance) {
        this.rewardPointsBalance = rewardPointsBalance;
    }

    public String getRewardPointsAmount() {
        return rewardPointsAmount;
    }

    public void setRewardPointsAmount(String rewardPointsAmount) {
        this.rewardPointsAmount = rewardPointsAmount;
    }

    public Integer getMinimumRewardPointsToUse() {
        return minimumRewardPointsToUse;
    }

    public void setMinimumRewardPointsToUse(Integer minimumRewardPointsToUse) {
        this.minimumRewardPointsToUse = minimumRewardPointsToUse;
    }

    public String getMinimumRewardPointsToUseAmount() {
        return minimumRewardPointsToUseAmount;
    }

    public void setMinimumRewardPointsToUseAmount(String minimumRewardPointsToUseAmount) {
        this.minimumRewardPointsToUseAmount = minimumRewardPointsToUseAmount;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(rewardPointsHistory);
        dest.writeValue(rewardPointsBalance);
        dest.writeValue(rewardPointsAmount);
        dest.writeValue(minimumRewardPointsToUse);
        dest.writeValue(minimumRewardPointsToUseAmount);
    }

    public int describeContents() {
        return 0;
    }

}
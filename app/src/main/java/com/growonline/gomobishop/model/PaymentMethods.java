package com.growonline.gomobishop.model;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentMethods implements Parcelable
{

    @SerializedName("PaymentMethods")
    @Expose
    private List<PaymentMethod> paymentMethods = null;
    @SerializedName("DisplayRewardPoints")
    @Expose
    private Boolean displayRewardPoints;
    @SerializedName("RewardPointsBalance")
    @Expose
    private Integer rewardPointsBalance;
    @SerializedName("RewardPointsAmount")
    @Expose
    private String rewardPointsAmount;
    @SerializedName("RewardPointsEnoughToPayForOrder")
    @Expose
    private Boolean rewardPointsEnoughToPayForOrder;
    @SerializedName("UseRewardPoints")
    @Expose
    private Boolean useRewardPoints;

    public final static Parcelable.Creator<PaymentMethods> CREATOR = new Creator<PaymentMethods>() {


        @SuppressWarnings({
                "unchecked"
        })
        public PaymentMethods createFromParcel(Parcel in) {
            return new PaymentMethods(in);
        }

        public PaymentMethods[] newArray(int size) {
            return (new PaymentMethods[size]);
        }

    }
            ;

    protected PaymentMethods(Parcel in) {
        in.readList(this.paymentMethods, (PaymentMethod.class.getClassLoader()));
        this.displayRewardPoints = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.rewardPointsBalance = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.rewardPointsAmount = ((String) in.readValue((String.class.getClassLoader())));
        this.rewardPointsEnoughToPayForOrder = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.useRewardPoints = ((Boolean) in.readValue((Boolean.class.getClassLoader())));

    }

    public PaymentMethods() {
    }

    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public Boolean getDisplayRewardPoints() {
        return displayRewardPoints;
    }

    public void setDisplayRewardPoints(Boolean displayRewardPoints) {
        this.displayRewardPoints = displayRewardPoints;
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

    public Boolean getRewardPointsEnoughToPayForOrder() {
        return rewardPointsEnoughToPayForOrder;
    }

    public void setRewardPointsEnoughToPayForOrder(Boolean rewardPointsEnoughToPayForOrder) {
        this.rewardPointsEnoughToPayForOrder = rewardPointsEnoughToPayForOrder;
    }

    public Boolean getUseRewardPoints() {
        return useRewardPoints;
    }

    public void setUseRewardPoints(Boolean useRewardPoints) {
        this.useRewardPoints = useRewardPoints;
    }



    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(paymentMethods);
        dest.writeValue(displayRewardPoints);
        dest.writeValue(rewardPointsBalance);
        dest.writeValue(rewardPointsAmount);
        dest.writeValue(rewardPointsEnoughToPayForOrder);
        dest.writeValue(useRewardPoints);
    }

    public int describeContents() {
        return 0;
    }

}
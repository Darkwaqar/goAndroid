package com.growonline.gomobishop.model;

/**
 * Created by asifrizvi on 2/22/2019.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewSummaryModel implements Parcelable {

    @SerializedName("TotalReviews")
    @Expose
    private Integer totalReviews;
    @SerializedName("RatingCounts")
    @Expose
    private List<Integer> ratingCounts = null;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("Reviews")
    @Expose
    private List<Review> reviews = null;
    public final static Parcelable.Creator<ReviewSummaryModel> CREATOR = new Creator<ReviewSummaryModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ReviewSummaryModel createFromParcel(Parcel in) {
            return new ReviewSummaryModel(in);
        }

        public ReviewSummaryModel[] newArray(int size) {
            return (new ReviewSummaryModel[size]);
        }

    };

    protected ReviewSummaryModel(Parcel in) {
        this.totalReviews = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.ratingCounts, (java.lang.Integer.class.getClassLoader()));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.reviews, (Review.class.getClassLoader()));
    }

    public ReviewSummaryModel() {
    }

    public Integer getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(Integer totalReviews) {
        this.totalReviews = totalReviews;
    }

    public List<Integer> getRatingCounts() {
        return ratingCounts;
    }

    public void setRatingCounts(List<Integer> ratingCounts) {
        this.ratingCounts = ratingCounts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(totalReviews);
        dest.writeList(ratingCounts);
        dest.writeValue(name);
        dest.writeValue(image);
        dest.writeList(reviews);
    }

    public int describeContents() {
        return 0;
    }

}
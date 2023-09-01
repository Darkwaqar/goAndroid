package com.growonline.gomobishop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SocialLinks implements Parcelable
{

    public final static Parcelable.Creator<SocialLinks> CREATOR = new Creator<SocialLinks>() {


        @SuppressWarnings({
                "unchecked"
        })
        public SocialLinks createFromParcel(Parcel in) {
            return new SocialLinks(in);
        }

        public SocialLinks[] newArray(int size) {
            return (new SocialLinks[size]);
        }

    };
    @SerializedName("VendorId")
    @Expose
    private Integer vendorId;
    @SerializedName("InstagramMobile")
    @Expose
    private String instagramMobile;
    @SerializedName("InstagramWebURL")
    @Expose
    private String instagramWebURL;
    @SerializedName("FaceboolMobile")
    @Expose
    private String faceboolMobile;
    @SerializedName("FaceboolWebURL")
    @Expose
    private String faceboolWebURL;
    @SerializedName("TwitterMobile")
    @Expose
    private String twitterMobile;
    @SerializedName("TwitterWebURL")
    @Expose
    private String twitterWebURL;
    @SerializedName("LinkedInMobile")
    @Expose
    private String linkedInMobile;
    @SerializedName("LinkedWebURL")
    @Expose
    private String linkedWebURL;
    @SerializedName("YoutubeMobile")
    @Expose
    private String youtubeMobile;
    @SerializedName("YoutubeWebURL")
    @Expose
    private String youtubeWebURL;
    @SerializedName("WhatsappMobile")
    @Expose
    private String whatsappMobile;
    @SerializedName("WhatsappWebURL")
    @Expose
    private String whatsappWebURL;
    @SerializedName("Id")
    @Expose
    private Integer id;

    protected SocialLinks(Parcel in) {
        this.vendorId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.instagramMobile = ((String) in.readValue((Object.class.getClassLoader())));
        this.instagramWebURL = ((String) in.readValue((Object.class.getClassLoader())));
        this.faceboolMobile = ((String) in.readValue((Object.class.getClassLoader())));
        this.faceboolWebURL = ((String) in.readValue((Object.class.getClassLoader())));
        this.twitterMobile = ((String) in.readValue((Object.class.getClassLoader())));
        this.twitterWebURL = ((String) in.readValue((Object.class.getClassLoader())));
        this.linkedInMobile = ((String) in.readValue((Object.class.getClassLoader())));
        this.linkedWebURL = ((String) in.readValue((Object.class.getClassLoader())));
        this.youtubeMobile = ((String) in.readValue((Object.class.getClassLoader())));
        this.youtubeWebURL = ((String) in.readValue((Object.class.getClassLoader())));
        this.whatsappMobile = ((String) in.readValue((Object.class.getClassLoader())));
        this.whatsappWebURL = ((String) in.readValue((Object.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public SocialLinks() {
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getInstagramMobile() {
        return instagramMobile;
    }

    public void setInstagramMobile(String instagramMobile) {
        this.instagramMobile = instagramMobile;
    }

    public String getInstagramWebURL() {
        return instagramWebURL;
    }

    public void setInstagramWebURL(String instagramWebURL) {
        this.instagramWebURL = instagramWebURL;
    }

    public String getFaceboolMobile() {
        return faceboolMobile;
    }

    public void setFaceboolMobile(String faceboolMobile) {
        this.faceboolMobile = faceboolMobile;
    }

    public String getFaceboolWebURL() {
        return faceboolWebURL;
    }

    public void setFaceboolWebURL(String faceboolWebURL) {
        this.faceboolWebURL = faceboolWebURL;
    }

    public String getTwitterMobile() {
        return twitterMobile;
    }

    public void setTwitterMobile(String twitterMobile) {
        this.twitterMobile = twitterMobile;
    }

    public String getTwitterWebURL() {
        return twitterWebURL;
    }

    public void setTwitterWebURL(String twitterWebURL) {
        this.twitterWebURL = twitterWebURL;
    }

    public String getLinkedInMobile() {
        return linkedInMobile;
    }

    public void setLinkedInMobile(String linkedInMobile) {
        this.linkedInMobile = linkedInMobile;
    }

    public String getLinkedWebURL() {
        return linkedWebURL;
    }

    public void setLinkedWebURL(String linkedWebURL) {
        this.linkedWebURL = linkedWebURL;
    }

    public String getYoutubeMobile() {
        return youtubeMobile;
    }

    public void setYoutubeMobile(String youtubeMobile) {
        this.youtubeMobile = youtubeMobile;
    }

    public String getYoutubeWebURL() {
        return youtubeWebURL;
    }

    public void setYoutubeWebURL(String youtubeWebURL) {
        this.youtubeWebURL = youtubeWebURL;
    }

    public String getWhatsappMobile() {
        return whatsappMobile;
    }

    public void setWhatsappMobile(String whatsappMobile) {
        this.whatsappMobile = whatsappMobile;
    }

    public String getWhatsappWebURL() {
        return whatsappWebURL;
    }

    public void setWhatsappWebURL(String whatsappWebURL) {
        this.whatsappWebURL = whatsappWebURL;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(vendorId);
        dest.writeValue(instagramMobile);
        dest.writeValue(instagramWebURL);
        dest.writeValue(faceboolMobile);
        dest.writeValue(faceboolWebURL);
        dest.writeValue(twitterMobile);
        dest.writeValue(twitterWebURL);
        dest.writeValue(linkedInMobile);
        dest.writeValue(linkedWebURL);
        dest.writeValue(youtubeMobile);
        dest.writeValue(youtubeWebURL);
        dest.writeValue(whatsappMobile);
        dest.writeValue(whatsappWebURL);
        dest.writeValue(id);

    }

    public int describeContents() {
        return 0;
    }

}
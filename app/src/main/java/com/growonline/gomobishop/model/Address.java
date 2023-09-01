package com.growonline.gomobishop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Address implements Parcelable {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Company")
    @Expose
    private String company;
    @SerializedName("CountryId")
    @Expose
    private Integer countryId;
    @SerializedName("CountryName")
    @Expose
    private String countryName;
    @SerializedName("StateProvinceId")
    @Expose
    private Integer stateProvinceId;
    @SerializedName("StateProvinceName")
    @Expose
    private String stateProvinceName;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("Address1")
    @Expose
    private String address1;
    @SerializedName("Address2")
    @Expose
    private String address2;
    @SerializedName("ZipPostalCode")
    @Expose
    private String zipPostalCode;
    @SerializedName("PhoneNumber")
    @Expose
    private String phoneNumber;

    public Address() {
    }


    protected Address(Parcel in) {
        this.firstName = ((String) in.readValue((String.class.getClassLoader())));
        this.lastName = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.company = ((String) in.readValue((String.class.getClassLoader())));
        this.countryId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.countryName = ((String) in.readValue((String.class.getClassLoader())));
        this.stateProvinceId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.stateProvinceName = ((String) in.readValue((String.class.getClassLoader())));
        this.city = ((String) in.readValue((String.class.getClassLoader())));
        this.address1 = ((String) in.readValue((String.class.getClassLoader())));
        this.address2 = ((String) in.readValue((String.class.getClassLoader())));
        this.zipPostalCode = ((String) in.readValue((String.class.getClassLoader())));
        this.phoneNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(firstName);
        dest.writeValue(lastName);
        dest.writeValue(email);
        dest.writeValue(company);
        dest.writeValue(countryId);
        dest.writeValue(countryName);
        dest.writeValue(stateProvinceId);
        dest.writeValue(stateProvinceName);
        dest.writeValue(city);
        dest.writeValue(address1);
        dest.writeValue(address2);
        dest.writeValue(zipPostalCode);
        dest.writeValue(phoneNumber);
        dest.writeValue(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Integer getStateProvinceId() {
        return stateProvinceId;
    }

    public void setStateProvinceId(Integer stateProvinceId) {
        this.stateProvinceId = stateProvinceId;
    }

    public String getStateProvinceName() {
        return stateProvinceName;
    }

    public void setStateProvinceName(String stateProvinceName) {
        this.stateProvinceName = stateProvinceName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getZipPostalCode() {
        return zipPostalCode;
    }

    public void setZipPostalCode(String zipPostalCode) {
        this.zipPostalCode = zipPostalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


}



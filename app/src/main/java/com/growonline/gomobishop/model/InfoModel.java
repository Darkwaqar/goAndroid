package com.growonline.gomobishop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InfoModel implements Parcelable {

    @SerializedName("Email")
    @Expose
    private String email;
    public final static Parcelable.Creator<InfoModel> CREATOR = new Creator<InfoModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public InfoModel createFromParcel(Parcel in) {
            return new InfoModel(in);
        }

        public InfoModel[] newArray(int size) {
            return (new InfoModel[size]);
        }

    };
    @SerializedName("CheckUsernameAvailabilityEnabled")
    @Expose
    private Boolean checkUsernameAvailabilityEnabled;
    @SerializedName("AllowUsersToChangeUsernames")
    @Expose
    private Boolean allowUsersToChangeUsernames;
    @SerializedName("UsernamesEnabled")
    @Expose
    private Boolean usernamesEnabled;
    @SerializedName("Username")
    @Expose
    private String username;
    @SerializedName("GenderEnabled")
    @Expose
    private Boolean genderEnabled;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("EmailToRevalidate")
    @Expose
    private String emailToRevalidate;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("FirstNameEnabled")
    @Expose
    private Boolean firstNameEnabled;
    @SerializedName("FirstNameRequired")
    @Expose
    private Boolean firstNameRequired;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("LastNameEnabled")
    @Expose
    private Boolean lastNameEnabled;
    @SerializedName("DateOfBirthEnabled")
    @Expose
    private Boolean dateOfBirthEnabled;
    @SerializedName("DateOfBirthDay")
    @Expose
    private Integer dateOfBirthDay;
    @SerializedName("DateOfBirthMonth")
    @Expose
    private Integer dateOfBirthMonth;
    @SerializedName("DateOfBirthYear")
    @Expose
    private Integer dateOfBirthYear;
    @SerializedName("DateOfBirthRequired")
    @Expose
    private Boolean dateOfBirthRequired;
    @SerializedName("CompanyEnabled")
    @Expose
    private Boolean companyEnabled;
    @SerializedName("CompanyRequired")
    @Expose
    private Boolean companyRequired;
    @SerializedName("Company")
    @Expose
    private String company;
    @SerializedName("StreetAddressEnabled")
    @Expose
    private Boolean streetAddressEnabled;
    @SerializedName("StreetAddressRequired")
    @Expose
    private Boolean streetAddressRequired;
    @SerializedName("StreetAddress")
    @Expose
    private String streetAddress;
    @SerializedName("StreetAddress2Enabled")
    @Expose
    private Boolean streetAddress2Enabled;
    @SerializedName("StreetAddress2Required")
    @Expose
    private Boolean streetAddress2Required;
    @SerializedName("LastNameRequired")
    @Expose
    private Boolean lastNameRequired;
    @SerializedName("ZipPostalCodeEnabled")
    @Expose
    private Boolean zipPostalCodeEnabled;
    @SerializedName("ZipPostalCodeRequired")
    @Expose
    private Boolean zipPostalCodeRequired;
    @SerializedName("StreetAddress2")
    @Expose
    private String streetAddress2;
    @SerializedName("CityEnabled")
    @Expose
    private Boolean cityEnabled;
    @SerializedName("CityRequired")
    @Expose
    private Boolean cityRequired;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("ZipPostalCode")
    @Expose
    private String zipPostalCode;
    @SerializedName("CountyEnabled")
    @Expose
    private Boolean countyEnabled;
    @SerializedName("CountyRequired")
    @Expose
    private Boolean countyRequired;
    @SerializedName("CountryEnabled")
    @Expose
    private Boolean countryEnabled;
    @SerializedName("CountryRequired")
    @Expose
    private Boolean countryRequired;
    @SerializedName("CountryId")
    @Expose
    private Integer countryId;
    @SerializedName("County")
    @Expose
    private String county;
    @SerializedName("StateProvinceEnabled")
    @Expose
    private Boolean stateProvinceEnabled;
    @SerializedName("StateProvinceRequired")
    @Expose
    private Boolean stateProvinceRequired;
    @SerializedName("StateProvinceId")
    @Expose
    private Integer stateProvinceId;
    @SerializedName("AvailableCountries")
    @Expose
    private List<Object> availableCountries = null;
    @SerializedName("PhoneEnabled")
    @Expose
    private Boolean phoneEnabled;
    @SerializedName("PhoneRequired")
    @Expose
    private Boolean phoneRequired;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("FaxEnabled")
    @Expose
    private Boolean faxEnabled;
    @SerializedName("FaxRequired")
    @Expose
    private Boolean faxRequired;
    @SerializedName("Fax")
    @Expose
    private String fax;
    @SerializedName("NewsletterEnabled")
    @Expose
    private Boolean newsletterEnabled;
    @SerializedName("Newsletter")
    @Expose
    private Boolean newsletter;
    @SerializedName("SignatureEnabled")
    @Expose
    private Boolean signatureEnabled;
    @SerializedName("AvailableStates")
    @Expose
    private List<Object> availableStates = null;
    @SerializedName("Signature")
    @Expose
    private String signature;
    @SerializedName("AllowCustomersToSetTimeZone")
    @Expose
    private Boolean allowCustomersToSetTimeZone;
    @SerializedName("TimeZoneId")
    @Expose
    private int timeZoneId;
    @SerializedName("AvailableTimeZones")
    @Expose
    private List<AvailableTimeZone> availableTimeZones = null;
    @SerializedName("VatNumberStatusNote")
    @Expose
    private String vatNumberStatusNote;
    @SerializedName("DisplayVatNumber")
    @Expose
    private Boolean displayVatNumber;
    @SerializedName("VatNumber")
    @Expose
    private String vatNumber;
    @SerializedName("NumberOfExternalAuthenticationProviders")
    @Expose
    private Integer numberOfExternalAuthenticationProviders;
    @SerializedName("AssociatedExternalAuthRecords")
    @Expose
    private List<Object> associatedExternalAuthRecords = null;
    @SerializedName("AllowCustomersToRemoveAssociations")
    @Expose
    private Boolean allowCustomersToRemoveAssociations;
    @SerializedName("CustomerAttributes")
    @Expose
    private List<Object> customerAttributes = null;
    @SerializedName("GdprConsents")
    @Expose
    private List<Object> gdprConsents = null;

    protected InfoModel(Parcel in) {
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.emailToRevalidate = ((String) in.readValue((String.class.getClassLoader())));
        this.checkUsernameAvailabilityEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.allowUsersToChangeUsernames = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.usernamesEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.username = ((String) in.readValue((String.class.getClassLoader())));
        this.genderEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.gender = ((String) in.readValue((String.class.getClassLoader())));
        this.firstNameEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.firstName = ((String) in.readValue((String.class.getClassLoader())));
        this.firstNameRequired = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.lastNameEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.lastName = ((String) in.readValue((String.class.getClassLoader())));
        this.lastNameRequired = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.dateOfBirthEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.dateOfBirthDay = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.dateOfBirthMonth = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.dateOfBirthYear = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.dateOfBirthRequired = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.companyEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.companyRequired = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.company = ((String) in.readValue((String.class.getClassLoader())));
        this.streetAddressEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.streetAddressRequired = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.streetAddress = ((String) in.readValue((String.class.getClassLoader())));
        this.streetAddress2Enabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.streetAddress2Required = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.streetAddress2 = ((String) in.readValue((String.class.getClassLoader())));
        this.zipPostalCodeEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.zipPostalCodeRequired = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.zipPostalCode = ((String) in.readValue((String.class.getClassLoader())));
        this.cityEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.cityRequired = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.city = ((String) in.readValue((String.class.getClassLoader())));
        this.countyEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.countyRequired = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.county = ((String) in.readValue((String.class.getClassLoader())));
        this.countryEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.countryRequired = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.countryId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.availableCountries, (java.lang.Object.class.getClassLoader()));
        this.stateProvinceEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.stateProvinceRequired = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.stateProvinceId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.availableStates, (java.lang.Object.class.getClassLoader()));
        this.phoneEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.phoneRequired = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.phone = ((String) in.readValue((String.class.getClassLoader())));
        this.faxEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.faxRequired = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.fax = ((String) in.readValue((String.class.getClassLoader())));
        this.newsletterEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.newsletter = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.signatureEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.signature = ((String) in.readValue((String.class.getClassLoader())));
        this.timeZoneId = ((int) in.readValue((int.class.getClassLoader())));
        this.allowCustomersToSetTimeZone = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.availableTimeZones, (AvailableTimeZone.class.getClassLoader()));
        this.vatNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.vatNumberStatusNote = ((String) in.readValue((String.class.getClassLoader())));
        this.displayVatNumber = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.associatedExternalAuthRecords, (java.lang.Object.class.getClassLoader()));
        this.numberOfExternalAuthenticationProviders = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.allowCustomersToRemoveAssociations = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.customerAttributes, (java.lang.Object.class.getClassLoader()));
        in.readList(this.gdprConsents, (java.lang.Object.class.getClassLoader()));
    }

    public InfoModel() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailToRevalidate() {
        return emailToRevalidate;
    }

    public void setEmailToRevalidate(String emailToRevalidate) {
        this.emailToRevalidate = emailToRevalidate;
    }

    public Boolean getCheckUsernameAvailabilityEnabled() {
        return checkUsernameAvailabilityEnabled;
    }

    public void setCheckUsernameAvailabilityEnabled(Boolean checkUsernameAvailabilityEnabled) {
        this.checkUsernameAvailabilityEnabled = checkUsernameAvailabilityEnabled;
    }

    public Boolean getAllowUsersToChangeUsernames() {
        return allowUsersToChangeUsernames;
    }

    public void setAllowUsersToChangeUsernames(Boolean allowUsersToChangeUsernames) {
        this.allowUsersToChangeUsernames = allowUsersToChangeUsernames;
    }

    public Boolean getUsernamesEnabled() {
        return usernamesEnabled;
    }

    public void setUsernamesEnabled(Boolean usernamesEnabled) {
        this.usernamesEnabled = usernamesEnabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getGenderEnabled() {
        return genderEnabled;
    }

    public void setGenderEnabled(Boolean genderEnabled) {
        this.genderEnabled = genderEnabled;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Boolean getFirstNameEnabled() {
        return firstNameEnabled;
    }

    public void setFirstNameEnabled(Boolean firstNameEnabled) {
        this.firstNameEnabled = firstNameEnabled;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Boolean getFirstNameRequired() {
        return firstNameRequired;
    }

    public void setFirstNameRequired(Boolean firstNameRequired) {
        this.firstNameRequired = firstNameRequired;
    }

    public Boolean getLastNameEnabled() {
        return lastNameEnabled;
    }

    public void setLastNameEnabled(Boolean lastNameEnabled) {
        this.lastNameEnabled = lastNameEnabled;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getLastNameRequired() {
        return lastNameRequired;
    }

    public void setLastNameRequired(Boolean lastNameRequired) {
        this.lastNameRequired = lastNameRequired;
    }

    public Boolean getDateOfBirthEnabled() {
        return dateOfBirthEnabled;
    }

    public void setDateOfBirthEnabled(Boolean dateOfBirthEnabled) {
        this.dateOfBirthEnabled = dateOfBirthEnabled;
    }

    public Integer getDateOfBirthDay() {
        return dateOfBirthDay;
    }

    public void setDateOfBirthDay(Integer dateOfBirthDay) {
        this.dateOfBirthDay = dateOfBirthDay;
    }

    public Integer getDateOfBirthMonth() {
        return dateOfBirthMonth;
    }

    public void setDateOfBirthMonth(Integer dateOfBirthMonth) {
        this.dateOfBirthMonth = dateOfBirthMonth;
    }

    public Integer getDateOfBirthYear() {
        return dateOfBirthYear;
    }

    public void setDateOfBirthYear(Integer dateOfBirthYear) {
        this.dateOfBirthYear = dateOfBirthYear;
    }

    public Boolean getDateOfBirthRequired() {
        return dateOfBirthRequired;
    }

    public void setDateOfBirthRequired(Boolean dateOfBirthRequired) {
        this.dateOfBirthRequired = dateOfBirthRequired;
    }

    public Boolean getCompanyEnabled() {
        return companyEnabled;
    }

    public void setCompanyEnabled(Boolean companyEnabled) {
        this.companyEnabled = companyEnabled;
    }

    public Boolean getCompanyRequired() {
        return companyRequired;
    }

    public void setCompanyRequired(Boolean companyRequired) {
        this.companyRequired = companyRequired;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Boolean getStreetAddressEnabled() {
        return streetAddressEnabled;
    }

    public void setStreetAddressEnabled(Boolean streetAddressEnabled) {
        this.streetAddressEnabled = streetAddressEnabled;
    }

    public Boolean getStreetAddressRequired() {
        return streetAddressRequired;
    }

    public void setStreetAddressRequired(Boolean streetAddressRequired) {
        this.streetAddressRequired = streetAddressRequired;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public Boolean getStreetAddress2Enabled() {
        return streetAddress2Enabled;
    }

    public void setStreetAddress2Enabled(Boolean streetAddress2Enabled) {
        this.streetAddress2Enabled = streetAddress2Enabled;
    }

    public Boolean getStreetAddress2Required() {
        return streetAddress2Required;
    }

    public void setStreetAddress2Required(Boolean streetAddress2Required) {
        this.streetAddress2Required = streetAddress2Required;
    }

    public String getStreetAddress2() {
        return streetAddress2;
    }

    public void setStreetAddress2(String streetAddress2) {
        this.streetAddress2 = streetAddress2;
    }

    public Boolean getZipPostalCodeEnabled() {
        return zipPostalCodeEnabled;
    }

    public void setZipPostalCodeEnabled(Boolean zipPostalCodeEnabled) {
        this.zipPostalCodeEnabled = zipPostalCodeEnabled;
    }

    public Boolean getZipPostalCodeRequired() {
        return zipPostalCodeRequired;
    }

    public void setZipPostalCodeRequired(Boolean zipPostalCodeRequired) {
        this.zipPostalCodeRequired = zipPostalCodeRequired;
    }

    public String getZipPostalCode() {
        return zipPostalCode;
    }

    public void setZipPostalCode(String zipPostalCode) {
        this.zipPostalCode = zipPostalCode;
    }

    public Boolean getCityEnabled() {
        return cityEnabled;
    }

    public void setCityEnabled(Boolean cityEnabled) {
        this.cityEnabled = cityEnabled;
    }

    public Boolean getCityRequired() {
        return cityRequired;
    }

    public void setCityRequired(Boolean cityRequired) {
        this.cityRequired = cityRequired;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getCountyEnabled() {
        return countyEnabled;
    }

    public void setCountyEnabled(Boolean countyEnabled) {
        this.countyEnabled = countyEnabled;
    }

    public Boolean getCountyRequired() {
        return countyRequired;
    }

    public void setCountyRequired(Boolean countyRequired) {
        this.countyRequired = countyRequired;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public Boolean getCountryEnabled() {
        return countryEnabled;
    }

    public void setCountryEnabled(Boolean countryEnabled) {
        this.countryEnabled = countryEnabled;
    }

    public Boolean getCountryRequired() {
        return countryRequired;
    }

    public void setCountryRequired(Boolean countryRequired) {
        this.countryRequired = countryRequired;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public List<Object> getAvailableCountries() {
        return availableCountries;
    }

    public void setAvailableCountries(List<Object> availableCountries) {
        this.availableCountries = availableCountries;
    }

    public Boolean getStateProvinceEnabled() {
        return stateProvinceEnabled;
    }

    public void setStateProvinceEnabled(Boolean stateProvinceEnabled) {
        this.stateProvinceEnabled = stateProvinceEnabled;
    }

    public Boolean getStateProvinceRequired() {
        return stateProvinceRequired;
    }

    public void setStateProvinceRequired(Boolean stateProvinceRequired) {
        this.stateProvinceRequired = stateProvinceRequired;
    }

    public Integer getStateProvinceId() {
        return stateProvinceId;
    }

    public void setStateProvinceId(Integer stateProvinceId) {
        this.stateProvinceId = stateProvinceId;
    }

    public List<Object> getAvailableStates() {
        return availableStates;
    }

    public void setAvailableStates(List<Object> availableStates) {
        this.availableStates = availableStates;
    }

    public Boolean getPhoneEnabled() {
        return phoneEnabled;
    }

    public void setPhoneEnabled(Boolean phoneEnabled) {
        this.phoneEnabled = phoneEnabled;
    }

    public Boolean getPhoneRequired() {
        return phoneRequired;
    }

    public void setPhoneRequired(Boolean phoneRequired) {
        this.phoneRequired = phoneRequired;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getFaxEnabled() {
        return faxEnabled;
    }

    public void setFaxEnabled(Boolean faxEnabled) {
        this.faxEnabled = faxEnabled;
    }

    public Boolean getFaxRequired() {
        return faxRequired;
    }

    public void setFaxRequired(Boolean faxRequired) {
        this.faxRequired = faxRequired;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Boolean getNewsletterEnabled() {
        return newsletterEnabled;
    }

    public void setNewsletterEnabled(Boolean newsletterEnabled) {
        this.newsletterEnabled = newsletterEnabled;
    }

    public Boolean getNewsletter() {
        return newsletter;
    }

    public void setNewsletter(Boolean newsletter) {
        this.newsletter = newsletter;
    }

    public Boolean getSignatureEnabled() {
        return signatureEnabled;
    }

    public void setSignatureEnabled(Boolean signatureEnabled) {
        this.signatureEnabled = signatureEnabled;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(int timeZoneId) {
        this.timeZoneId = timeZoneId;
    }

    public Boolean getAllowCustomersToSetTimeZone() {
        return allowCustomersToSetTimeZone;
    }

    public void setAllowCustomersToSetTimeZone(Boolean allowCustomersToSetTimeZone) {
        this.allowCustomersToSetTimeZone = allowCustomersToSetTimeZone;
    }

    public List<AvailableTimeZone> getAvailableTimeZones() {
        return availableTimeZones;
    }

    public void setAvailableTimeZones(List<AvailableTimeZone> availableTimeZones) {
        this.availableTimeZones = availableTimeZones;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public String getVatNumberStatusNote() {
        return vatNumberStatusNote;
    }

    public void setVatNumberStatusNote(String vatNumberStatusNote) {
        this.vatNumberStatusNote = vatNumberStatusNote;
    }

    public Boolean getDisplayVatNumber() {
        return displayVatNumber;
    }

    public void setDisplayVatNumber(Boolean displayVatNumber) {
        this.displayVatNumber = displayVatNumber;
    }

    public List<Object> getAssociatedExternalAuthRecords() {
        return associatedExternalAuthRecords;
    }

    public void setAssociatedExternalAuthRecords(List<Object> associatedExternalAuthRecords) {
        this.associatedExternalAuthRecords = associatedExternalAuthRecords;
    }

    public Integer getNumberOfExternalAuthenticationProviders() {
        return numberOfExternalAuthenticationProviders;
    }

    public void setNumberOfExternalAuthenticationProviders(Integer numberOfExternalAuthenticationProviders) {
        this.numberOfExternalAuthenticationProviders = numberOfExternalAuthenticationProviders;
    }

    public Boolean getAllowCustomersToRemoveAssociations() {
        return allowCustomersToRemoveAssociations;
    }

    public void setAllowCustomersToRemoveAssociations(Boolean allowCustomersToRemoveAssociations) {
        this.allowCustomersToRemoveAssociations = allowCustomersToRemoveAssociations;
    }

    public List<Object> getCustomerAttributes() {
        return customerAttributes;
    }

    public void setCustomerAttributes(List<Object> customerAttributes) {
        this.customerAttributes = customerAttributes;
    }

    public List<Object> getGdprConsents() {
        return gdprConsents;
    }

    public void setGdprConsents(List<Object> gdprConsents) {
        this.gdprConsents = gdprConsents;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(email);
        dest.writeValue(emailToRevalidate);
        dest.writeValue(checkUsernameAvailabilityEnabled);
        dest.writeValue(allowUsersToChangeUsernames);
        dest.writeValue(usernamesEnabled);
        dest.writeValue(username);
        dest.writeValue(genderEnabled);
        dest.writeValue(gender);
        dest.writeValue(firstNameEnabled);
        dest.writeValue(firstName);
        dest.writeValue(firstNameRequired);
        dest.writeValue(lastNameEnabled);
        dest.writeValue(lastName);
        dest.writeValue(lastNameRequired);
        dest.writeValue(dateOfBirthEnabled);
        dest.writeValue(dateOfBirthDay);
        dest.writeValue(dateOfBirthMonth);
        dest.writeValue(dateOfBirthYear);
        dest.writeValue(dateOfBirthRequired);
        dest.writeValue(companyEnabled);
        dest.writeValue(companyRequired);
        dest.writeValue(company);
        dest.writeValue(streetAddressEnabled);
        dest.writeValue(streetAddressRequired);
        dest.writeValue(streetAddress);
        dest.writeValue(streetAddress2Enabled);
        dest.writeValue(streetAddress2Required);
        dest.writeValue(streetAddress2);
        dest.writeValue(zipPostalCodeEnabled);
        dest.writeValue(zipPostalCodeRequired);
        dest.writeValue(zipPostalCode);
        dest.writeValue(cityEnabled);
        dest.writeValue(cityRequired);
        dest.writeValue(city);
        dest.writeValue(countyEnabled);
        dest.writeValue(countyRequired);
        dest.writeValue(county);
        dest.writeValue(countryEnabled);
        dest.writeValue(countryRequired);
        dest.writeValue(countryId);
        dest.writeList(availableCountries);
        dest.writeValue(stateProvinceEnabled);
        dest.writeValue(stateProvinceRequired);
        dest.writeValue(stateProvinceId);
        dest.writeList(availableStates);
        dest.writeValue(phoneEnabled);
        dest.writeValue(phoneRequired);
        dest.writeValue(phone);
        dest.writeValue(faxEnabled);
        dest.writeValue(faxRequired);
        dest.writeValue(fax);
        dest.writeValue(newsletterEnabled);
        dest.writeValue(newsletter);
        dest.writeValue(signatureEnabled);
        dest.writeValue(signature);
        dest.writeValue(timeZoneId);
        dest.writeValue(allowCustomersToSetTimeZone);
        dest.writeList(availableTimeZones);
        dest.writeValue(vatNumber);
        dest.writeValue(vatNumberStatusNote);
        dest.writeValue(displayVatNumber);
        dest.writeList(associatedExternalAuthRecords);
        dest.writeValue(numberOfExternalAuthenticationProviders);
        dest.writeValue(allowCustomersToRemoveAssociations);
        dest.writeList(customerAttributes);
        dest.writeList(gdprConsents);
    }

    public int describeContents() {
        return 0;
    }

}
package com.growonline.gomobishop.model;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetail implements Parcelable
{

    @SerializedName("PrintMode")
    @Expose
    private Boolean printMode;
    @SerializedName("PdfInvoiceDisabled")
    @Expose
    private Boolean pdfInvoiceDisabled;
    @SerializedName("CustomOrderNumber")
    @Expose
    private String customOrderNumber;
    @SerializedName("CreatedOn")
    @Expose
    private String createdOn;
    @SerializedName("OrderStatus")
    @Expose
    private String orderStatus;
    @SerializedName("IsReOrderAllowed")
    @Expose
    private Boolean isReOrderAllowed;
    @SerializedName("IsReturnRequestAllowed")
    @Expose
    private Boolean isReturnRequestAllowed;
    @SerializedName("IsShippable")
    @Expose
    private Boolean isShippable;
    @SerializedName("PickUpInStore")
    @Expose
    private Boolean pickUpInStore;
    @SerializedName("PickupAddress")
    @Expose
    private Address pickupAddress;
    @SerializedName("ShippingStatus")
    @Expose
    private String shippingStatus;
    @SerializedName("ShippingAddress")
    @Expose
    private Address shippingAddress;
    @SerializedName("ShippingMethod")
    @Expose
    private String shippingMethod;
    @SerializedName("Shipments")
    @Expose
    private List<Object> shipments = null;
    @SerializedName("BillingAddress")
    @Expose
    private Address billingAddress;
    @SerializedName("VatNumber")
    @Expose
    private Object vatNumber;
    @SerializedName("PaymentMethod")
    @Expose
    private String paymentMethod;
    @SerializedName("PaymentMethodStatus")
    @Expose
    private String paymentMethodStatus;
    @SerializedName("CanRePostProcessPayment")
    @Expose
    private Boolean canRePostProcessPayment;

    @SerializedName("OrderSubtotal")
    @Expose
    private String orderSubtotal;
    @SerializedName("OrderSubTotalDiscount")
    @Expose
    private Object orderSubTotalDiscount;
    @SerializedName("OrderShipping")
    @Expose
    private String orderShipping;
    @SerializedName("PaymentMethodAdditionalFee")
    @Expose
    private Object paymentMethodAdditionalFee;
    @SerializedName("CheckoutAttributeInfo")
    @Expose
    private String checkoutAttributeInfo;
    @SerializedName("PricesIncludeTax")
    @Expose
    private Boolean pricesIncludeTax;
    @SerializedName("DisplayTaxShippingInfo")
    @Expose
    private Boolean displayTaxShippingInfo;
    @SerializedName("Tax")
    @Expose
    private String tax;
    @SerializedName("TaxRates")
    @Expose
    private List<TaxRate> taxRates = null;
    @SerializedName("DisplayTax")
    @Expose
    private Boolean displayTax;
    @SerializedName("DisplayTaxRates")
    @Expose
    private Boolean displayTaxRates;
    @SerializedName("OrderTotalDiscount")
    @Expose
    private String orderTotalDiscount;
    @SerializedName("RedeemedRewardPoints")
    @Expose
    private Integer redeemedRewardPoints;
    @SerializedName("RedeemedRewardPointsAmount")
    @Expose
    private String redeemedRewardPointsAmount;
    @SerializedName("OrderTotal")
    @Expose
    private String orderTotal;
    @SerializedName("GiftCards")
    @Expose
    private List<Object> giftCards = null;
    @SerializedName("ShowSku")
    @Expose
    private Boolean showSku;
    @SerializedName("Items")
    @Expose
    private List<ShoppingCartItem> items = null;
    @SerializedName("OrderNotes")
    @Expose
    private List<Object> orderNotes = null;
    @SerializedName("Id")
    @Expose
    private Integer id;

    public final static Parcelable.Creator<OrderDetail> CREATOR = new Creator<OrderDetail>() {


        @SuppressWarnings({
                "unchecked"
        })
        public OrderDetail createFromParcel(Parcel in) {
            return new OrderDetail(in);
        }

        public OrderDetail[] newArray(int size) {
            return (new OrderDetail[size]);
        }

    }
            ;

    protected OrderDetail(Parcel in) {
        this.printMode = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.pdfInvoiceDisabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.customOrderNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.createdOn = ((String) in.readValue((String.class.getClassLoader())));
        this.orderStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.isReOrderAllowed = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.isReturnRequestAllowed = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.isShippable = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.pickUpInStore = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.pickupAddress = ((Address) in.readValue((Address.class.getClassLoader())));
        this.shippingStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.shippingAddress = ((Address) in.readValue((Address.class.getClassLoader())));
        this.shippingMethod = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.shipments, (java.lang.Object.class.getClassLoader()));
        this.billingAddress = ((Address) in.readValue((Address.class.getClassLoader())));
        this.vatNumber = ((Object) in.readValue((Object.class.getClassLoader())));
        this.paymentMethod = ((String) in.readValue((String.class.getClassLoader())));
        this.paymentMethodStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.canRePostProcessPayment = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.orderSubtotal = ((String) in.readValue((String.class.getClassLoader())));
        this.orderSubTotalDiscount = ((Object) in.readValue((Object.class.getClassLoader())));
        this.orderShipping = ((String) in.readValue((String.class.getClassLoader())));
        this.paymentMethodAdditionalFee = ((Object) in.readValue((Object.class.getClassLoader())));
        this.checkoutAttributeInfo = ((String) in.readValue((String.class.getClassLoader())));
        this.pricesIncludeTax = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.displayTaxShippingInfo = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.tax = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.taxRates, (com.growonline.gomobishop.model.TaxRate.class.getClassLoader()));
        this.displayTax = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.displayTaxRates = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.orderTotalDiscount = ((String) in.readValue((String.class.getClassLoader())));
        this.redeemedRewardPoints = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.redeemedRewardPointsAmount = ((String) in.readValue((String.class.getClassLoader())));
        this.orderTotal = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.items, (com.growonline.gomobishop.model.ShoppingCartItem.class.getClassLoader()));
        in.readList(this.giftCards, (java.lang.Object.class.getClassLoader()));
        this.showSku = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.orderNotes, (java.lang.Object.class.getClassLoader()));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public OrderDetail() {
    }

    public Boolean getPrintMode() {
        return printMode;
    }

    public void setPrintMode(Boolean printMode) {
        this.printMode = printMode;
    }

    public Boolean getPdfInvoiceDisabled() {
        return pdfInvoiceDisabled;
    }

    public void setPdfInvoiceDisabled(Boolean pdfInvoiceDisabled) {
        this.pdfInvoiceDisabled = pdfInvoiceDisabled;
    }

    public String getCustomOrderNumber() {
        return customOrderNumber;
    }

    public void setCustomOrderNumber(String customOrderNumber) {
        this.customOrderNumber = customOrderNumber;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Boolean getIsReOrderAllowed() {
        return isReOrderAllowed;
    }

    public void setIsReOrderAllowed(Boolean isReOrderAllowed) {
        this.isReOrderAllowed = isReOrderAllowed;
    }

    public Boolean getIsReturnRequestAllowed() {
        return isReturnRequestAllowed;
    }

    public void setIsReturnRequestAllowed(Boolean isReturnRequestAllowed) {
        this.isReturnRequestAllowed = isReturnRequestAllowed;
    }

    public Boolean getIsShippable() {
        return isShippable;
    }

    public void setIsShippable(Boolean isShippable) {
        this.isShippable = isShippable;
    }

    public Boolean getPickUpInStore() {
        return pickUpInStore;
    }

    public void setPickUpInStore(Boolean pickUpInStore) {
        this.pickUpInStore = pickUpInStore;
    }

    public Address getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(Address pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public String getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(String shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public List<Object> getShipments() {
        return shipments;
    }

    public void setShipments(List<Object> shipments) {
        this.shipments = shipments;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Object getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(Object vatNumber) {
        this.vatNumber = vatNumber;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethodStatus() {
        return paymentMethodStatus;
    }

    public void setPaymentMethodStatus(String paymentMethodStatus) {
        this.paymentMethodStatus = paymentMethodStatus;
    }

    public Boolean getCanRePostProcessPayment() {
        return canRePostProcessPayment;
    }

    public void setCanRePostProcessPayment(Boolean canRePostProcessPayment) {
        this.canRePostProcessPayment = canRePostProcessPayment;
    }

    public String getOrderSubtotal() {
        return orderSubtotal;
    }

    public void setOrderSubtotal(String orderSubtotal) {
        this.orderSubtotal = orderSubtotal;
    }

    public Object getOrderSubTotalDiscount() {
        return orderSubTotalDiscount;
    }

    public void setOrderSubTotalDiscount(Object orderSubTotalDiscount) {
        this.orderSubTotalDiscount = orderSubTotalDiscount;
    }

    public String getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(String orderShipping) {
        this.orderShipping = orderShipping;
    }

    public Object getPaymentMethodAdditionalFee() {
        return paymentMethodAdditionalFee;
    }

    public void setPaymentMethodAdditionalFee(Object paymentMethodAdditionalFee) {
        this.paymentMethodAdditionalFee = paymentMethodAdditionalFee;
    }

    public String getCheckoutAttributeInfo() {
        return checkoutAttributeInfo;
    }

    public void setCheckoutAttributeInfo(String checkoutAttributeInfo) {
        this.checkoutAttributeInfo = checkoutAttributeInfo;
    }

    public Boolean getPricesIncludeTax() {
        return pricesIncludeTax;
    }

    public void setPricesIncludeTax(Boolean pricesIncludeTax) {
        this.pricesIncludeTax = pricesIncludeTax;
    }

    public Boolean getDisplayTaxShippingInfo() {
        return displayTaxShippingInfo;
    }

    public void setDisplayTaxShippingInfo(Boolean displayTaxShippingInfo) {
        this.displayTaxShippingInfo = displayTaxShippingInfo;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public List<TaxRate> getTaxRates() {
        return taxRates;
    }

    public void setTaxRates(List<TaxRate> taxRates) {
        this.taxRates = taxRates;
    }

    public Boolean getDisplayTax() {
        return displayTax;
    }

    public void setDisplayTax(Boolean displayTax) {
        this.displayTax = displayTax;
    }

    public Boolean getDisplayTaxRates() {
        return displayTaxRates;
    }

    public void setDisplayTaxRates(Boolean displayTaxRates) {
        this.displayTaxRates = displayTaxRates;
    }

    public String getOrderTotalDiscount() {
        return orderTotalDiscount;
    }

    public void setOrderTotalDiscount(String orderTotalDiscount) {
        this.orderTotalDiscount = orderTotalDiscount;
    }

    public Integer getRedeemedRewardPoints() {
        return redeemedRewardPoints;
    }

    public void setRedeemedRewardPoints(Integer redeemedRewardPoints) {
        this.redeemedRewardPoints = redeemedRewardPoints;
    }

    public String getRedeemedRewardPointsAmount() {
        return redeemedRewardPointsAmount;
    }

    public void setRedeemedRewardPointsAmount(String redeemedRewardPointsAmount) {
        this.redeemedRewardPointsAmount = redeemedRewardPointsAmount;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }

    public List<Object> getGiftCards() {
        return giftCards;
    }

    public void setGiftCards(List<Object> giftCards) {
        this.giftCards = giftCards;
    }

    public Boolean getShowSku() {
        return showSku;
    }

    public void setShowSku(Boolean showSku) {
        this.showSku = showSku;
    }

    public List<ShoppingCartItem> getItems() {
        return items;
    }

    public void setItems(List<ShoppingCartItem> items) {
        this.items = items;
    }

    public List<Object> getOrderNotes() {
        return orderNotes;
    }

    public void setOrderNotes(List<Object> orderNotes) {
        this.orderNotes = orderNotes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(printMode);
        dest.writeValue(pdfInvoiceDisabled);
        dest.writeValue(customOrderNumber);
        dest.writeValue(createdOn);
        dest.writeValue(orderStatus);
        dest.writeValue(isReOrderAllowed);
        dest.writeValue(isReturnRequestAllowed);
        dest.writeValue(isShippable);
        dest.writeValue(pickUpInStore);
        dest.writeValue(pickupAddress);
        dest.writeValue(shippingStatus);
        dest.writeValue(shippingAddress);
        dest.writeValue(shippingMethod);
        dest.writeList(shipments);
        dest.writeValue(billingAddress);
        dest.writeValue(vatNumber);
        dest.writeValue(paymentMethod);
        dest.writeValue(paymentMethodStatus);
        dest.writeValue(canRePostProcessPayment);
        dest.writeValue(orderSubtotal);
        dest.writeValue(orderSubTotalDiscount);
        dest.writeValue(orderShipping);
        dest.writeValue(paymentMethodAdditionalFee);
        dest.writeValue(checkoutAttributeInfo);
        dest.writeValue(pricesIncludeTax);
        dest.writeValue(displayTaxShippingInfo);
        dest.writeValue(tax);
        dest.writeList(taxRates);
        dest.writeValue(displayTax);
        dest.writeValue(displayTaxRates);
        dest.writeValue(orderTotalDiscount);
        dest.writeValue(redeemedRewardPoints);
        dest.writeValue(redeemedRewardPointsAmount);
        dest.writeValue(orderTotal);
        dest.writeList(giftCards);
        dest.writeValue(showSku);
        dest.writeList(orderNotes);
        dest.writeValue(id);
    }

    public int describeContents() {
        return 0;
    }

}
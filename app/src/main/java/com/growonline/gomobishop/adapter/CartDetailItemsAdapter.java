package com.growonline.gomobishop.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.BaseActivity;
import com.growonline.gomobishop.BuildConfig;
import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.OnePageCheckoutActivity;
import com.growonline.gomobishop.OrderDetailActivity;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.RelatedProductsActivity;
import com.growonline.gomobishop.ShopCartActivity;
import com.growonline.gomobishop.WishListDetailsActivity;
import com.growonline.gomobishop.asynctask.AddItemsToCartFromWishlist;
import com.growonline.gomobishop.asynctask.AsyncTaskDeleteProductFromCart;
import com.growonline.gomobishop.asynctask.AsyncTaskResult;
import com.growonline.gomobishop.asynctask.AsyncTaskResultListener;
import com.growonline.gomobishop.control.AspectRatioImageView;
import com.growonline.gomobishop.control.DetectHtml;
import com.growonline.gomobishop.control.FontTextView;
import com.growonline.gomobishop.control.SwipeRevealLayout;
import com.growonline.gomobishop.control.ViewBinderHelper;
import com.growonline.gomobishop.model.AttributeValue;
import com.growonline.gomobishop.model.Product;
import com.growonline.gomobishop.model.ProductAttribute;
import com.growonline.gomobishop.model.ShoppingCartItem;
import com.growonline.gomobishop.model.VendorItem;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;

import java.util.ArrayList;
import java.util.List;


public class CartDetailItemsAdapter extends RecyclerView.Adapter<CartDetailItemsAdapter.CartDetailItemViewHolder> {

    private final ViewBinderHelper binderHelper = new ViewBinderHelper();
    private AppCompatActivity mActivity;
    private ShopCartListener mListener;
    private List<ShoppingCartItem> mCartItems = new ArrayList<>();
    private AddItemsToCartFromWishlist addItemsToCartFromWishlist;
    private List<VendorItem> mVendorItems = new ArrayList<>();


    public CartDetailItemsAdapter(AppCompatActivity activity, List<ShoppingCartItem> cartItems, List<VendorItem> vendorItems, ShopCartListener listener) {
//        mCartItems = cartItems;
        mActivity = activity;
        mListener = listener;
        // uncomment if you want to open only one row at a time
        mCartItems.clear();
        if (vendorItems.size() > 1) {
            for (VendorItem vendorItem : vendorItems) {
                mCartItems.addAll(vendorItem.getItems());
                mCartItems.add(new ShoppingCartItem());
            }
        } else {
            mCartItems.addAll(cartItems);
        }
        mVendorItems.clear();
        mVendorItems.addAll(vendorItems);

        binderHelper.setOpenOnlyOne(true);

    }

    @Override
    public CartDetailItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(BuildConfig.market ? R.layout.single_shop_cart_item : R.layout.single_shop_cart_item_simple, parent, false);

        return new CartDetailItemViewHolder(itemView);

    }

    private VendorItem FindVendor(int vendorId) {
        for (VendorItem vendorItem : mVendorItems) {
            if (vendorItem.getVendorId().equals(vendorId)) {
                return vendorItem;
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(CartDetailItemViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        ShoppingCartItem item = mCartItems.get(position);
        if (item.getProductId() == null) {
            holder.swipeLayout.setVisibility(View.GONE);
            holder.separator.setVisibility(View.VISIBLE);
            holder.total.setVisibility(View.VISIBLE);
            holder.shipping_charges.setVisibility(View.VISIBLE);
            holder.total.setText("Total : " + FindVendor(mCartItems.get(position - 1).getVendorId()).getTotalFormatted());
            holder.shipping_charges.setText("Shipping Charges : " + FindVendor(mCartItems.get(position - 1).getVendorId()).getShippingChargesFormatted());
            return;
        }

        GoMobileApp.getmCacheManager().loadImage(Uri.parse(item.getPicture().getImageUrl()),
                holder.img_prd_thumb, null);

        holder.txt_title.setText(item.getProductName());
        holder.txt_price.setText(item.getUnitPrice());
        holder.txt_qty.setText(String.valueOf(item.getQuantity()));

        if (item.getVendorName() != null)
            holder.txt_vendor.setText(item.getVendorName());
        else holder.txt_vendor.setVisibility(View.GONE);


        holder.pid = item.getProductId();
        holder.quantity = item.getQuantity();
        holder.cartItemId = item.getId();
        if (DetectHtml.isHtml(item.getAttributeInfo())) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                holder.attributes.setText(Html.fromHtml(item.getAttributeInfo(), Html.FROM_HTML_MODE_LEGACY));
            } else {
                holder.attributes.setText(Html.fromHtml(item.getAttributeInfo()));
            }
            holder.attributes.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            holder.attributes.setText(item.getAttributeInfo());
        }

        if (mActivity instanceof WishListDetailsActivity || mActivity instanceof OrderDetailActivity) {
            holder.cartQuantity.setVisibility(View.GONE);
            holder.cartDelete.setVisibility(View.GONE);
            holder.addToCart.setVisibility(View.GONE);
        } else if (mActivity instanceof ShopCartActivity || mActivity instanceof RelatedProductsActivity) {
            holder.addToCart.setVisibility(View.GONE);
        }
        if (mActivity instanceof OrderDetailActivity || mActivity instanceof OnePageCheckoutActivity) {
            binderHelper.lockSwipe(item.getProductName());
            holder.cartQuantity.setVisibility(View.GONE);
            holder.cartDelete.setVisibility(View.GONE);
            holder.addToCart.setVisibility(View.GONE);
        }


        // Use ViewBindHelper to restore and save the open/close state of the SwipeRevealView
        // put an unique string id as value, can be any string which uniquely define the data
        if (BuildConfig.market)
            binderHelper.bind(holder.swipeLayout, item.getProductName());
        else
            holder.setIsRecyclable(false);


    }

    @Override
    public int getItemCount() {
        return mCartItems.size();
    }

    public void setData(List<ShoppingCartItem> cartItems, List<VendorItem> vendorItems) {
        mCartItems.clear();
        if (vendorItems.size() > 1) {
            for (VendorItem vendorItem : vendorItems) {
                mCartItems.addAll(vendorItem.getItems());
                mCartItems.add(new ShoppingCartItem());
            }
        } else {
            mCartItems.addAll(cartItems);
        }
        mVendorItems.clear();
        mVendorItems.addAll(vendorItems);
    }

    private AdapterShopCartItemAttribute getAttributeValueAdapter(String attributeType,
                                                                  List<ProductAttribute> attributes,
                                                                  List<ProductAttribute> selectedAttributes) {

        AdapterShopCartItemAttribute adapter = new AdapterShopCartItemAttribute(mActivity,
                android.R.layout.simple_spinner_item, new ArrayList<AttributeValue>());

        for (int i = 0; i < selectedAttributes.size(); i++) {

            ProductAttribute temp = selectedAttributes.get(i);

            for (int j = 0; j < attributes.size(); j++) {
                ProductAttribute tempAttr = attributes.get(j);

                if (tempAttr.getId().equals(temp.getId()) &&
                        tempAttr.getName().equalsIgnoreCase(attributeType)) {

                    adapter = new AdapterShopCartItemAttribute(mActivity,
                            android.R.layout.simple_spinner_item,
                            tempAttr.getValues());

                    break;
                }
            }
        }

        return adapter;
    }

    public void removeItem(final int position) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity, R.style.AppTheme_Dialog);
        alertDialogBuilder.setMessage("Are you sure you want to delete.").setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                mListener.onItemDeleted(mCartItems.get(position).getProductId(), mCartItems.get(position).getId(), 0);
                mCartItems.remove(position);
                // notify the item removed by position
                // to perform recycler view delete animations
                // NOTE: don't call notifyDataSetChanged()
                notifyItemRemoved(position);
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                notifyDataSetChanged();
            }


        }).setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                notifyDataSetChanged();
            }
        }).show();
    }


    public interface ShopCartListener {
        public void onQuantityAdded(int pid, int cartItemId, int quantity);

        public void onQuantityRemoved(int pid, int cartItemId, int quantity);

        public void onItemDeleted(int pid, int cartItemId, int quantity);
    }

    public class CartDetailItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView btn_plus, btn_minus;
        AspectRatioImageView img_prd_thumb;
        TextView txt_title, txt_price, txt_qty, txt_vendor;
        int quantity, pid, cartItemId;
        private FontTextView attributes;
        private LinearLayout cartQuantity;
        private SwipeRevealLayout swipeLayout;
        private TextView addToCart, cartEdit, cartDelete;
        private TextView shipping_charges, total;
        private View separator;

        CartDetailItemViewHolder(View view) {
            super(view);
            swipeLayout = (SwipeRevealLayout) itemView.findViewById(R.id.swipe_layout);
            img_prd_thumb = (AspectRatioImageView) view.findViewById(R.id.img_prd);
            txt_vendor = (TextView) view.findViewById(R.id.txt_vendor);
            txt_title = (TextView) view.findViewById(R.id.txt_title);
            txt_price = (TextView) view.findViewById(R.id.txt_price);
            txt_qty = (TextView) view.findViewById(R.id.txt_quantity_item);
            btn_plus = (ImageView) view.findViewById(R.id.btn_plus_item);
            btn_minus = (ImageView) view.findViewById(R.id.btn_minus_item);
            attributes = (FontTextView) view.findViewById(R.id.checkout_attributes);
            cartQuantity = (LinearLayout) view.findViewById(R.id.Cart_Quantity);
            addToCart = (TextView) view.findViewById(R.id.Cart_AddToCart);
            cartEdit = (TextView) view.findViewById(R.id.Cart_Edit);
            cartDelete = (TextView) view.findViewById(R.id.Cart_Delete);
            shipping_charges = (TextView) view.findViewById(R.id.shipping_charges);
            separator = view.findViewById(R.id.separator);
            total = (TextView) view.findViewById(R.id.total);
            btn_plus.setOnClickListener(this);
            btn_minus.setOnClickListener(this);
            cartEdit.setOnClickListener(this);
            cartDelete.setOnClickListener(this);
            addToCart.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btn_plus_item: {
                    mListener.onQuantityAdded(pid, cartItemId, quantity);
                }
                break;
                case R.id.btn_minus_item: {
                    if (quantity > 1) {
                        mListener.onQuantityRemoved(pid, cartItemId, quantity);
                    }
                }
                break;
                case R.id.Cart_AddToCart: {
                    addItemsToCartFromWishlist = new AddItemsToCartFromWishlist(String.valueOf(mCartItems.get(getAdapterPosition()).getId()));
                    addItemsToCartFromWishlist.addOnResultListener(new AsyncTaskResultListener<Boolean>() {
                        @Override
                        public void response(AsyncTaskResult<Boolean> response) {
                            if (!response.hasException()) {
                                AppHelper.showMsgDialog(mActivity, "", mActivity.getString(R.string.wishlist_add_to_cart), null, null);
                                ((WishListDetailsActivity) mActivity).updateList(false);
                            } else {
                                GoMobileApp.Toast(response.getException().getMessage());
                            }
                        }
                    });
                    addItemsToCartFromWishlist.execute();
                }
                break;
                case R.id.Cart_Edit: {
                    Intent intent = new Intent(mActivity, RelatedProductsActivity.class);
                    intent.putExtra(AppConstant.INTENT_VENDOR, ((BaseActivity) mActivity).mVendor);
                    intent.putExtra(AppConstant.INTENT_TITLE, mCartItems.get(getAdapterPosition()).getProductName());
                    ArrayList<Product> p = new ArrayList<>();
                    for (int i = 0; i < mCartItems.size(); i++) {
                        if (mCartItems.get(i).getProductId() == null) continue;
                        Product product = new Product();
                        product.setId(mCartItems.get(i).getProductId());
                        p.add(product);
                    }
                    intent.putParcelableArrayListExtra(AppConstant.INTENT_PRODUCT, p);
                    intent.putExtra(AppConstant.INTENT_PRODUCT_ID, mCartItems.get(getAdapterPosition()).getProductId());

                    GoMobileApp.getmAppPrefEditor().putString(AppConstant.INTENT_PRODUCT_ID, String.valueOf(mCartItems.get(getAdapterPosition()).getProductId())).commit();
                    mActivity.startActivity(intent);
                }
                break;
                case R.id.Cart_Delete: {
                    if (mActivity instanceof WishListDetailsActivity)
                        new AsyncTaskDeleteProductFromCart(mActivity, String.valueOf(pid), String.valueOf(cartItemId)).execute();

                    else if (mActivity instanceof ShopCartActivity || mActivity instanceof RelatedProductsActivity)
                        mListener.onItemDeleted(pid, cartItemId, 0);
                }
                break;
            }
        }
    }
}
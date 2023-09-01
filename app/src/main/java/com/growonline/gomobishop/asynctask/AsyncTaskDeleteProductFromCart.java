package com.growonline.gomobishop.asynctask;

import android.app.Activity;

import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.ShopCartActivity;
import com.growonline.gomobishop.WishListDetailsActivity;
import com.growonline.gomobishop.base.BaseAsynctask;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;

import org.json.JSONObject;

public class AsyncTaskDeleteProductFromCart extends BaseAsynctask {
    private Object mFromWhere;
    private String mProductId;
    private String mCartItemId;
    private int mAdapterId;

    /**
     * @param mActivity  Activity From where Request Come
     * @param mProductId productId
     * @param cartItemId CartId
     */
    public AsyncTaskDeleteProductFromCart(Activity mActivity, String mProductId,
                                          String cartItemId) {
        super(mActivity, true);
        this.mProductId = mProductId;
        mCartItemId = cartItemId;
    }

    @Override
    protected void onComplete(String output) {
        if (output.equalsIgnoreCase("")) {
            if (mActivity instanceof WishListDetailsActivity) {
                AppHelper.showMsgDialog(mActivity, "", mActivity.getString(R.string.delete_wishlist_msg), null, null);
                ((WishListDetailsActivity) mActivity).updateList(false);
            }
        } else {
            AppHelper.showMsgDialog(mActivity, "", output, null, null);
        }
    }

    @Override
    protected String doInBackground(String... params) {
        String response = getResponseFromService();
        String checkPoint = onResponseReceived(response);

        if (checkPoint.equalsIgnoreCase("")) {
            try {
                String quantity = new JSONObject(response).optString("ShoppingCartItemsCount");

                if (quantity != null && !quantity.equalsIgnoreCase(""))
                    GoMobileApp.addToSharedPrefs(AppConstant.UPDATE_CART, quantity);

                return "";
            } catch (Exception e) {
                e.printStackTrace();
                return AppConstant.SERVER_ERROR_MSG;
            }
        } else {
            return checkPoint;
        }
    }

    private String getResponseFromService() {
        JSONObject jsonObject = new JSONObject();
        String response = "";
        try {
            if (mActivity instanceof ShopCartActivity) {
                jsonObject.put("productId", mProductId);
                jsonObject.put("removefromcart", mCartItemId);
                response = new GetPostSender().sendPostJSON(AppConstant.DELETE_FROM_CART, jsonObject.toString(), false);
            } else if (mActivity instanceof WishListDetailsActivity) {
                jsonObject.put("cartitemid", mCartItemId);
                response = new GetPostSender().sendPostJSON(AppConstant.DELETE_FROM_WISHLIST, jsonObject.toString(), false);
            }

            AppHelper.LogEvent(jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response = "";
        }
        return response;
    }
}

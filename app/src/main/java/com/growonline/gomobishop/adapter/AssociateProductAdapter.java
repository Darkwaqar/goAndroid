package com.growonline.gomobishop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Build;
import android.util.StateSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.RelatedProductsActivity;
import com.growonline.gomobishop.ShopTheLookDialog;
import com.growonline.gomobishop.asynctask.AddToCartAsyncTask;
import com.growonline.gomobishop.asynctask.AsyncTaskResult;
import com.growonline.gomobishop.asynctask.AsyncTaskResultListener;
import com.growonline.gomobishop.model.AttributeValue;
import com.growonline.gomobishop.model.BeanProductDetail;
import com.growonline.gomobishop.model.ProductAttribute;
import com.growonline.gomobishop.model.UserSelectedAttribute;
import com.growonline.gomobishop.util.AppHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AssociateProductAdapter extends RecyclerView.Adapter<AssociateProductAdapter.AssociateProductViewHolder> {

    private List<BeanProductDetail> mList = new ArrayList<>();
    private Context mContext;
    private AddToCartAsyncTask mAddtoCartAsynctask;
    private ShopTheLookDialog mDialog;

    public AssociateProductAdapter(Context context, ShopTheLookDialog dialog, List<BeanProductDetail> list) {
        mList = list;
        mContext = context;
        mDialog = dialog;
    }


    @Override
    public AssociateProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_associate_product, parent, false);

        return new AssociateProductViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(AssociateProductAdapter.AssociateProductViewHolder holder, int position) {

        BeanProductDetail mProduct = mList.get(position);

        holder.pid = mProduct.getId();
        GoMobileApp.getmCacheManager().loadImageForDiscoverTab(
                Uri.parse(mProduct.getPictureModels().get(0).getFullSizeImageUrl()),
                holder.mImageView, null);
        holder.mTitle.setText(mProduct.getName());
        holder.mDescription.setText(mProduct.getShortDescription());

        //style price value
        String priceValue = mProduct.getProductPrice().getPrice();
        holder.mPrice.setText(priceValue);

        //Attributes
        if (mProduct.getProductAttributes() != null && mProduct.getProductAttributes().size() > 0) {
            holder.hasAttributes = true;
            holder.mAttributePanel.setVisibility(View.VISIBLE);
            setupProductAttributes(mProduct.getProductAttributes(), holder.mAttributePanel, holder);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private void setupProductAttributes(List<ProductAttribute> attributes, LinearLayout container,
                                        final AssociateProductAdapter.AssociateProductViewHolder holder) {

        for (int i = 0; i < attributes.size(); i++) {
            final ProductAttribute currAttribute = attributes.get(i);

            switch (currAttribute.getAttributeControlType()) {
                case 1: {

                    boolean hasPreSelectedItem = false;

                    LinearLayout attrContainer = new LinearLayout(mContext);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    attrContainer.setOrientation(LinearLayout.VERTICAL);
                    attrContainer.setLayoutParams(params);

                    List<AttributeValue> values = currAttribute.getValues();

                    ArrayList<String> spinnerArray = new ArrayList<>();
                    final ArrayList<String> spinnerArrayValue = new ArrayList<>();
                    int selectedIndex = 0;

                    spinnerArray.add("SELECT " + currAttribute.getName().toUpperCase());
                    spinnerArrayValue.add("");

                    for (int v = 0; v < values.size(); v++) {
                        AttributeValue val = values.get(v);

                        String label = val.getName();

                        if (val.getIsPreSelected()) {
                            hasPreSelectedItem = true;
                            selectedIndex = v;
                        }

                        spinnerArray.add(label.toUpperCase());
                        spinnerArrayValue.add(String.valueOf(val.getId()));
                    }

                    Spinner spinner = new Spinner(mContext, Spinner.MODE_DROPDOWN);
                    LinearLayout.LayoutParams spinnerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    spinnerParams.topMargin = 20;
                    spinner.setLayoutParams(spinnerParams);

                    final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(mContext, R.layout.single_size_attr_drp_item, spinnerArray);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(spinnerArrayAdapter);
                    spinner.setTag(currAttribute.getId());
                    spinner.setBackgroundResource(R.drawable.spinner_border_dropdown);
                    spinner.setGravity(Gravity.NO_GRAVITY);
                    if (Build.VERSION.SDK_INT >= 17)
                        spinner.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);


                    TextView lbl = new TextView(mContext);

                    lbl.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12f, mContext.getResources().getDisplayMetrics()));
                    AppHelper.applyRobotoRegularFont(lbl);

                    lbl.setPadding(0, mContext.getResources().getDimensionPixelOffset(R.dimen.EightDp), mContext.getResources().getDimensionPixelSize(R.dimen.EightDp), 0);

                    lbl.setText(String.format("%s:", currAttribute.getName().toUpperCase()));
                    lbl.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                    lbl.setTypeface(lbl.getTypeface(), Typeface.BOLD);
                    attrContainer.addView(lbl);

                    spinner.setId(getDynamicViewUniqueId());


                    attrContainer.addView(spinner);

                    if (hasPreSelectedItem) {
                        spinner.setSelection(selectedIndex + 1);
                        addAttributeValue(String.valueOf(currAttribute.getId()),
                                String.valueOf(currAttribute.getValues().get(selectedIndex).getId()),
                                "1", currAttribute.getName(), holder);
                    } else {
                        addAttributeValue(String.valueOf(currAttribute.getId()), "",
                                "1", currAttribute.getName(), holder);
                    }


                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (spinnerArrayValue.get(position).equalsIgnoreCase("")) {
                                holder.mError.setVisibility(View.VISIBLE);
                                addAttributeValue(parent.getTag().toString(), "", "1", currAttribute.getName(), holder);
                            } else {
                                holder.mError.setVisibility(View.GONE);
                                addAttributeValue(parent.getTag().toString(), spinnerArrayValue.get(position), "1",
                                        currAttribute.getName(), holder);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    container.addView(attrContainer);
                }
                break;
                case 2: {

                    /*ArrayList<BeanPostAttributes.BeanProductAttributeValues> values = currAttribute.getAttributeValues();

                    RadioGroup rg = new RadioGroup(mContext);
                    if (Build.VERSION.SDK_INT > 16) {
                        rg.setId(View.generateViewId());
                    }
                    rg.setTag(currAttribute.getId());

                    boolean preSelection = false;
                    for (int v = 0; v < values.size(); v++) {
                        BeanPostAttributes.BeanProductAttributeValues val = values.get(v);

                        RadioButton rb = new RadioButton(mContext);

                        String label = val.getName();
                        *//*if (val.getPriceAdjustmentFormatted() != null && !val.getPriceAdjustmentFormatted().isEmpty()) {
                            label += " [" + val.getPriceAdjustmentFormatted() + "]";
                        }*//*

                        rb.setText(label);
                        if (Build.VERSION.SDK_INT > 16) {
                            rb.setId(View.generateViewId());
                        }
                        rb.setTag(val.getId());

                        if (val.getIsPreSelected().equalsIgnoreCase("true")) {
                            rb.setChecked(true);
                            preSelection = true;
                            addAttributeValue(rg.getTag().toString(), rb.getTag().toString(), false);
                        }

                        rg.addView(rb);
                    }

                    TextView lbl = new TextView(mContext);
                    lbl.setText(currAttribute.getName());
                    container.addView(lbl);

                    container.addView(rg);

                    if (!preSelection) {
                        RadioButton rb = ((RadioButton) rg.getChildAt(0));
                        rb.setChecked(true);
                        addAttributeValue(rg.getTag().toString(), rb.getTag().toString(), false);
                    }

                    rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            RadioButton rb = (RadioButton) layoutView.findViewById(checkedId);
                            addAttributeValue(group.getTag().toString(), rb.getTag().toString(), false);
                            //ProductAttributesUpdate(prd.getId(), false);
                        }
                    });*/
                }
                break;
                case 3: {
                    /*TextView lbl = new TextView(mContext);
                    lbl.setText(currAttribute.getName());
                    container.addView(lbl);

                    ArrayList<BeanPostAttributes.BeanProductAttributeValues> values = currAttribute.getAttributeValues();
                    for (int v = 0; v < values.size(); v++) {
                        BeanPostAttributes.BeanProductAttributeValues val = values.get(v);

                        CheckBox cb = new CheckBox(mContext);

                        String label = val.getName();
                        *//*if (val.getPriceAdjustmentFormatted() != null && !val.getPriceAdjustmentFormatted().isEmpty()) {
                            label += " [" + val.getPriceAdjustmentFormatted() + "]";
                        }*//*

                        cb.setTag(currAttribute.getId() + ":" + val.getId());
                        cb.setText(label);

                        if (Build.VERSION.SDK_INT > 16) {
                            cb.setId(View.generateViewId());
                        }

                        if (val.getIsPreSelected().equalsIgnoreCase("true")) {
                            cb.setChecked(true);
                            addAttributeValue(String.valueOf(currAttribute.getId()), String.valueOf(val.getId()), false);
                        }

                        container.addView(cb);

                        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                String[] vals = buttonView.getTag().toString().split(":");
                                if (vals != null) {
                                    if (isChecked) {
                                        addAttributeValue(vals[0], vals[1], true);
                                        //ProductAttributesUpdate(prd.getId(), false);
                                    } else {
                                        deleteAttributeValue(vals[0], vals[1]);
                                        //ProductAttributesUpdate(prd.getId(), false);
                                    }
                                }
                            }
                        });
                    }*/
                }
                break;
                case 40: {
                    /*ArrayList<BeanPostAttributes.BeanProductAttributeValues> values = currAttribute.getAttributeValues();

                    RadioGroup rg = new RadioGroup(mContext);
                    rg.setOrientation(LinearLayout.HORIZONTAL);
                    rg.setPadding(0,20,0,0);
                    if (Build.VERSION.SDK_INT > 16) {
                        rg.setId(View.generateViewId());
                    }
                    rg.setTag(currAttribute.getId());

                    boolean preSelection = false;
                    for (int v = 0; v < values.size(); v++) {
                        BeanPostAttributes.BeanProductAttributeValues val = values.get(v);

                        RadioButton rb = new RadioButton(mContext);
                        rb.setPadding(0,0,10,0);
                        rb.setButtonDrawable(getColorSquareDrawable(val.getColorSquaresRgb()));

                        String label = val.getName();
                        *//*if (val.getPriceAdjustmentFormatted() != null && !val.getPriceAdjustmentFormatted().isEmpty()) {
                            label += " [" + val.getPriceAdjustmentFormatted() + "]";
                        }*//*

                        //rb.setText(label);
                        if (Build.VERSION.SDK_INT > 16) {
                            rb.setId(View.generateViewId());
                        }
                        rb.setTag(val.getId());

                        if (val.getIsPreSelected().equalsIgnoreCase("true")) {
                            rb.setChecked(true);
                            preSelection = true;
                            addAttributeValue(rg.getTag().toString(), rb.getTag().toString(), false);
                        }

                        rg.addView(rb);
                    }

                    TextView lbl = new TextView(mContext);
                    lbl.setText(currAttribute.getName());
                    container.addView(lbl);

                    container.addView(rg);

                    if (!preSelection) {
                        RadioButton rb = ((RadioButton) rg.getChildAt(0));
                        rb.setChecked(true);
                        addAttributeValue(rg.getTag().toString(), rb.getTag().toString(), false);
                    }

                    rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            RadioButton rb = (RadioButton) layoutView.findViewById(checkedId);
                            addAttributeValue(group.getTag().toString(), rb.getTag().toString(), false);
                            //ProductAttributesUpdate(prd.getId(), false);
                        }
                    });*/
                }
                break;
            }

            //break;
        }
    }

    private void addAttributeValue(String attributeId, String attributeValue,
                                   String controlType,
                                   String attributeName,
                                   AssociateProductAdapter.AssociateProductViewHolder holder) {

        int indexToRemove = CheckForDuplicate(attributeId, holder);
        if (indexToRemove >= 0)
            holder.mSelectedAttributes.remove(indexToRemove);

        UserSelectedAttribute selectedAttribute = new UserSelectedAttribute(attributeId,
                attributeValue, controlType, "", attributeName);

        holder.mSelectedAttributes.add(selectedAttribute);
    }

    private int CheckForDuplicate(String attributeId, AssociateProductAdapter.AssociateProductViewHolder holder) {
        int indexFound = -1;
        for (int i = 0; i < holder.mSelectedAttributes.size(); i++) {
            UserSelectedAttribute curr = holder.mSelectedAttributes.get(i);

            if (curr.getAttributeId().equalsIgnoreCase(attributeId)) {
                indexFound = i;
                break;
            }
        }
        return indexFound;
    }

    void deleteAttributeValue(String attributeMappingId, String attributeValue) {

        /*String valJoined = productSelectedAttributes.get(attributeMappingId);
        if (valJoined.contains(",")) {

            String finalValue = valJoined;

            if (finalValue.contains(attributeValue + ",")) {
                finalValue = finalValue.replace(attributeValue + ",", "");
            } else {
                finalValue = finalValue.replace("," + attributeValue, "");
            }

            productSelectedAttributes.put(attributeMappingId, finalValue);

        } else {
            productSelectedAttributes.remove(attributeMappingId);
        }*/
    }

    public StateListDrawable getColorSquareDrawable(String color) {

        int width = 60;
        int height = 60;

        if (color == null) {
            color = "#c5c5c5";
        }

        int parsedColor = Color.parseColor(color);

        ShapeDrawable bg = new ShapeDrawable(new OvalShape());
        bg.getPaint().setColor(parsedColor);
        bg.setIntrinsicWidth(width);
        bg.setIntrinsicHeight(height);

        ShapeDrawable selectedBg = new ShapeDrawable(new OvalShape());
        selectedBg.getPaint().setColor(parsedColor);
        selectedBg.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
        selectedBg.getPaint().setStrokeWidth(2);
        selectedBg.setIntrinsicWidth(width);
        selectedBg.setIntrinsicHeight(height);

        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_checked}, selectedBg);
        stateListDrawable.addState(StateSet.WILD_CARD, bg);

        return stateListDrawable;
    }

    private int getDynamicViewUniqueId() {
        if (Build.VERSION.SDK_INT > 16) {
            return View.generateViewId();
        } else {
            return AppHelper.generateViewId();
        }
    }

    class AssociateProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public int pid;
        ImageView mImageView;
        TextView mTitle, mPrice, mDescription, mError;
        Button mAddToCartButton;
        boolean hasAttributes = false;
        LinearLayout mAttributePanel;
        List<UserSelectedAttribute> mSelectedAttributes = new ArrayList<>();

        AssociateProductViewHolder(View itemView) {
            super(itemView);

            mImageView = (ImageView) itemView.findViewById(R.id.lbl_Image);
            mTitle = (TextView) itemView.findViewById(R.id.lbl_title);
            mPrice = (TextView) itemView.findViewById(R.id.lbl_price);
            mDescription = (TextView) itemView.findViewById(R.id.lbl_description);
            mAddToCartButton = (Button) itemView.findViewById(R.id.btn_addtocart);
            mAttributePanel = (LinearLayout) itemView.findViewById(R.id.pnl_attributes);
            mError = (TextView) itemView.findViewById(R.id.lbl_error);

            AppHelper.applyPlayFairDisplayBoldFont(mTitle);
            AppHelper.applyPlayFairDisplayBoldFont(mPrice);
            AppHelper.applyRobotoBoldFont(mAddToCartButton);

            mAddToCartButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btn_addtocart) {
                boolean hasError = false;

                if (hasAttributes) {
                    try {

                        String defaultPrefix = "product_attribute_";

                        JSONObject attributeArray = new JSONObject();
                        if (mSelectedAttributes.size() > 0) {
                            for (int i = 0; i < mSelectedAttributes.size(); i++) {

                                UserSelectedAttribute currAttribute = mSelectedAttributes.get(i);
                                if (currAttribute.getAttributeValue().equalsIgnoreCase("")) {
                                    hasError = true;
                                    mError.setText(String.format(mContext.getString(R.string.please_Select), currAttribute.getAttributeName().toLowerCase()));
                                    mError.setVisibility(View.VISIBLE);
                                    break;
                                } else {
                                    attributeArray.put(defaultPrefix + currAttribute.getAttributeId(), currAttribute.getAttributeValue());
                                }
                            }
                        }

                        if (!hasError) {
                            mAddToCartButton.setEnabled(false);
                            mAddtoCartAsynctask = new AddToCartAsyncTask(pid, 1, 1, mPrice.getText().toString(), attributeArray);
                        }


                    } catch (Exception e) {
                        GoMobileApp.Toast(e.getMessage());
                    }
                } else {
                    mAddtoCartAsynctask = new AddToCartAsyncTask(pid, 1, 1, mPrice.getText().toString());
                }

                if (!hasError) {
                    mAddtoCartAsynctask.addOnResultListener(new AsyncTaskResultListener<Boolean>() {
                        @Override
                        public void response(AsyncTaskResult<Boolean> response) {
                            if (!response.hasException()) {
                                mAddToCartButton.setEnabled(true);

                                if (mContext instanceof RelatedProductsActivity) {
                                    ((RelatedProductsActivity) mContext).refreshShopCartBadge();
                                    ((RelatedProductsActivity) mContext).showMiniShopCart(pid);
                                }


                            } else {
                                GoMobileApp.Toast(response.getException().toString());
                            }
                        }
                    });
                    mAddtoCartAsynctask.execute();
                }
            }
        }
    }

}

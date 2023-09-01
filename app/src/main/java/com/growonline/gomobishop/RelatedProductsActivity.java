package com.growonline.gomobishop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.growonline.gomobishop.adapter.ProductsDetailAdapter;
import com.growonline.gomobishop.control.ViewPagerFixed;
import com.growonline.gomobishop.fragment.ExpandingFragment;
import com.growonline.gomobishop.fragment.SingleProductDetailFragment;
import com.growonline.gomobishop.fragment.SingleProductDetailFragmentPush;
import com.growonline.gomobishop.model.BeanProductDetail;
import com.growonline.gomobishop.model.Product;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.util.AppConstant;

import java.util.ArrayList;


public class RelatedProductsActivity extends BaseActivity implements ExpandingFragment.OnExpandingClickListener {

    public Vendor mVendor;
    private ViewPagerFixed pager;
    private ProductsDetailAdapter adapter;
    private ArrayList<Product> allProduct;
    private int productId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_related_products);
        pager = (ViewPagerFixed) findViewById(R.id.detail_viewPager);

        Intent i = getIntent();
        productId = i.getIntExtra(AppConstant.INTENT_PRODUCT_ID, 0);
        String title = i.getStringExtra(AppConstant.INTENT_TITLE);
        mVendor = i.getParcelableExtra(AppConstant.INTENT_VENDOR);
        if (title == null)
            DisableSecondTitleRow();
        else
            setToolBarTitle(title);

        if (i.getParcelableArrayListExtra(AppConstant.INTENT_PRODUCT) != null) {
            allProduct = i.getParcelableArrayListExtra(AppConstant.INTENT_PRODUCT);
        } else {
            allProduct = new ArrayList<>();
            Product a = new Product();
            a.setId(productId);
            allProduct.add(a);
        }


        adapter = new ProductsDetailAdapter(getSupportFragmentManager(), getAllProductIds(), mVendor);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(2);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setToolBarTitle(allProduct.get(position).getName());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        int startWithIndex = getStartupProductIndex();
        pager.setCurrentItem(startWithIndex, false);
        if (allProduct.get(startWithIndex).getName() != null)
            setToolBarTitle(allProduct.get(startWithIndex).getName());


    }

    public Vendor getmVendor() {
        return mVendor;
    }

    @Override
    public void onExpandingClick(View v, BeanProductDetail product) {
        Intent i = new Intent(this, RelatedProductsActivity.class);
        i.putExtra(AppConstant.INTENT_VENDOR, mVendor);
        i.putExtra(AppConstant.INTENT_PRODUCT_ID, product.getId());
        i.putExtra(AppConstant.INTENT_TITLE, product.getName());
        startActivity(i);
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    private ArrayList<Integer> getAllProductIds() {
        ArrayList<Integer> res = new ArrayList<>();
        if (allProduct != null && allProduct.size() > 0) {
            for (int i = 0; i < allProduct.size(); i++) {
                res.add(allProduct.get(i).getId());
            }
        }
        return res;
    }

    Integer getStartupProductIndex() {
        int res = 0;
        for (int i = 0; i < allProduct.size(); i++) {
            if (productId == allProduct.get(i).getId()) {
                res = i;
                break;
            }
        }
        return res;
    }

    @Override
    public void onBackPressed() {
        if (adapter.getCurrentFragment() instanceof SingleProductDetailFragment) {
            if (((SingleProductDetailFragment) adapter.getCurrentFragment()).IsShopTheLookVisible()) {
                ((SingleProductDetailFragment) adapter.getCurrentFragment()).HideShopTheLook();
                return;
            }
        } else if (adapter.getCurrentFragment() instanceof SingleProductDetailFragmentPush) {
            if (((SingleProductDetailFragmentPush) adapter.getCurrentFragment()).IsShopTheLookVisible()) {
                ((SingleProductDetailFragmentPush) adapter.getCurrentFragment()).HideShopTheLook();
                return;
            }
        }
        super.onBackPressed();
    }
}
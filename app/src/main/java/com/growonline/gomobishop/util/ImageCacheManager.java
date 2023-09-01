package com.growonline.gomobishop.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.growonline.gomobishop.R;
import com.growonline.gomobishop.control.AspectRatioImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.io.File;

/**
 * Created by Basit on 9/18/2016.
 */
public class ImageCacheManager {
    private static ImageCacheManager mInstance;
    private Picasso mPicasso;
    private Context context;
    private int radiusArr[] = new int[]{25, 23, 21, 19, 17};

    private ImageCacheManager(Context context) {
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.defaultBitmapConfig(Bitmap.Config.RGB_565);
        builder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                exception.printStackTrace();
            }
        });
        mPicasso = builder.build();
        this.context = context;
    }

    public static ImageCacheManager Initialize(Context context) {
        if (mInstance == null) {
            mInstance = new ImageCacheManager(context);
            return mInstance;
        } else
            return mInstance;
    }

    public Picasso getmPicasso() {
        return mPicasso;
    }

    public void loadImage(int resourceId, ImageView mFirstImageView, final ProgressBar progressBar) {
        mPicasso.load(resourceId).into(mFirstImageView, new Callback() {
            @Override
            public void onError() {

            }

            @Override
            public void onSuccess() {
                if (progressBar == null) return;
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void loadImageWithFit(int resourceId, ImageView mFirstImageView, final ProgressBar progressBar) {
        mPicasso.load(resourceId).fit().into(mFirstImageView, new Callback() {
            @Override
            public void onError() {

            }

            @Override
            public void onSuccess() {
                if (progressBar == null) return;
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void loadImage(Uri uri, ImageView mFirstImageView, final ProgressBar progressBar) {
        mPicasso.load(uri).placeholder(R.drawable.img_placeholder).into(mFirstImageView, new Callback() {
            @Override
            public void onError() {
                if (progressBar == null) return;
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess() {
                if (progressBar == null) return;
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void loadImageWithDynamicRatio(Uri uri, final AspectRatioImageView mFirstImageView, final ProgressBar progressBar) {
        mPicasso.load(uri).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                mFirstImageView.setHeightRatio((float) height / width);
                mFirstImageView.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                if (progressBar == null) return;
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                if (progressBar == null) return;
                progressBar.setVisibility(View.GONE);
            }
        });
    }


    public void loadImageRectangle(Uri uri, ImageView mFirstImageView, final ProgressBar progressBar) {
        mPicasso.load(uri).placeholder(R.drawable.img_placeholder_reg).into(mFirstImageView, new Callback() {
            @Override
            public void onError() {
                if (progressBar == null) return;
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess() {
                if (progressBar == null) return;
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void loadImageAndFit(Uri uri, ImageView mFirstImageView, final ProgressBar progressBar) {
        mPicasso.load(uri).placeholder(R.drawable.img_placeholder).fit().into(mFirstImageView, new Callback() {
            @Override
            public void onError() {

            }

            @Override
            public void onSuccess() {
                if (progressBar == null) return;
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void loadImageForDiscoverTab(Uri uri, final ImageView mFirstImageView, final ProgressBar progressBar) {
        mPicasso.load(uri).placeholder(R.drawable.img_placeholder).into(mFirstImageView, new Callback() {
            @Override
            public void onError() {

            }

            @Override
            public void onSuccess() {
                if (mFirstImageView != null)
                    mFirstImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                if (progressBar == null) return;
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void loadImageWithCenterCrop(Uri uri, ImageView mFirstImageView, final ProgressBar progressBar) {
        mPicasso.load(uri).centerCrop().fit().into(mFirstImageView, new Callback() {
            @Override
            public void onError() {
            }

            @Override
            public void onSuccess() {
                if (progressBar == null) return;
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void loadImageWithFit(Uri uri, ImageView mFirstImageView) {
        mPicasso.load(uri).fit().placeholder(R.drawable.img_placeholder).into(mFirstImageView);
    }

    public void loadImage(Uri uri, ImageView mFirstImageView) {
        mPicasso.load(uri).placeholder(R.drawable.img_placeholder).into(mFirstImageView);
    }

    public void loadBlurImage(Uri uri, final ImageView imageView) {
        mPicasso.load(uri).placeholder(R.drawable.img_placeholder).fit().into(imageView, new Callback() {
            @Override
            public void onError() {

            }

            @Override
            public void onSuccess() {
                BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                Bitmap blurred = blurRenderScript(bitmap, radiusArr[4]);//second parametre is radius
                imageView.setImageBitmap(blurred);                          //radius decide blur amount
            }
        });
    }

    public void loadImageWithFitFromFile(File file, ImageView imageView, boolean invalidateCache) {
        if (invalidateCache)
            mPicasso.load(file).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).fit().into(imageView);
        else
            mPicasso.load(file).fit().into(imageView);
    }

    public void loadImageWithFitFromFile(String diskPath, ImageView imageView, boolean invalidateCache) {
        if (invalidateCache)
            mPicasso.load(diskPath).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).fit().into(imageView);
        else
            mPicasso.load(diskPath).fit().into(imageView);
    }

    public void loadBackgroundImageWithFitFromFile(File file, final View view, final Context context, boolean invalidateCache) {
        if (invalidateCache) {
            mPicasso.load(file).memoryPolicy(MemoryPolicy.NO_CACHE).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    setBitmapAsBackground(context, view, bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
        } else {
            //mPicasso.load(file).fit().into(imageView);
        }
    }

    void setBitmapAsBackground(Context context, View view, Bitmap bitmap) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(new BitmapDrawable(context.getResources(), bitmap));
        } else {
            view.setBackgroundDrawable(new BitmapDrawable(context.getResources(), bitmap));
        }
    }

    public void loadImageWithCenterCrop(int resourceId, ImageView mFirstImageView) {
        mPicasso.load(resourceId).centerCrop().fit().noFade().into(mFirstImageView, new Callback() {
            @Override
            public void onSuccess() {
                Log.e("ie", "suuceess");
            }

            @Override
            public void onError() {
                Log.e("ie", "loading image error");
            }
        });
    }

    @SuppressLint("NewApi")
    private Bitmap blurRenderScript(Bitmap smallBitmap, int radius) {

        try {
            smallBitmap = RGB565toARGB888(smallBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Bitmap bitmap = Bitmap.createBitmap(
                smallBitmap.getWidth(), smallBitmap.getHeight(),
                Bitmap.Config.ARGB_8888);

        RenderScript renderScript = RenderScript.create(context);

        Allocation blurInput = Allocation.createFromBitmap(renderScript, smallBitmap);
        Allocation blurOutput = Allocation.createFromBitmap(renderScript, bitmap);

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript,
                Element.U8_4(renderScript));
        blur.setInput(blurInput);
        blur.setRadius(radius); // radius must be 0 < r <= 25
        blur.forEach(blurOutput);

        blurOutput.copyTo(bitmap);
        renderScript.destroy();

        return bitmap;

    }

    private Bitmap RGB565toARGB888(Bitmap img) throws Exception {
        int numPixels = img.getWidth() * img.getHeight();
        int[] pixels = new int[numPixels];

        //Get JPEG pixels.  Each int is the color values for one pixel.
        img.getPixels(pixels, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        //Create a Bitmap of the appropriate format.
        Bitmap result = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.ARGB_8888);

        //Set RGB pixels.
        result.setPixels(pixels, 0, result.getWidth(), 0, 0, result.getWidth(), result.getHeight());
        return result;
    }

    public class TopCenterTransform implements Transformation {
        private ImageView mImageView;

        public TopCenterTransform(ImageView imageview) {
            this.mImageView = imageview;
        }

        @Override
        public Bitmap transform(Bitmap source) {
            int targetWidth = mImageView.getWidth();

            double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
            int targetHeight = (int) (targetWidth * aspectRatio);
            Bitmap result = Bitmap.createScaledBitmap(source, targetHeight, targetWidth, false);
            if (result != source) {
                // Same bitmap is returned if sizes are the same
                source.recycle();
            }
            return result;
        }

        @Override
        public String key() {
            return "transformation" + " desiredWidth";
        }

    }


}

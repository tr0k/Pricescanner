package pl.tr0k.pricescanner;

import android.app.Application;
import android.content.Context;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * Created by Emil Kleszcz on 2014-09-29.
 */
public class PriceScannerApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(getApplicationContext());
    }

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(setupImageOptions()).build();
        ImageLoader.getInstance().init(config);
    }

    private static DisplayImageOptions setupImageOptions(){
        return new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.NONE)
                .showImageOnFail(R.drawable.noimage)
                .showImageForEmptyUri(R.drawable.noimage)
                .build();
    }
}

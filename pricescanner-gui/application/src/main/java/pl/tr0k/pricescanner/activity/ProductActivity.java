package pl.tr0k.pricescanner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.inject.Inject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import pl.tr0k.pricescanner.R;
import pl.tr0k.pricescanner.async.RetrieveProductTask;
import pl.tr0k.pricescanner.common.dto.ProductDTO;
import pl.tr0k.pricescanner.common.util.MoneyUtils;
import pl.tr0k.pricescanner.common.util.ParameterException;
import pl.tr0k.pricescanner.event.GetProductDTOEvent;
import roboguice.inject.ContentView;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;

@ContentView(R.layout.product)
public class ProductActivity extends EanHandlingActivity {

    private static String TAG = ProductActivity.class.getName();

    private static final int REQUEST_CODE = 0;

    @InjectView(R.id.priceText)
    private TextView priceText;

    @InjectView(R.id.nameText)
    private TextView nameText;

    @InjectView(R.id.describeText)
    private TextView describeText;

    @InjectView(R.id.imageView)
    private ImageView productImage;

    @InjectView(R.id.productInfoLayout)
    private LinearLayout productInfoLayout;

    @InjectResource(R.string.currency)
    private String currency;

    @InjectResource(R.string.images_service_url)
    private String imageServerURL;

    @Inject
    private Bus eventBus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        eventBus.register(this);
    }

    @Override
    public void onEanDecoded(String ean) {
        productInfoLayout.setVisibility(View.INVISIBLE);
        new RetrieveProductTask(ean, this).execute();
    }

    public void sendToAdvertsActivity(View view) {
        Intent intent = new Intent(this, AdvertsActivity.class);
        startActivityForResult(intent, 0);
    }

    public void sendToBasketActivity(View view) {
        Intent intent = new Intent(this, ProductsBasketActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        productInfoLayout.setVisibility(View.INVISIBLE);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data.hasExtra("ean")) {
                new RetrieveProductTask(data.getStringExtra("ean"), this).execute();
                setContentView(R.layout.product);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void getProductAfterScan(GetProductDTOEvent event){
        if(!event.getDestActivity().equals(this)) return;
        ProductDTO productDTO = event.getProductDTO();

        try {
            String amount = MoneyUtils.getAmountFromLong(productDTO.getPrice());
            priceText.setText(amount + " " + currency);
        } catch (ParameterException e) {
            Log.e(TAG, "Could not convert price", e);
        }

        String imageURL = String.format(imageServerURL, productDTO.getImageId());
        ImageLoader.getInstance().displayImage(imageURL, productImage);

        nameText.setText(productDTO.getName());
        describeText.setText(productDTO.getDescription());
        productInfoLayout.setVisibility(View.VISIBLE);
    }

}
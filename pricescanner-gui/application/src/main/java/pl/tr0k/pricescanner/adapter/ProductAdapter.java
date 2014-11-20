package pl.tr0k.pricescanner.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import pl.tr0k.pricescanner.R;
import pl.tr0k.pricescanner.common.dto.ProductDTO;
import roboguice.RoboGuice;
import roboguice.inject.InjectResource;

import java.util.ArrayList;


/**
 * Created by Emil Kleszcz on 2014-09-22.
 */
public class ProductAdapter extends ArrayAdapter<ProductDTO> {

    private final Activity context;

    @InjectResource(R.string.images_service_url)
    private String imageServerURL;

    public ProductAdapter(Context context, ArrayList<ProductDTO> product) {
        super(context, 0, product);
        RoboGuice.getInjector(context).injectMembers(this);
        this.context = (Activity) context;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View productAdapterView = convertView;

        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            productAdapterView = inflater.inflate(R.layout.product_adapter, null);
            productAdapterView.setTag(initializeHolder(productAdapterView));
        }

        final ViewHolder holder = (ViewHolder) productAdapterView.getTag();
        addProductToBasket(holder, position);

        return productAdapterView;
    }

    private void addProductToBasket(final ViewHolder holder, final int position) {
        if (this.getCount() == 0) return;

        StringBuilder fullProductName = new StringBuilder();
        fullProductName.append(context.getString(R.string.product_adapter_name));
        fullProductName.append(getItem(position).getName());

        StringBuilder fullPrice = new StringBuilder();
        fullPrice.append(context.getString(R.string.product_adapter_price));
        fullPrice.append(getItem(position).getPrice().toString());

        holder.product_price_adapter_tv.setText(fullPrice);
        holder.product_name_adapter_tv.setText(fullProductName);

        final String imageURL = String.format(imageServerURL, getItem(position).getImageId());
        context.runOnUiThread(new Runnable() {
            public void run() {
                ImageLoader.getInstance().displayImage(imageURL, holder.image);
            }
        });
    }

    private ViewHolder initializeHolder(View productAdapterView) {
        ViewHolder holder = new ViewHolder();
        holder.image = (ImageView) productAdapterView.findViewById(R.id.adapter_imageView);
        holder.product_name_adapter_tv = (TextView) productAdapterView.findViewById(R.id.product_name_adapter_tv);
        holder.product_price_adapter_tv = (TextView) productAdapterView.findViewById(R.id.product_price_adapter_tv);
        return holder;
    }

    static class ViewHolder {
        public ImageView image;
        public TextView product_name_adapter_tv;
        public TextView product_price_adapter_tv;
    }
}
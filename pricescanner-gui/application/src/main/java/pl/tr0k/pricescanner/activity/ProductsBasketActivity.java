package pl.tr0k.pricescanner.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import com.google.inject.Inject;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import pl.tr0k.pricescanner.R;
import pl.tr0k.pricescanner.adapter.ProductAdapter;
import pl.tr0k.pricescanner.async.RetrieveProductTask;
import pl.tr0k.pricescanner.common.dto.ProductDTO;
import pl.tr0k.pricescanner.event.GetProductDTOEvent;
import pl.tr0k.pricescanner.model.IProductsBasketManager;
import pl.tr0k.pricescanner.model.ProductsBasketManager;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * Created by Emil Kleszcz on 2014-09-10.
 */
@ContentView(R.layout.basket)
public class ProductsBasketActivity extends EanHandlingActivity {

    @InjectView(R.id.basket_gv)
    private GridView basket_gv;

    @InjectView(R.id.busket_sum_num_tv)
    private TextView basket_sum_num_tv;

    @Inject
    private Bus eventBus;

    @Inject
    public IProductsBasketManager productsBasketManager;

    private ProductAdapter productAdapter;

    private AdapterView.OnItemClickListener onItemClickListener =
            new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Intent intent = new Intent(getApplicationContext(), ProductDetailsActivity.class);
//                    startActivity(intent);
                }
            };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productsBasketManager = new ProductsBasketManager();
        eventBus.register(this);
        productAdapter = new ProductAdapter(this, productsBasketManager.getBasket());
        basket_gv.setAdapter(productAdapter);
        basket_gv.setFocusableInTouchMode(false);
        basket_gv.setFocusable(false);
        basket_gv.setOnItemClickListener(onItemClickListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
    }

    @Override
    public void onEanDecoded(String ean) {
        new RetrieveProductTask(ean, this).execute();
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void getProductAfterScan(GetProductDTOEvent event) {
        ProductDTO productDTO = event.getProductDTO();
        if (!event.getDestActivity().equals(this)) return;
        productsBasketManager.addProduct(productDTO);
        updateBasket();
    }

    private void updateBasket() {
        productAdapter.notifyDataSetChanged();
        basket_sum_num_tv.setText(productsBasketManager.priceSum().toString());
    }


    public void backToHome(View view) {
        finish();
    }
}
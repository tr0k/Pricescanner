package pl.tr0k.pricescanner.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import pl.tr0k.pricescanner.R;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;

/**
 * Created by Emil Kleszcz on 2014-10-21.
 */
@ContentView(R.layout.product_details)
public class ProductDetailsActivity extends RoboActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean
    onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            super.onKeyDown(keyCode, event);
            return true;
        }
        // TODO przejscie do tylu
        return true;
    }
}

package pl.tr0k.pricescanner.activity;

import android.content.Intent;

/**
 * Created by Emil Kleszcz on 2014-09-19.
 */
public class DefaultEanHandlingActivity extends EanHandlingActivity {

    @Override
    public void onEanDecoded(String ean) {
        Intent intent = getIntent();
        intent.putExtra("ean", ean);
        setResult(RESULT_OK, intent);
        finish();
    }
}

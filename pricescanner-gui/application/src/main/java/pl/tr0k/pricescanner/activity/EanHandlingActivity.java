package pl.tr0k.pricescanner.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import com.google.inject.Inject;
import pl.tr0k.pricescanner.model.scanner.IKeyboardEventDecoder;
import roboguice.activity.RoboActivity;

public abstract class EanHandlingActivity extends RoboActivity {

    @Inject
    private IKeyboardEventDecoder keyboardEventEanDecoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            super.onKeyDown(keyCode, event);
            return true;
        }

        String ean = keyboardEventEanDecoder.decode(event);

        if (ean != null) {
            onEanDecoded(ean);
        }

        return true;
    }

    public abstract void onEanDecoded(String ean);
}

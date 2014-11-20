package pl.tr0k.pricescanner.model.scanner;

import android.view.KeyEvent;
import com.google.inject.Singleton;

/**
 * Created by Emil Kleszcz on 2014-10-27.
 */
@Singleton
public class KeyboardEventEanDecoder implements IKeyboardEventDecoder {

    private StringBuilder ean = new StringBuilder();

    public String decode(KeyEvent keyEvent) {

        if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            String result = ean.toString();
            ean = new StringBuilder();
            return result;
        }
        else if (keyEvent.getKeyCode() >= KeyEvent.KEYCODE_0 &&
                KeyEvent.KEYCODE_9 >= keyEvent.getKeyCode()) {
            ean.append(Character.toString(keyEvent.getNumber()));
        }

        return null;
    }

}

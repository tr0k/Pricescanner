package pl.tr0k.pricescanner.model.scanner;

import android.view.KeyEvent;

import java.util.Random;

/**
 * Created by Emil Kleszcz on 2014-10-20.
 */
public class KeyboardEventDecoderFake implements IKeyboardEventDecoder {

    private static final String[] eans = {
        "5390660180385",
        "1224104458"
    };

    public String decode(KeyEvent keyEvent) {
        return randomEan();
    }

    private String randomEan(){
        int randIndex = new Random().nextInt(eans.length);
        return eans[randIndex];
    }
}

package pl.tr0k.pricescanner.model.scanner;

import android.view.KeyEvent;

/**
 * @author Marcin
 */
public interface IKeyboardEventDecoder {

    public String decode(final KeyEvent keyEvent);
}

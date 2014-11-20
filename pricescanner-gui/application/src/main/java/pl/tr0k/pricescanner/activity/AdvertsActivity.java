package pl.tr0k.pricescanner.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import pl.tr0k.pricescanner.R;
import roboguice.inject.ContentView;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;

/**
 * Created by Emil Kleszcz on 2014-09-09.
 */
@ContentView(R.layout.adverts)
public class AdvertsActivity extends DefaultEanHandlingActivity {

    private static String TAG = AdvertsActivity.class.getSimpleName();

    private Handler timerHandler = new Handler();

    private int currentAdvertId;

    @InjectResource(R.integer.delay_millis)
    public int delayMillis;

    @InjectView(R.id.advertImageView)
    private ImageView advert;

    private Runnable timerRunnable = new Runnable() {
        public void run() {
            showNextAdvert();
            timerHandler.postDelayed(this, delayMillis);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        currentAdvertId = 1;
        timerHandler.postDelayed(timerRunnable, delayMillis);
    }

    private void showNextAdvert(){
        advert.setImageResource(getNextAdvertId());
    }

    private int getNextAdvertId(){
        currentAdvertId = (currentAdvertId == 5 ? 1 : ++currentAdvertId);
        String nextAdvert = "advert" + String.valueOf(currentAdvertId);
        return this.getResources().getIdentifier(nextAdvert, "drawable", this.getPackageName());
    }
}

package pl.tr0k.pricescanner.async;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.util.Log;
import com.google.inject.Inject;
import com.squareup.otto.Bus;
import pl.tr0k.pricescanner.R;
import pl.tr0k.pricescanner.common.dto.ProductDTO;
import pl.tr0k.pricescanner.event.GetProductDTOEvent;
import pl.tr0k.pricescanner.service.IProductService;
import pl.tr0k.pricescanner.service.exception.NotFoundProductException;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectResource;
import roboguice.util.RoboAsyncTask;

import java.net.ConnectException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Emil Kleszcz on 2014-09-25.
 */
public class RetrieveProductTask extends RoboAsyncTask<ProductDTO> {

    private String INNER_TAG = RetrieveProductTask.class.getName();

    @InjectResource(R.string.abstractActivity_productNotFound)
    private String productNotFoundText;

    @InjectResource(R.string.abstractActivity_loading)
    private String loadingText;

    @InjectResource(R.string.abstractActivity_connectionError)
    private String serverErrorText;

    @Inject
    private IProductService productService;

    @Inject
    Bus eventBus;

    private RoboActivity activity;

    private ProductDTO productDTO;

    private String ean;

    private ProgressDialog pDialog;

    public RetrieveProductTask(String ean, RoboActivity activity) {
        super(activity.getApplicationContext());
        this.ean = ean;
        this.activity = activity;
    }

    public ProductDTO call() throws Exception {
        try {
            showLoadingDialog();
            stopAfterDelay(4);
            this.productDTO = productService.retrieveProductByEan(ean);
            pDialog.dismiss();
            return productDTO;
        } catch (NotFoundProductException e) {
            showAlertDialog(productNotFoundText);
            Log.e(INNER_TAG, "Could not find specified product.", e);
        } catch (Exception e) {
            showAlertDialog(serverErrorText);
            Log.e(INNER_TAG, "Could not connect to server", e);
        }
        pDialog.dismiss();
        return null;
    }

    private void stopAfterDelay(long delaySeconds) throws ConnectException{
        final ScheduledExecutorService worker =
                Executors.newSingleThreadScheduledExecutor();
        Runnable task = new Runnable() {
            public void run() {
                Log.e(INNER_TAG, "Could not connect to server");
                pDialog.dismiss();
            }
        };
        worker.schedule(task, delaySeconds, TimeUnit.SECONDS);
    }

    @Override
    protected void onSuccess(ProductDTO productDTO) {
        if(productDTO != null){
            eventBus.post(new GetProductDTOEvent(productDTO, activity));
        }
    }

    private void showLoadingDialog() {

        activity.runOnUiThread(new Runnable() {
            public void run() {
                pDialog = new ProgressDialog(activity);
                pDialog.setCancelable(false);
                pDialog.setMessage(loadingText);
                pDialog.show();
            }
        });
    }

    public void showAlertDialog(final String msg) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage(msg)
                        .setTitle("Komunikat")
                        .setCancelable(false)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
}
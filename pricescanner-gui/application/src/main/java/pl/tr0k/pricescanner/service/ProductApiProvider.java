package pl.tr0k.pricescanner.service;

import com.google.inject.Provider;
import pl.tr0k.pricescanner.R;
import pl.tr0k.pricescanner.service.exception.RetrofitErrorHandler;
import retrofit.RestAdapter;
import roboguice.inject.InjectResource;

/**
 * Created by Emil Kleszcz on 2014-10-16.
 */
public class ProductApiProvider implements Provider<IProductAPI> {

    @InjectResource(R.string.server_url)
    private String serverBaseUrl;

    public IProductAPI get() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(serverBaseUrl)
                .setErrorHandler(new RetrofitErrorHandler())
                .build();
        return restAdapter.create(IProductAPI.class);
    }
}

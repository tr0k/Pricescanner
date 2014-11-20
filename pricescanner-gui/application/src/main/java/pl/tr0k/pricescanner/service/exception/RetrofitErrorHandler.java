package pl.tr0k.pricescanner.service.exception;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Emil Kleszcz on 2014-10-16.
 */
public class RetrofitErrorHandler implements ErrorHandler {

    public Throwable handleError(RetrofitError cause) {
        Response r = cause.getResponse();
        if(r == null){
            return new NoConnectionException(cause);
        }
        else if (r.getStatus() == 404) {
            return new NotFoundProductException(cause);
        }
        return cause;
    }

}

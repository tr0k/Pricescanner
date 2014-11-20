package pl.tr0k.pricescanner.service;

import pl.tr0k.pricescanner.common.dto.ProductDTO;
import retrofit.RetrofitError;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Emil Kleszcz on 2014-10-01.
 */
public interface IProductAPI {

    @GET("/products/{ean}")
    ProductDTO getProductByEan(@Path("ean") String ean) throws RetrofitError;

}

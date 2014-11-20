package pl.tr0k.pricescanner.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import pl.tr0k.pricescanner.common.dto.ProductDTO;
import retrofit.RetrofitError;

/**
 * Author: Emil Kleszcz
 */
@Singleton
public class ProductService implements IProductService {

    @Inject
    IProductAPI productAPI;

    public ProductDTO retrieveProductByEan(String productEan) throws RetrofitError {
        return productAPI.getProductByEan(productEan);
    }
}
package pl.tr0k.pricescanner.service;

import pl.tr0k.pricescanner.common.dto.ProductDTO;
import retrofit.RetrofitError;

public interface IProductService {

    public ProductDTO retrieveProductByEan(String productEan) throws RetrofitError;

}

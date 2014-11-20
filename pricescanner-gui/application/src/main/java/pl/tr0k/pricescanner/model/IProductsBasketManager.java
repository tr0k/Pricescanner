package pl.tr0k.pricescanner.model;

import pl.tr0k.pricescanner.common.dto.ProductDTO;

import java.util.ArrayList;

/**
 * Created by Emil Kleszcz on 2014-10-14.
 */
public interface IProductsBasketManager {

    public Long priceSum();

    public void addProduct(ProductDTO productDTO);

    public void clearBasket();

    public ArrayList<ProductDTO> getBasket();
}

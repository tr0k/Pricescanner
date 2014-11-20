package pl.tr0k.pricescanner.model;

import pl.tr0k.pricescanner.common.dto.ProductDTO;

import java.util.ArrayList;

/**
 * Created by Emil Kleszcz on 2014-10-09.
 */
public class ProductsBasketManager implements IProductsBasketManager {

    private ArrayList<ProductDTO> product;

    public ProductsBasketManager() {
        this.product = new ArrayList<ProductDTO>();
    }

    public void addProduct(ProductDTO productDTO){
        product.add(productDTO);
    }

    public void clearBasket(){
        product.clear();
    }

    public ArrayList<ProductDTO> getBasket() {
        return product;
    }

    public Long priceSum(){
        long sum = 0L;
        for (ProductDTO element : product) {
            sum += element.getPrice();
        }
        return sum;
    }
}

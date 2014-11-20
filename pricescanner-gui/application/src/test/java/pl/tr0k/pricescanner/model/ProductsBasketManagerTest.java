package pl.tr0k.pricescanner.model;

import org.junit.Test;
import pl.tr0k.pricescanner.common.dto.ProductDTO;
import pl.tr0k.pricescanner.model.ProductsBasketManager;

import static org.junit.Assert.assertEquals;

public class ProductsBasketManagerTest {

    @Test
    public void testPriceSum(){
        ProductsBasketManager productsBasketManager  = createProductsBasket(1L, 2L);
        assertEquals(3L, (long)productsBasketManager.priceSum());

        productsBasketManager  = createProductsBasket(1L, -1L);
        assertEquals(0L, (long)productsBasketManager.priceSum());

        productsBasketManager  = createProductsBasket(-3L, -2L);
        assertEquals(-5L, (long)productsBasketManager.priceSum());

        productsBasketManager  = createProductsBasket(0L, 0L);
        assertEquals(0L, (long)productsBasketManager.priceSum());
    }

    private ProductsBasketManager createProductsBasket(Long... prices) {
        ProductsBasketManager productsBasketManager = new ProductsBasketManager();
        for (Long price : prices) {
            ProductDTO productDTO = new ProductDTO("", "", price, "", 0L);
            productsBasketManager.addProduct(productDTO);
        }
        return productsBasketManager;
    }

}
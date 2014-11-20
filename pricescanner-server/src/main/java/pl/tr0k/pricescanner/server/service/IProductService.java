package pl.tr0k.pricescanner.server.service;

import org.springframework.data.domain.Page;
import pl.tr0k.pricescanner.server.domain.Image;
import pl.tr0k.pricescanner.server.domain.Product;

import java.util.List;

public interface IProductService {

    Product findProductByEan(String ean);

    Product findProduct(Long id);

    void saveProduct(Product product);

    Page<Product> findAllProducts(int page, int size);

    Iterable<Product> findAllProducts();

    byte[] getProductImage(String productEan);

    boolean addImageToProduct(String productEan, Image image);

    void deleteProduct(Long id);

    List<Product> findProductByAnyField(int page, int size, String filter);
}

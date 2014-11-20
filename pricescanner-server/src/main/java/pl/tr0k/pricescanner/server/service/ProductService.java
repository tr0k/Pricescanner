package pl.tr0k.pricescanner.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.tr0k.pricescanner.server.domain.Image;
import pl.tr0k.pricescanner.server.domain.Product;
import pl.tr0k.pricescanner.server.domain.repositories.ImageRepository;
import pl.tr0k.pricescanner.server.domain.repositories.ProductRepository;

import java.util.List;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Product findProductByEan(String ean) {
        return productRepository.findByEan(ean);
    }

    @Override
    public Product findProduct(Long id) {
        return productRepository.findOne(id);
    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public Page<Product> findAllProducts(int page, int size) {
        return productRepository.findAll(new PageRequest(page, size));
    }

    @Override
    public Iterable<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public byte[] getProductImage(String productEan) {
        Product product = productRepository.findByEan(productEan);
        if (product == null) {
            return null;
        }
        Image image = product.getImage();
        if (image == null) {
            return null;
        }
        return image.getBytes();
    }

    @Override
    @Transactional
    public boolean addImageToProduct(String productEan, Image image) {
        Product product = productRepository.findByEan(productEan);
        if (product == null) {
            return false;
        }
        product.setImage(image);
        imageRepository.save(image);
        productRepository.save(product);
        return true;
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.delete(id);
    }

    @Override
    public List<Product> findProductByAnyField(int page, int size, String filter) {
        return productRepository.findByAnyField(filter, new PageRequest(page, size));
    }

}

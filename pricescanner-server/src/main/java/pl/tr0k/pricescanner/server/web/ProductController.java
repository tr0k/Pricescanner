package pl.tr0k.pricescanner.server.web;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.tr0k.pricescanner.common.dto.ProductDTO;
import pl.tr0k.pricescanner.server.domain.Image;
import pl.tr0k.pricescanner.server.domain.Product;
import pl.tr0k.pricescanner.server.service.IProductService;
import pl.tr0k.pricescanner.server.web.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/products")
@Controller
public class ProductController {

    @Autowired
    private IProductService productService;

    private final Mapper mapper = new DozerBeanMapper();

    @RequestMapping(value = "/{ean}", method = RequestMethod.GET)
    public @ResponseBody ProductDTO show(@PathVariable("ean") String ean) {
        Product product = productService.findProductByEan(ean);
        if (product == null) {
            throw new NotFoundException();
        }
        ProductDTO productDTO = mapper.map(product, ProductDTO.class);
        productDTO.setImageId(product.getImage() != null ? product.getImage().getId() : null);
        return productDTO;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody ProductDTO create(@RequestBody ProductDTO productDTO) {
        Product product = mapper.map(productDTO, Product.class);
        productService.saveProduct(product);
        return mapper.map(product, ProductDTO.class);
    }

    @RequestMapping(value = "/{ean}/image", method = RequestMethod.POST, produces = "image/png")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody byte[] createImage(@PathVariable("ean") String ean, @RequestBody byte[] bytes) {
        Image image = new Image();
        image.setBytes(bytes);
        if (!productService.addImageToProduct(ean, image)) {
            throw new NotFoundException();
        }
        return bytes;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<ProductDTO> list(@RequestParam(value = "page", required = true) Integer page,
                                               @RequestParam(value = "size", required = false) Integer size) {
        int sizeNo = size == null ? 10 : size;

        Page<Product> products = productService.findAllProducts(page, sizeNo);

        List<ProductDTO> productDTOs = new ArrayList<ProductDTO>(products.getSize());
        for (Product product : products) {
            productDTOs.add(mapper.map(product, ProductDTO.class));
        }
        return productDTOs;
    }

    @RequestMapping(value = "/{ean}/image", method = RequestMethod.GET, produces = "image/png")
    public @ResponseBody byte[] image(@PathVariable("ean") String ean) {
        byte[] imageBytes = productService.getProductImage(ean);
        if (imageBytes == null) {
            throw new NotFoundException();
        }
        return imageBytes;
    }
}

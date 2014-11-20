package pl.tr0k.pricescanner.config;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.squareup.otto.Bus;
import pl.tr0k.pricescanner.model.IProductsBasketManager;
import pl.tr0k.pricescanner.model.ProductsBasketManager;
import pl.tr0k.pricescanner.model.scanner.IKeyboardEventDecoder;
import pl.tr0k.pricescanner.model.scanner.KeyboardEventDefaultEanDecoder;
import pl.tr0k.pricescanner.service.IProductAPI;
import pl.tr0k.pricescanner.service.IProductService;
import pl.tr0k.pricescanner.service.ProductApiProvider;
import pl.tr0k.pricescanner.service.ProductService;


public class BindingModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IKeyboardEventDecoder.class).to(KeyboardEventDefaultEanDecoder.class);
        bind(IProductService.class).to(ProductService.class);
        bind(Bus.class).in(Singleton.class);
        bind(IProductsBasketManager.class).to(ProductsBasketManager.class);
        bind(IProductAPI.class).toProvider(ProductApiProvider.class);
    }

}

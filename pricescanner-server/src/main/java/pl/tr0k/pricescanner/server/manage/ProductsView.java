package pl.tr0k.pricescanner.server.manage;

import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.AbstractStringValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.FieldEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.*;
import com.vaadin.ui.*;
import com.vaadin.ui.Image;
import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.teemu.clara.Clara;
import org.vaadin.teemu.clara.binder.annotation.UiField;
import org.vaadin.teemu.clara.binder.annotation.UiHandler;
import pl.tr0k.pricescanner.common.dto.ProductDTO;
import pl.tr0k.pricescanner.server.manage.util.InMemoryUploadReceiver;
import pl.tr0k.pricescanner.server.manage.validation.MoneyStringToLongConverter;
import pl.tr0k.pricescanner.server.service.IProductService;
import pl.tr0k.pricescanner.server.domain.Product;

import java.io.*;

@Configurable(preConstruction = true)
public class ProductsView extends CssLayout implements View {

    @UiField("productList")
    private Table productList;

    @UiField("searchField")
    private TextField searchField;

    @UiField("removeProductButton")
    private Button removeProductButton;

    @UiField("editorLayout")
    private FormLayout editorLayout;

    @UiField("ean")
    private TextField eanField;

    @UiField("name")
    private TextField nameField;

    @UiField("price")
    private TextField priceField;

    @UiField("description")
    private TextArea descriptionField;

    @UiField
    private Image image;

    @UiField
    private Upload imageUpload;

    private final FieldGroup editorFields = new FieldGroup();

    private byte[] uploadedImage = null;

    private static final String EAN = "ean";
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String DESCRIPTION = "description";
    private static final String[] tableColumns = new String[]{EAN, NAME, PRICE};

    private String filterText;

    @Autowired
    private IProductService productService;

    private final Mapper mapper = new DozerBeanMapper();

    public ProductsView() {
        addComponent(Clara.create("ProductsView.xml", this));
        setSizeFull();
    }

    private void initEditor() {
        editorFields.bind(eanField, EAN);
        editorFields.bind(nameField, NAME);
        editorFields.bind(descriptionField, DESCRIPTION);
        editorFields.bind(priceField, PRICE);

        eanField.addValidator(new AbstractStringValidator("Podany EAN już istnieje") {
            @Override
            protected boolean isValidValue(String value) {
                Long productId = (Long) productList.getValue();
                Product productByEan = productService.findProductByEan(value);
                return productByEan == null || productByEan.getId().equals(productId);
            }
        });


        priceField.setConverter(new MoneyStringToLongConverter());

        eanField.addValidator(new StringLengthValidator("EAN nie może być pusty", 1, Integer.MAX_VALUE, false));
        eanField.setImmediate(true);

        nameField.addValidator(new StringLengthValidator("Nazwa nie może być pusta", 1, Integer.MAX_VALUE, false));
        nameField.setImmediate(true);

        imageUpload.setImmediate(true);

        final InMemoryUploadReceiver receiver = new InMemoryUploadReceiver();

        imageUpload.setReceiver(receiver);
        imageUpload.addStartedListener(receiver);
        imageUpload.addSucceededListener(new Upload.SucceededListener() {
            private int i = 0;

            public void uploadSucceeded(Upload.SucceededEvent event) {
                final byte[] bytes = receiver.getUpload();
                image.setSource(new StreamResource(new StreamResource.StreamSource() {
                    @Override
                    public InputStream getStream() {
                        return new ByteArrayInputStream(bytes);
                    }
                }, "image" + i++));
                image.setVisible(true);
                uploadedImage = bytes;
            }
        });

        /*
         * Data can be buffered in the user interface. When doing so, commit()
         * writes the changes to the data source. Here we choose to write the
         * changes automatically without calling commit().
         */
        editorFields.setBuffered(false);
        editorLayout.setVisible(false);
    }

    @UiHandler("searchField")
    public void doSearch(final FieldEvents.TextChangeEvent event) {
        filterText = event.getText();
        refreshProductList();
    }

    @UiHandler("removeProductButton")
    public void removeSelectedProduct(Button.ClickEvent event) {
        ConfirmDialog.show(getUI(), "Potwierdź usunięcie", "Czy na pewno usunąć wybrany produkt?", "Ok", "Anuluj",
                new ConfirmDialog.Listener() {
            @Override
            public void onClose(ConfirmDialog components) {
                if (components.isConfirmed()) {
                    Object productId = productList.getValue();
                    productService.deleteProduct((Long) productId);
                    refreshProductList();
                }
            }
        });
    }

    @UiHandler("saveProductButton")
    public void saveSelectedProduct(Button.ClickEvent event) {
        Object productId = productList.getValue();
        Product product = null;
        if (productId != null) {
            product = productService.findProduct((Long) productId);
        }
        if (product == null) {
            product = new Product();
        }

        mapper.map(((BeanItem) editorFields.getItemDataSource()).getBean(), product);
        productService.saveProduct(product);
        if (uploadedImage != null) {
            pl.tr0k.pricescanner.server.domain.Image image1 = new pl.tr0k.pricescanner.server.domain.Image();
            image1.setBytes(uploadedImage);
            productService.addImageToProduct(product.getEan(), image1);
        }
        refreshProductList();
    }

    @UiHandler("newProductButton")
    public void addNewProduct(Button.ClickEvent event) {
        productList.select(null);
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("");
        productDTO.setPrice(0L);
        productDTO.setEan("");
        productDTO.setDescription("");

        editorFields.setItemDataSource(new BeanItem<ProductDTO>(productDTO));

        editorLayout.setVisible(true);
        image.setVisible(false);
        uploadedImage = null;
    }

    private void refreshProductList() {
        BeanContainer<Long, ProductDTO> productContainer = new BeanContainer<Long, ProductDTO>(ProductDTO.class);

        Iterable<Product> products;
        if (StringUtils.isEmpty(filterText)) {
            products = productService.findAllProducts();
        } else {
            products = productService.findProductByAnyField(0, 20, filterText);
        }

        for (Product product : products) {
            productContainer.addItem(product.getId(), mapper.map(product, ProductDTO.class));
        }

        productList.setContainerDataSource(productContainer);
        productList.refreshRowCache();

        productList.setVisibleColumns(tableColumns);
        productList.setColumnHeaders("EAN", "Nazwa", "Cena");

        productList.setConverter(PRICE, new MoneyStringToLongConverter());
    }

    @UiHandler("productList")
    public void productSelected(Property.ValueChangeEvent event) {
        Object productId = productList.getValue();

        /*
         * When a product is selected from the list, we want to show that in our
         * editor on the right. This is nicely done by the FieldGroup that binds
         * all the fields to the corresponding Properties in our product at
         * once.
         */
        uploadedImage = null;

        if (productId != null) {
            ProductDTO bean = ((BeanItem<ProductDTO>) productList.getItem(productId)).getBean();
            editorFields.setItemDataSource(new BeanItem<ProductDTO>(bean));
            imageUpload.setEnabled(true);

            if (productService.findProduct((Long) productId).getImage() != null) {
                image.setSource(new ExternalResource("../products/" + bean.getEan() + "/image"));
                image.setVisible(true);
            } else {
                image.setVisible(false);
            }

        }

        editorLayout.setVisible(productId != null);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        refreshProductList();
        initEditor();
    }
}

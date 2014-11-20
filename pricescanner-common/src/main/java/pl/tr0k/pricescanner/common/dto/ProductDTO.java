package pl.tr0k.pricescanner.common.dto;

import java.io.Serializable;

public class ProductDTO implements Serializable {

    private String name;

    private String description;

    private Long price;

    private String ean;

    private Long imageId;

    public ProductDTO() {
    }

    public ProductDTO(String name, String description, Long price, String ean, Long imageId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.ean = ean;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }
}

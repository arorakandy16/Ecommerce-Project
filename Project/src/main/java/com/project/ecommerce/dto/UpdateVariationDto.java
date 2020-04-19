package com.project.ecommerce.dto;

import java.io.File;
import java.util.Map;

public class UpdateVariationDto {
        private Long productId;
        private Long quantityAvailable;
        private Integer price;
        private File productVariationImage = new File("/static/images/img.jpeg");
        private Map<String, String> metadata;
        Boolean isActive;

        public Boolean isActive () {
        return isActive;
    }

        public void setActive (Boolean active){
        isActive = active;
    }

        public Long getProductId () {
        return productId;
    }

        public void setProductId (Long productId){
        this.productId = productId;
    }

        public Long getQuantityAvailable () {
        return quantityAvailable;
    }

        public void setQuantityAvailable (Long quantityAvailable){
        this.quantityAvailable = quantityAvailable;
    }

        public Integer getPrice () {
        return price;
    }

        public void setPrice (Integer price){
        this.price = price;
    }

        public File getProductVariationImage () {
        return productVariationImage;
    }

        public void setProductVariationImage (File productVariationImage){
        this.productVariationImage = productVariationImage;
    }

        public Map<String, String> getMetadata () {
        return metadata;
    }

        public void setMetadata (Map < String, String > metadata){
        this.metadata = metadata;
    }

        @Override
        public String toString () {
        return "UpdateProductVariationDto{" +
                "productId=" + productId +
                ", quantityAvailable=" + quantityAvailable +
                ", price=" + price +
                ", productVariationImage=" + productVariationImage +
                ", metadata=" + metadata +
                ", isActive=" + isActive +
                '}';
    }
}

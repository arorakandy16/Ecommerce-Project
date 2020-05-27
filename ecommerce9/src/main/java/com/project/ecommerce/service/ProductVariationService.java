package com.project.ecommerce.service;

import com.project.ecommerce.dto.ProductVariationDto;
import com.project.ecommerce.dto.UpdateVariationDto;
import com.project.ecommerce.entity.*;
import com.project.ecommerce.exception.Message;
import com.project.ecommerce.exception.ProductNotFoundException;
import com.project.ecommerce.exception.UserNotFoundException;
import com.project.ecommerce.exception.ValidationException;
import com.project.ecommerce.repository.*;
import com.project.ecommerce.security.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class ProductVariationService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MetadataFieldRepository metadataFieldRepository;

    @Autowired
    MetadataFieldValuesRepository metadataFieldValuesRepository;

    @Autowired
    ProductVariationRepository productVariationRepository;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

    Logger logger = LoggerFactory.getLogger(ProductVariationService.class);



    //to Login Seller

    public User getLoggedInSeller() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser userDetail = (AppUser) authentication.getPrincipal();
        String username = userDetail.getUsername();
        User user = userRepository.findByUsername(username);
        return user;
    }



    //Add Product Variation

    public String addProductVariation(ProductVariationDto productVariationDto, Locale locale) {
        Optional<Product> product = productRepository.findById
                (productVariationDto.getProductId());

        if (!product.isPresent() && !product.get().isIs_active())
            throw new ProductNotFoundException("PRODUCT NOT FOUND");
        Product product1 = product.get();
        if (product1.getSeller().getUserid() != getLoggedInSeller().getUserid())
            throw new UserNotFoundException("Product not related to this seller");

        ProductVariant productVariation = new ProductVariant();
        productVariation.setProduct(product1);

        Optional<ProductCategory> category
                = productCategoryRepository.findById(product1.getProductcategory().getPcId());
        Long pcId=category.get().getPcId();

        Map<String, String> metaData = productVariationDto.getMetaData();

        for (Map.Entry<String, String> fieldValues : metaData.entrySet()) {
            String field = fieldValues.getKey();
            String value = fieldValues.getValue();
            CategoryMetadataField categoryMetadataField = metadataFieldRepository.findByName(field);
            CategoryMetadataFieldValues values
                    = metadataFieldValuesRepository.findMetadataFieldValue
                    (categoryMetadataField.getId(),pcId);
            List<String> list = Arrays.asList(values.getMetadataValues().split(","));
            System.out.println(list);
            if (list.isEmpty()) {
                throw new ProductNotFoundException("Value not found");
            }
        }
        productVariation.setMetaData(productVariationDto.getMetaData());

        if (productVariationDto.getPrice() < 0) {
            throw new ValidationException("Price must be 0 or more");
        }
        productVariation.setPrice(productVariationDto.getPrice());
        if (productVariationDto.getQuantityAvailable()< 0) {
            throw new ValidationException("Quantity must be ) or more");
        }
        productVariation.setQuantityAvailable
                (productVariationDto.getQuantityAvailable());
        productVariation.setProductVariationImage
                (productVariationDto.getProductVariationImage());

        productVariationRepository.save(productVariation);
        throw new Message(messageSource.getMessage
                ("variation.add.message", null, locale));

    }



    //Find Product Variation

    @Cacheable("${variation.cache}")

    public ProductVariant findProductVariation(Long variationId) {
        Optional<ProductVariant> productVariation
                = productVariationRepository.findById(variationId);

        logger.info("Caching is working");

        if(!productVariation.isPresent())
            throw new ProductNotFoundException("Product Variation does not exist");
        Product product = productVariation.get().getProduct();

        if(product.isIs_deleted())
            throw new ProductNotFoundException("Product does not exist");

        Seller seller = product.getSeller();

        if (seller.getEmail().equals(getLoggedInSeller().getEmail())) {
            return productVariation.get();
        }
        else{
            throw new ProductNotFoundException("User not authorized");
        }
    }



    //Find All Variation

    @Cacheable("${variation.cache}")

    public List<ProductVariant> findAllVariation(Long productId,Integer offset, Integer size) {

        Optional<Product> product=productRepository.findById(productId);

        logger.info("Caching is working");

        if(product.get().isIs_deleted())
            throw new ProductNotFoundException("Product does not exist");

        if(product.isPresent()) {

            if(getLoggedInSeller().getEmail().equals(product.get().getSeller().getEmail())) {
                return productVariationRepository.findByProductId
                        (productId, PageRequest.of(offset,size, Sort.Direction.ASC, "product_id"));
            }

            else {
                throw new ProductNotFoundException("User Not authorized");
            }

        }

        else {
            throw new ProductNotFoundException("Product not found");
        }
    }



    //Update Product Variation

    @CachePut("${variation.cache}")

    public String updateProductVariation(Long id, UpdateVariationDto updateVariationDto,
                                         Locale locale) {
        Optional<ProductVariant> productVariationOptional
                = productVariationRepository.findById(id);

        logger.info("Caching is working");

        if (!productVariationOptional.isPresent())
            throw new EntityNotFoundException("Product variation Id is not valid");
        ProductVariant productVariation = productVariationOptional.get();

        if (productVariation.getProduct().getSeller().getUserid()
                != getLoggedInSeller().getUserid()) {
            throw new EntityNotFoundException("Product not related to this seller");
        }

        if(productVariation.getProduct().isIs_deleted())
            throw new ProductNotFoundException("Product does not exist");

        if(productVariation.getProduct().isIs_deleted())
            throw new ProductNotFoundException("Product does not exist");

        if (updateVariationDto.getQuantityAvailable() != null) {
            productVariation.setQuantityAvailable(updateVariationDto.getQuantityAvailable());
        }

        if (updateVariationDto.getPrice() != null) {
            productVariation.setPrice(updateVariationDto.getPrice());
        }

        if (updateVariationDto.getProductVariationImage()!= null) {
            productVariation.setProductVariationImage
                    (updateVariationDto.getProductVariationImage());
        }

        if (updateVariationDto.isActive() != null) {
            productVariation.setActive(updateVariationDto.isActive());
        }

        if (updateVariationDto.getMetadata() != null) {
            Map<String, String> metadata = updateVariationDto.getMetadata();

            for (Map.Entry<String, String> fieldValues : metadata.entrySet()) {
                String field = fieldValues.getKey();
                String value = fieldValues.getValue();

                CategoryMetadataField categoryMetadataField
                        = metadataFieldRepository.findByName(field);

                CategoryMetadataFieldValues categoryMetadataFieldValues
                        = metadataFieldValuesRepository.findMetadataFieldValue
                        (categoryMetadataField.getId(), productVariation.getProduct()
                                .getProductcategory().getPcId());

                List<String> list = Arrays.asList
                        (categoryMetadataFieldValues.getMetadataValues().split(","));

                if (!list.contains(value)) {
                    throw new EntityNotFoundException("Value not found");
                }
            }
            productVariation.setMetaData(metadata);
        }
        productVariationRepository.save(productVariation);
        throw new Message(messageSource.getMessage("variation.update.message", null, locale));

    }
}

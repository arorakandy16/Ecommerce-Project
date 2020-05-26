package com.project.ecommerce.service;

import com.project.ecommerce.dto.CategoryDto;
import com.project.ecommerce.dto.FilterCategoryDto;
import com.project.ecommerce.entity.CategoryMetadataFieldValues;
import com.project.ecommerce.entity.Product;
import com.project.ecommerce.entity.ProductCategory;
import com.project.ecommerce.entity.ProductVariant;
import com.project.ecommerce.exception.*;
import com.project.ecommerce.repository.MetadataFieldValuesRepository;
import com.project.ecommerce.repository.ProductCategoryRepository;
import com.project.ecommerce.repository.ProductRepository;
import com.project.ecommerce.repository.ProductVariationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductCategoryService {

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    MetadataFieldValuesRepository metadataFieldValuesRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductVariationRepository productVariationRepository;

    @Autowired
    private MessageSource messageSource;

    Logger logger = LoggerFactory.getLogger(ProductCategoryService.class);



    //Get All Product Category

    @Cacheable(cacheNames = "getAll")

    public List<ProductCategory> getAll(Integer offset, Integer size) {

        logger.info("Caching is working");

        return productCategoryRepository.getAll
                (PageRequest.of
                        ( offset,size, Sort.Direction.ASC, "pc_id"));
    }



    //Add Category

//    @Cacheable(cacheNames = "addCategory")

    public String addCategory(ProductCategory productCategory, Locale locale) {

//        logger.info("Caching is working");

        try {
            productCategoryRepository.save(productCategory);
        }

        catch (Exception ex) {
            throw new CategoryAlreadyRegistered("Category Name Already Registered");
        }

        throw new Message(messageSource.getMessage
                ("admin.add.category.message", null, locale));

    }


    // Get a Category

    @Cacheable(cacheNames = "getACategory")

    public Optional<ProductCategory> getACategory(Long id) {

        Optional<ProductCategory> productCategory = productCategoryRepository.findById(id);

        logger.info("Caching is working");

        try {

            ProductCategory product1 = productCategory.get();
        }
        catch (Exception ex) {
            throw new ProductNotFoundException("Category Id is Invalid");
        }
        return productCategory;
    }


    //Update Category

    @CachePut(cacheNames = "updateCategory")

    public String updateCategory(Long id, ProductCategory name, Locale locale) {

        Optional<ProductCategory> productCategory = productCategoryRepository.findById(id);

        Optional<ProductCategory> productCategory2 =
                productCategoryRepository.findByName(name.getName());

        if (productCategory2.isPresent())
            throw new ValidationException("Category name already exists");

        logger.info("Caching is working");

        try {
            ProductCategory productCategory1 = productCategory.get();

            productCategory1.setName(name.getName());

            productCategoryRepository.save(productCategory1);
        }
        catch (Exception ex) {
            throw new ProductNotFoundException("Category Id is Invalid");
        }

        throw new Message(messageSource.getMessage
                ("admin.update.category.message", null, locale));
    }


    //Get All Categories By Customer

    @Cacheable(cacheNames = "viewAllCategoriesByCustomer")

    public List<CategoryDto> viewAllCategoriesByCustomer(Long id) {

        logger.info("Caching is working");

        if (id == null) {

            List<CategoryDto> categoryDtos = new ArrayList<>();

            List<ProductCategory> productCategories = productCategoryRepository.getAllRoot();

            for (ProductCategory productCategory : productCategories) {
                CategoryDto categoryDto = new CategoryDto();

                categoryDto.setPcId(productCategory.getPcId());
                categoryDto.setName(productCategory.getName());
                categoryDtos.add(categoryDto);
            }
            return categoryDtos;
        }

        else {

            List<CategoryDto> categoryDtos = new ArrayList<>();

            try {

                List<ProductCategory> productCategories = productCategoryRepository.getAllChild(id);

                if (productCategories.get(0).getPcId() != null) {

                    for (ProductCategory productCategory : productCategories) {
                        CategoryDto categoryDto = new CategoryDto();
                        categoryDto.setPcId(productCategory.getPcId());
                        categoryDto.setName(productCategory.getName());
                        categoryDtos.add(categoryDto);
                    }
                }
            }

            catch (Exception ex) {
                throw new InvalidCategoryOrFieldIdException
                        ("Either Category Id is not valid "
                                + "or" +
                                " This Id is not associated with any child category");
            }
            return categoryDtos;
        }
    }


    //Filter Category By Customer

    @Cacheable(cacheNames = "category6")

    public FilterCategoryDto filterCategoryByCustomer(Long categoryId) {

        List<Product> productList = productRepository.findAllByCategoryId(categoryId);

        logger.info("Caching is working");

        if (productList.isEmpty())
            throw new ProductNotFoundException("There is no product related to this category");

        FilterCategoryDto filterCategoryDto = new FilterCategoryDto();

        List<CategoryMetadataFieldValues> categoryFieldValuesList = new ArrayList<>();

        Iterator<CategoryMetadataFieldValues> categoryFieldValuesIterator = metadataFieldValuesRepository.findAll().iterator();

        while (categoryFieldValuesIterator.hasNext()) {
            CategoryMetadataFieldValues currentCategoryFieldValues = categoryFieldValuesIterator.next();

            if (currentCategoryFieldValues.getId().getCategoryId() == categoryId) {
                categoryFieldValuesList.add(currentCategoryFieldValues);
            }
        }

        Integer max = Integer.MIN_VALUE;

        Integer min = Integer.MAX_VALUE;

        Set<String> brandsList = new HashSet<>();

        Iterator<Product> productIterator = productRepository
                .findAllByCategoryId(categoryId).iterator();

        while (productIterator.hasNext()) {

            Product currentProduct = productIterator.next();

            if (currentProduct.getProductcategory().getPcId() == categoryId) {

                brandsList.add(currentProduct.getBrand());

                Iterator<ProductVariant> productVariantIterator =
                        productVariationRepository
                                .findByProductIdWithoutPaging
                                        (currentProduct.getProductId()).iterator();

                while (productVariantIterator.hasNext()) {

                    ProductVariant currentVariant = productVariantIterator.next();

                    if (currentVariant.getPrice() <= min)
                        min = currentVariant.getPrice().intValue();

                    if (currentVariant.getPrice() >= max)
                        max = currentVariant.getPrice().intValue();

                }
            }
        }

        filterCategoryDto.setCategoryFieldValues(categoryFieldValuesList);

        if (max > 0)
            filterCategoryDto.setMaxPrice(max);

        if (min < Integer.MAX_VALUE)
            filterCategoryDto.setMinPrice(min);

        filterCategoryDto.setBrandsList(brandsList);
        return filterCategoryDto;
    }
}

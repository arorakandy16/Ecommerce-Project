package com.project.ecommerce.service;

import com.project.ecommerce.dto.CategoryDto;
import com.project.ecommerce.dto.FilterCategoryDto;
import com.project.ecommerce.entity.CategoryMetadataFieldValues;
import com.project.ecommerce.entity.Product;
import com.project.ecommerce.entity.ProductCategory;
import com.project.ecommerce.entity.ProductVariant;
import com.project.ecommerce.exception.CategoryAlreadyRegistered;
import com.project.ecommerce.exception.InvalidCategoryOrFieldIdException;
import com.project.ecommerce.exception.ProductNotFoundException;
import com.project.ecommerce.repository.MetadataFieldValuesRepository;
import com.project.ecommerce.repository.ProductCategoryRepository;
import com.project.ecommerce.repository.ProductRepository;
import com.project.ecommerce.repository.ProductVariationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {

    @Autowired
    ProductCategoryRepository productCategoryRepository;
    @Autowired
    MetadataFieldValuesRepository metadataFieldValuesRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductVariationRepository productVariationRepository;

    public List<ProductCategory> getAll() {
        return productCategoryRepository.getAll(PageRequest.of(0, 10, Sort.Direction.ASC, "pc_id"));
    }

    public String addCategory(ProductCategory productCategory) {
        try{
            productCategoryRepository.save(productCategory);
        }
        catch (Exception ex){
            throw new CategoryAlreadyRegistered( "Category Name Already Registered");
        }
        return "Category Added Successfully";

    }

    public Optional<ProductCategory> getACategory(Long id) {
        Optional<ProductCategory> productCategory = productCategoryRepository.findById(id);
        try{
            ProductCategory product1=productCategory.get();
        }
        catch (Exception ex){
            throw new ProductNotFoundException("Category Id is Invalid");
        }
        return productCategory;

    }

    public String updateCategory(Long id, ProductCategory name) {
        Optional<ProductCategory> productCategory = productCategoryRepository.findById(id);
        try{
            ProductCategory productCategory1=productCategory.get();
            productCategory1.setName(name.getName());
            productCategoryRepository.save(productCategory1);
        }
        catch (Exception ex){
            throw new ProductNotFoundException("Category Id is Invalid");
        }
        return "Category updated";
    }

    public List<CategoryDto> viewAllCategoriesByCustomer(Long id) {
        if(id==null){
            List<CategoryDto> categoryDtos=new ArrayList<>();
            List<ProductCategory> productCategories=productCategoryRepository.getAllRoot();
            for(ProductCategory productCategory : productCategories){
                CategoryDto categoryDto=new CategoryDto();
                categoryDto.setPcId(productCategory.getPcId());
                categoryDto.setName(productCategory.getName());
                categoryDtos.add(categoryDto);
            }
            return categoryDtos;
        }
        else {
            List<CategoryDto> categoryDtos=new ArrayList<>();
            try {
                List<ProductCategory> productCategories=productCategoryRepository.getAllChild(id);
                if(productCategories.get(0).getPcId() !=null){
                    for(ProductCategory productCategory : productCategories){
                    CategoryDto categoryDto=new CategoryDto();
                    categoryDto.setPcId(productCategory.getPcId());
                    categoryDto.setName(productCategory.getName());
                    categoryDtos.add(categoryDto);
                    }
                }
            }
            catch (Exception ex){
                throw new InvalidCategoryOrFieldIdException("Either Category Id is not valid or" +
                        " This Id is not associated with any child category");
            }
            return categoryDtos;
        }
    }

    public FilterCategoryDto filterCategoryByCustomer(Long categoryId) {

        FilterCategoryDto filterCategoryDto=new FilterCategoryDto();
        List<CategoryMetadataFieldValues> categoryFieldValuesList=new ArrayList<>();
        Iterator<CategoryMetadataFieldValues> categoryFieldValuesIterator= metadataFieldValuesRepository.findAll().iterator();
        while (categoryFieldValuesIterator.hasNext()) {
            CategoryMetadataFieldValues currentCategoryFieldValues=categoryFieldValuesIterator.next();
            if (currentCategoryFieldValues.getId().getCategoryId()==categoryId) {
                categoryFieldValuesList.add(currentCategoryFieldValues);
            }
        }

        Integer max=Integer.MIN_VALUE;
        Integer min=Integer.MAX_VALUE;
        Set<String> brandsList=new HashSet<>();
        Iterator<Product> productIterator= productRepository.findAllByCategoryIdForCustomerAdmin(categoryId,PageRequest.of(0, 10, Sort.Direction.ASC, "product_id")).iterator();
        while (productIterator.hasNext()) {
            Product currentProduct = productIterator.next();
            if (currentProduct.getProductcategory().getPcId() == categoryId) {
                brandsList.add(currentProduct.getBrand());
                Iterator<ProductVariant> productVariantIterator = productVariationRepository.findByProductId(currentProduct.getProductId()).iterator();
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
        if (max>0)
            filterCategoryDto.setMaxPrice(max);
        if(min<Integer.MAX_VALUE)
        filterCategoryDto.setMinPrice(min);
        filterCategoryDto.setBrandsList(brandsList);
        return filterCategoryDto;
    }
}


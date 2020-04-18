package com.project.ecommerce.Services;

import com.project.ecommerce.Daos.CategoryDto;
import com.project.ecommerce.Entities.ProductCategory;
import com.project.ecommerce.Exceptions.CategoryAlreadyRegistered;
import com.project.ecommerce.Exceptions.InvalidCategoryOrFieldIdException;
import com.project.ecommerce.Exceptions.ProductNotFoundException;
import com.project.ecommerce.Repositries.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CategoryService {

    @Autowired
    ProductCategoryRepository productCategoryRepository;

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

    public List<CategoryDto> viewAllCategoriesBySeller(Long id) {
        if(id==null){
            List<CategoryDto> categoryDtos=new ArrayList<>();
            List<ProductCategory> productCategories=productCategoryRepository.getAllRoot();
            for(ProductCategory productCategory : productCategories){
                CategoryDto categoryDto=new CategoryDto();
                categoryDto.setPc_id(productCategory.getPc_id());
                categoryDto.setName(productCategory.getName());
                categoryDtos.add(categoryDto);
            }
            return categoryDtos;
        }
        else {
            List<CategoryDto> categoryDtos=new ArrayList<>();
            try {
                List<ProductCategory> productCategories=productCategoryRepository.getAllChild(id);
                System.out.println(productCategories);
                if(productCategories.get(0).getPc_id() !=null){
                    for(ProductCategory productCategory : productCategories){
                    CategoryDto categoryDto=new CategoryDto();
                    categoryDto.setPc_id(productCategory.getPc_id());
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
}

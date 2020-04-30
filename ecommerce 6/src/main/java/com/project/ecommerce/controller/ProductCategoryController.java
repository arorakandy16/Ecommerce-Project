package com.project.ecommerce.controller;

import com.project.ecommerce.dto.CategoryDto;
import com.project.ecommerce.dto.FilterCategoryDto;
import com.project.ecommerce.entity.ProductCategory;
import com.project.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductCategoryController {

    @Autowired
    CategoryService categoryService;



    @PostMapping("/admin/category/add")
    public String addCategory(@RequestBody ProductCategory productCategory){
        return categoryService.addCategory(productCategory);
    }



    @GetMapping("/category/view/all")
    public List<ProductCategory> viewAll(){
       return categoryService.getAll();
    }



    @GetMapping("/admin/category/view/{id}")
    public Optional<ProductCategory> viewCategory(@PathVariable Long id) {
        return categoryService.getACategory(id);
    }



    @PutMapping("/admin/category/update/{id}")
    public String updateCategory(@PathVariable Long id, @RequestBody ProductCategory name){
        return categoryService.updateCategory(id,name);
    }



    @GetMapping("/customer/category/view/all")
    public List<CategoryDto> viewAllCategoriesByCustomer(){
        return categoryService.viewAllCategoriesByCustomer(null);
    }



    @GetMapping("/customer/category/view/{id}")
    public List<CategoryDto> viewAllCategoriesByIdByCustomer(@PathVariable Long id ){
        return categoryService.viewAllCategoriesByCustomer(id);
    }



    @GetMapping("/customer/category/filter/{id}")
    public FilterCategoryDto filterCategoriesByIdByCustomer(@PathVariable Long id ){
        return categoryService.filterCategoryByCustomer(id);
    }
}

package com.project.ecommerce.Controllers;

import com.project.ecommerce.Daos.CategoryDto;
import com.project.ecommerce.Entities.ProductCategory;
import com.project.ecommerce.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductCategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/category/add")
    public String addCategory(@RequestBody ProductCategory productCategory){
        return categoryService.addCategory(productCategory);
    }
    @GetMapping("/category/*")
    public List<ProductCategory> viewAll(){
       return categoryService.getAll();
    }

    @GetMapping("/category/{id}")
    public Optional<ProductCategory> viewCategory(@PathVariable Long id){
        return categoryService.getACategory(id);
    }

    @PutMapping("/category/update/{id}")
    public String updateCategory(@PathVariable Long id, @RequestBody ProductCategory name){
        return categoryService.updateCategory(id,name);
    }


    @GetMapping("/seller/category")
    public List<CategoryDto> viewAllCategoriesBySeller(){
        return categoryService.viewAllCategoriesBySeller(null);
    }

    @GetMapping("/seller/category/{id}")
    public List<CategoryDto> viewAllCategoriesByIdBySeller(@PathVariable Long id ){
        return categoryService.viewAllCategoriesBySeller(id);
    }

}

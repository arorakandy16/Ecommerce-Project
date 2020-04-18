package com.project.ecommerce.Controllers;
import com.project.ecommerce.Daos.ProductDto;
import com.project.ecommerce.Entities.Product;
import com.project.ecommerce.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {
    
    @Autowired
    ProductService productService;

    //Work in progress.....

    @PostMapping("/product/add")
    public String addProduct(@RequestBody Product product){
        productService.addProduct(product);
        return "product added successfully";
    }

    @GetMapping("/seller/product/view/{productId}")
    public Optional<Product> viewProductAsSeller(@PathVariable Long productId){
        Optional<Product> product = productService.viewProductAsSeller(productId);
        return product;
    }

    @GetMapping("/seller/product/view/all")
    public List<Product> viewAllProductAsSeller(){
        return productService.viewAllProductAsSeller();
    }

    @DeleteMapping("/seller/product/delete/{productId}")
    public String deleteProductAsSeller(@PathVariable Long productId){
        productService.deleteProduct(productId);
        return "product deleted successfully";
    }

    @PostMapping("/seller/product/update/{productId}")
    public String updateProductAsSeller(@PathVariable Long productId,@RequestBody ProductDto productDto){
        productService.updateProduct(productId,productDto);
        return "product updated successfully";
    }

    @GetMapping("/product/view/{productId}")
    public Optional<Product> viewProduct(@PathVariable Long productId){
        return productService.viewAProductAsCustomer(productId);
    }

    @GetMapping("/product/view/all/{categoryId}")
    public List<Product> viewAllProduct(@PathVariable Long categoryId){
        return productService.viewAllProductsAsCustomer(categoryId);
    }

    @GetMapping("/admin/product/view/{productId}")
    public Optional<Product> viewProductAsAdmin(@PathVariable Long productId){
        return productService.viewAProductAsAdmin(productId);
    }

    @GetMapping("/admin/product/view/all/{categoryId}")
    public List<Product> viewAllProductAsAdmin(@PathVariable Long categoryId){
        return productService.viewAllProductsAsAdmin(categoryId);
    }

    @GetMapping("/admin/product/de-activate/{productId}")
    public String deactivateProduct(@PathVariable Long productId){
        return productService.deactivateProduct(productId);
    }

    @GetMapping("/admin/product/activate/{productId}")
    public String activateProduct(@PathVariable Long productId){
        return productService.activateProduct(productId);
    }
}

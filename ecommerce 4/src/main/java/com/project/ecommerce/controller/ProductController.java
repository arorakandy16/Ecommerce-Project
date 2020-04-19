package com.project.ecommerce.controller;
import com.project.ecommerce.dto.ProductDto;
import com.project.ecommerce.dto.ProductVariationDto;
import com.project.ecommerce.dto.UpdateVariationDto;
import com.project.ecommerce.entity.Product;
import com.project.ecommerce.entity.ProductVariant;
import com.project.ecommerce.service.ProductService;
import com.project.ecommerce.service.ProductVariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {
    
    @Autowired
    ProductService productService;
    @Autowired
    ProductVariationService productVariationService;

    @PostMapping("/seller/product/add")
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
        return productService.deleteProduct(productId);
    }

    @PostMapping("/seller/product/update/{productId}")
    public String updateProductAsSeller(@PathVariable Long productId,@RequestBody ProductDto productDto){
        productService.updateProduct(productId,productDto);
        return "product updated successfully";
    }

    @GetMapping("/customer/product/view/{productId}")
    public Optional<Product> viewProduct(@PathVariable Long productId){
        return productService.viewAProductAsCustomer(productId);
    }

    @GetMapping("/customer/product/view/all/{categoryId}")
    public List<Product> viewAllProductAsCustomer(@PathVariable Long categoryId){
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

    @PostMapping("/seller/product/variation/add")
    public String setProductVariation(@RequestBody ProductVariationDto productVariationDto){
        productVariationService.addProductVariation(productVariationDto);
        return "saved";
    }

    @GetMapping("/seller/product/variation/view/{variationId}")
    public ProductVariant getProductVariation(@PathVariable Long variationId){
        return productVariationService.findProductVariation(variationId);
    }

    @GetMapping("/seller/product/variation/views/{productId}")
    public List<ProductVariant> getAllProductVariation(@PathVariable Long productId){
        return productVariationService.findAllVariation(productId);
    }

    @PutMapping("/seller/product/variation/update/{variationId}")
    public String updateProductVariation(@PathVariable Long variationId, @RequestBody UpdateVariationDto updateVariationDto){
        return productVariationService.updateProductVariation(variationId,updateVariationDto);
    }

    @GetMapping("/customer/product/view/similar/{productId}")
    public List<Product> similarProductVariation(@PathVariable Long productId){
        return productService.similarProductVariation(productId);
    }
}

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
import java.util.Locale;
import java.util.Optional;

@RestController
public class ProductController {
    
    @Autowired
    ProductService productService;

    @Autowired
    ProductVariationService productVariationService;



    @PostMapping("/seller/product/add")
    public String addProduct(@RequestBody Product product,
                             @RequestHeader(name = "Accept-Language",
                                     required = false) Locale locale){
        productService.addProduct(product,locale);
        return "product added successfully";
    }



    @GetMapping("/seller/product/view/{productId}")
    public Optional<Product> viewProductAsSeller(@PathVariable Long productId){
        Optional<Product> product = productService.viewProductAsSeller(productId);
        return product;
    }



    @GetMapping("/seller/product/view/all/{page}/{size}")
    public List<Product> viewAllProductAsSeller(@PathVariable Integer page,
                                                @PathVariable Integer size){
        return productService.viewAllProductAsSeller(page, size);
    }



    @DeleteMapping("/seller/product/delete/{productId}")
    public String deleteProductAsSeller(@PathVariable Long productId){
        return productService.deleteProduct(productId);
    }



    @PutMapping("/seller/product/update/{productId}")
    public String updateProductAsSeller(@PathVariable Long productId,
                                        @RequestBody ProductDto productDto,
                                        @RequestHeader(name = "Accept-Language",
                                                required = false) Locale locale){
        productService.updateProduct(productId,productDto,locale);
        return "product updated successfully";
    }



    @GetMapping("/customer/product/view/{productId}")
    public Optional<Product> viewProduct(@PathVariable Long productId){
        return productService.viewAProductAsCustomer(productId);
    }



    @GetMapping("/customer/product/view/all/{categoryId}/{page}/{size}")
    public List<Product> viewAllProductAsCustomer(@PathVariable Long categoryId,
                                                  @PathVariable Integer page,
                                                  @PathVariable Integer size){
        return productService.viewAllProductsAsCustomer(categoryId, page, size);
    }



    @GetMapping("/admin/product/view/{productId}")
    public Optional<Product> viewProductAsAdmin(@PathVariable Long productId){
        return productService.viewAProductAsAdmin(productId);
    }



    @GetMapping("/admin/product/view/all/{categoryId}/{page}/{size}")
    public List<Product> viewAllProductAsAdmin(@PathVariable Long categoryId,
                                               @PathVariable Integer page,
                                               @PathVariable Integer size){
        return productService.viewAllProductsAsAdmin(categoryId,page,size);
    }



    @GetMapping("/admin/product/de-activate/{productId}")
    public String deactivateProduct(@PathVariable Long productId,
                                    @RequestHeader(name = "Accept-Language",
                                            required = false) Locale locale){
        return productService.deactivateProduct(productId,locale);
    }



    @GetMapping("/admin/product/activate/{productId}")
    public String activateProduct(@PathVariable Long productId,
                                  @RequestHeader(name = "Accept-Language",
            required = false) Locale locale){
        return productService.activateProduct(productId,locale);
    }



    @PostMapping("/seller/product/variation/add")
    public String setProductVariation(@RequestBody ProductVariationDto productVariationDto,
                                      @RequestHeader(name = "Accept-Language",
                                              required = false) Locale locale){
        productVariationService.addProductVariation(productVariationDto,locale);
        return "saved";
    }



    @GetMapping("/seller/product/variation/view/{variationId}")
    public ProductVariant getProductVariation(@PathVariable Long variationId){
        return productVariationService.findProductVariation(variationId);
    }



    @GetMapping("/seller/product/variation/views/{productId}/{page}/{size}")
    public List<ProductVariant> getAllProductVariation
            (@PathVariable Long productId,
             @PathVariable Integer page,
             @PathVariable Integer size){
        return productVariationService.findAllVariation(productId, page, size);
    }



    @PutMapping("/seller/product/variation/update/{variationId}")
    public String updateProductVariation
            (@PathVariable Long variationId,
             @RequestBody UpdateVariationDto updateVariationDto,
             @RequestHeader(name = "Accept-Language",
                     required = false) Locale locale){
        return productVariationService.updateProductVariation
                (variationId,updateVariationDto,locale);
    }



    @GetMapping("/customer/product/view/similar/{productId}/{page}/{size}")
    public List<Product> similarProductVariation
            (@PathVariable Long productId,
             @PathVariable Integer page,
             @PathVariable Integer size){
        return productService.similarProductVariation(productId, page, size);
    }

}

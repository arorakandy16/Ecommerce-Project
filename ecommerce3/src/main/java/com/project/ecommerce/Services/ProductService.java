package com.project.ecommerce.Services;

import com.project.ecommerce.Daos.ProductDto;
import com.project.ecommerce.Entities.*;
import com.project.ecommerce.Exceptions.ProductNotFoundException;
import com.project.ecommerce.Repositries.ProductRepository;
import com.project.ecommerce.Repositries.SellerRepository;
import com.project.ecommerce.Security.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Component
public class ProductService {
    
    @Autowired
    ProductRepository productRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    EmailService emailService;

    //to Get the Current Logged-In Username in Spring Security
    public Seller getLoggedInSeller() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser userDetail = (AppUser) authentication.getPrincipal();
        String username = userDetail.getUsername();
        Seller seller = sellerRepository.findByUsername(username);
        return seller;
    }

    public String addProduct(Product product) {
        Seller seller = getLoggedInSeller();
        product.setSeller(seller);
        try {
            emailService.sendEmail("REGARDING PRODUCT ACTIVATION", "Hii Admin, \n There is a pending task for you. Seller "+seller.getFirstname()+
                    " added a product '" + product.getProductName() +"', Could you please verify it and activate it ASAP.  ",seller.getEmail());
            productRepository.save(product);
        } catch (Exception ex) {
            return "Mail sending Failed... Product is not added yet... please try again...";
        }
        return "Product added successfully";
    }

    public Optional<Product> viewProductAsSeller(Long productId) {
        Seller seller=getLoggedInSeller();
        Optional<Product> product = productRepository.findByIdAndSellerId(seller.getUserid(),productId);
        try{
            Product product1= product.get();
        }
        catch (Exception ex){
            throw new ProductNotFoundException("Product Not Found");
        }
        return product;
    }

    public List<Product> viewAllProductAsSeller() {
        Seller seller=getLoggedInSeller();
        return productRepository.findAllBySeller(seller.getUserid());
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Seller seller = getLoggedInSeller();
        Product product = productRepository.findByUserIdAndSellerId(productId, seller.getUserid());
        if (product.getProductId() != null) {
            productRepository.deleteByIdAndSellerId(productId,seller.getUserid());
        }
        else
            throw new ProductNotFoundException("Cann't delete unavailable product");
    }

    public void updateProduct(Long productId, ProductDto productDto) {
        Seller seller = getLoggedInSeller();
        Product product = productRepository.findByUserIdAndSellerId(productId, seller.getUserid());
        if (product.getBrand() != null) {
            if(productDto.getBrand()!=null)
                product.setBrand(productDto.getBrand());
            if(productDto.getDescription()!=null)
                product.setDescription(productDto.getDescription());
            if(productDto.getProductName()!=null)
                product.setProductName(productDto.getProductName());
            if(productDto.isIs_cancellable())
                product.setIs_cancellable(productDto.isIs_cancellable());
            if(productDto.isIs_returnable())
                product.setIs_returnable(productDto.isIs_returnable());
            productRepository.save(product);
        }
    }

    public Optional<Product> viewAProductAsCustomer(Long productId) {
       Optional<Product> product = productRepository.findById(productId);
       try{
           Product product1=product.get();
       }
       catch (Exception ex){
           throw new ProductNotFoundException("Product Not Found");
       }
       return product;
    }

    public List<Product> viewAllProductsAsCustomer(Long categoryId) {
        List<Product> products=productRepository.findAllByCategoryId(categoryId);
        try {
            products.get(0).getProductId();
        }
        catch (Exception ex){
            throw new ProductNotFoundException("Invalid Category Id");
        }
        return products;
    }

    public Optional<Product> viewAProductAsAdmin(Long productId) {
            Optional<Product> product = productRepository.findById(productId);
            try{
                Product product1=product.get();
            }
            catch (Exception ex){
                throw new ProductNotFoundException("Product Not Found");
            }
            return product;
        }

    public List<Product> viewAllProductsAsAdmin(Long categoryId) {
        List<Product> products=productRepository.findAllByCategoryId(categoryId);
        try {
            products.get(0).getProductId();
        }
        catch (Exception ex){
            throw new ProductNotFoundException("Invalid Category Id");
        }
        return products;
    }

    public String deactivateProduct(Long productId) {
        Optional<Product> product=productRepository.findById(productId);
        if(product.get().isIs_active()){
            try {
                emailService.sendEmail("REGARDING PRODUCT ACTIVATION", "Hii, \n We have found a suspicious product that you added," +
                        " it violates our terms and conditions, that's why we have to de-activate it.\n\n"+ "Product Details are : \n\t Category : " +
                        product.get().getProductcategory().getName() +"\n\t Name : "+ product.get().getProductName()+" \n\t Brand : " + product.get().getBrand() +"\n\t Details : " +
                        product.get().getDescription() , product.get().getSeller().getEmail());
                productRepository.deActivateProduct(product.get().getProductId(),false);
            }
            catch (Exception ex) {
                return "Mail sending Failed... Product is activated yet... please try again...";
            }
            return "Product de-activated";
        }
        else
            return "Product is already de-activated";
    }

    public String activateProduct(Long productId) {
        Optional<Product> product=productRepository.findById(productId);
        if(!product.get().isIs_active()){
        try {
            emailService.sendEmail("REGARDING PRODUCT ACTIVATION", "Hii, \n Your product "+product.get().getProductName()+
                    " has been activated, now you can add multiple variations and many more to it.", product.get().getSeller().getEmail());
            productRepository.activateProduct(product.get().getProductId(),true);
        }
        catch (Exception ex) {
            return "Mail sending Failed... Product is not activated yet... please try again...";
        }
            return "Product activated";
    }
        else
            return "Product is already activated";
    }


}

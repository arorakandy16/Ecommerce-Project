package com.project.ecommerce.service;

import com.project.ecommerce.dto.ProductDto;
import com.project.ecommerce.entity.Product;
import com.project.ecommerce.entity.ProductCategory;
import com.project.ecommerce.entity.Seller;
import com.project.ecommerce.exception.InvalidCategoryOrFieldIdException;
import com.project.ecommerce.exception.Message;
import com.project.ecommerce.exception.ProductNotFoundException;
import com.project.ecommerce.exception.ValidationException;
import com.project.ecommerce.repository.ProductCategoryRepository;
import com.project.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Component
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    SellerService sellerService;

    @Autowired
    EmailService emailService;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    private MessageSource messageSource;


    //Add a product

    public String addProduct(Product product, Locale locale) {

        Seller seller = sellerService.getLoggedInSeller();
        product.setSeller(seller);
        if (product.getBrand() != null && product.getProductName() != null && product.getProductcategory() != null) {
            try {
                emailService.sendEmail("REGARDING PRODUCT ACTIVATION", "Hii Admin, \n There is a pending task for you. Seller " + seller.getFirstname() +
                        " added a product '" + product.getProductName() + "', Could you please verify it and activate it ASAP.  ", seller.getEmail());
                productRepository.save(product);
            } catch (Exception ex) {
                throw new ValidationException("Mail sending Failed... Product is not added yet... please try again...");
            }
            throw new Message(messageSource.getMessage("product.added.message", null, locale));
        }
        else
            throw new InvalidCategoryOrFieldIdException("Fields should not be null");
    }



    //View Product as Seller

    public Optional<Product> viewProductAsSeller(Long productId) {
        Seller seller = sellerService.getLoggedInSeller();
        Optional<Product> product = productRepository.findByIdAndSellerId(productId, seller.getUserid());
        if (product.get().getProductId() != null)
            if(!product.get().isIs_deleted())
                return product;
            else
                throw new ProductNotFoundException("Product has been deleted from the database");
        else
            throw new ProductNotFoundException("Product does not exist");
    }

    public List<Product> viewAllProductAsSeller() {
        Seller seller = sellerService.getLoggedInSeller();
        return productRepository.findAllBySeller(seller.getUserid(),
                PageRequest.of(0, 10,
                        Sort.Direction.ASC, "product_id"));
    }


    //Delete a product

    @Transactional
    public String deleteProduct(Long productId) {
        Seller seller = sellerService.getLoggedInSeller();
        Optional<Product> product=productRepository.findById(productId);
        if(!product.isPresent())
            throw new ProductNotFoundException("Product does not exist");
        if(product.get().getSeller().getUserid()!=seller.getUserid())
            throw new ProductNotFoundException("Product does not exist");
            if(product.get().isIs_deleted())
                throw new ValidationException("Product is already deleted");
            productRepository.deleteProduct(productId);
            return "Product deleted successfully";
    }


    //Update Product

    @Transactional
    public void updateProduct(Long productId,ProductDto productDto,Locale locale) {
        Seller seller = sellerService.getLoggedInSeller();
        Optional<Product> product = productRepository.findByIdAndSellerId(productId, seller.getUserid());
        if(!product.isPresent())
            throw new ProductNotFoundException("Product does not exist");

        if (productDto.getBrand() != null)
            product.get().setBrand(productDto.getBrand());
        if (productDto.getDescription() != null)
            product.get().setDescription(productDto.getDescription());
        if (productDto.getProductName() != null)
            product.get().setProductName(productDto.getProductName());
        if (productDto.isIs_cancellable())
            product.get().setIs_cancellable(productDto.isIs_cancellable());
        if (productDto.isIs_returnable())
            product.get().setIs_returnable(productDto.isIs_returnable());
        Product product1 = product.get();
        productRepository.save(product1);

        throw new Message(messageSource.getMessage
                ("product.update.message", null, locale));

    }



    //View a Product as Customer

    public Optional<Product> viewAProductAsCustomer(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.get() != null)
            return product;
        else
            throw new ProductNotFoundException("Product Does not exist");
    }



    //View All Products as Customer

    public List<Product> viewAllProductsAsCustomer(Long categoryId) {
        List<Product> products=productRepository.findAllByCategoryIdForCustomerAdmin(categoryId, PageRequest.of(0, 10, Sort.Direction.ASC, "product_id"));
        if (products.isEmpty())
            throw new ProductNotFoundException("Invalid Category Id");
        return products;
    }




    //View Product as Admin

    public Optional<Product> viewAProductAsAdmin(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (!product.isPresent())
            throw new ProductNotFoundException("Invalid Category Id");
        return product;
    }




    //View All Products as Admin

    public List<Product> viewAllProductsAsAdmin(Long categoryId) {
        List<Product> products=productRepository.findAllByCategoryIdForCustomerAdmin(categoryId, PageRequest.of(0, 10, Sort.Direction.ASC, "product_id","seller_user_id"));
        if (products.isEmpty())
            throw new ProductNotFoundException("Invalid Category Id");
        return products;
    }




    //De-activate Product

    public String deactivateProduct(Long productId, Locale locale) {
        Optional<Product> product=productRepository.findById(productId);
        if(product.get().isIs_active()){
            try {
                emailService.sendEmail("REGARDING PRODUCT ACTIVATION", "Hii, \n We have found a suspicious product that you added," +
                        " it violates our terms and conditions, that's why we have to de-activate it.\n\n"+ "Product Details are : \n\t Category : " +
                        product.get().getProductcategory().getName() +"\n\t Name : "+ product.get().getProductName()+" \n\t Brand : " + product.get().getBrand() +"\n\t Details : " +
                        product.get().getDescription() , product.get().getSeller().getEmail());
                productRepository.isActivateProduct(product.get().getProductId(),false);
            }
            catch (Exception ex) {
                return "Mail sending Failed... Product is activated yet... please try again...";
            }
            throw new Message(messageSource.getMessage("product.deactivated.message", null, locale));

        }
        else
            return "Product is already de-activated";
    }




    //Activate a Product

    public String activateProduct(Long productId,Locale locale) {
        Optional<Product> product=productRepository.findById(productId);

        if(!product.get().isIs_active()){

            try {
                emailService.sendEmail("REGARDING PRODUCT ACTIVATION",
                        "Hii, \n Your product "+product.get().getProductName()+
                        " has been activated, now you can add multiple variations " +
                                "and many more to it.", product.get().getSeller().getEmail());

                productRepository.isActivateProduct(product.get().getProductId(),true);
            }

            catch (Exception ex) {
                return "Mail sending Failed... Product is not activated yet... please try again...";
            }

            throw new Message(messageSource.getMessage("product.activated.message", null, locale));

        }
        else
            return "Product is already activated";
    }




    //Similar Product Variation

    public List<Product> similarProductVariation(Long productId) {
        Optional<Product> product = productRepository.findById(productId);

        if(!product.isPresent())
            throw new ProductNotFoundException("Product Id is not valid");

        if(!product.get().isIs_active())
            throw new ProductNotFoundException("Product Id is not valid");

        if(product.get().isIs_deleted())
            throw new ProductNotFoundException("Product Id is not valid");

        Optional<ProductCategory> productCategory
                = productCategoryRepository.findById
                (product.get().getProductcategory().getPcId());

        List<Product> products
                = productRepository.findAllByCategoryIdForCustomerAdmin
                (productCategory.get().getPcId(),
                        PageRequest.of(0, 10, Sort.Direction.ASC, "product_id"));
        return products;
    }
}
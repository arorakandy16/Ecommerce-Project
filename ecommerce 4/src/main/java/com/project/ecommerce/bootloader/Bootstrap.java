package com.project.ecommerce.bootloader;

import com.project.ecommerce.entity.*;
import com.project.ecommerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements ApplicationRunner {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    MetadataFieldRepository metadataFieldRepository;
    @Autowired
    MetadataFieldValuesRepository metadataFieldValuesRepository;
    @Autowired
    ProductCategoryRepository productCategoryRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        //saving the roles in to the role table

        Role admin = new Role(1l, "ROLE_ADMIN");
        Role seller = new Role(2l, "ROLE_SELLER");
        Role customer = new Role(3l, "ROLE_CUSTOMER");
        roleRepository.save(admin);
        roleRepository.save(seller);
        roleRepository.save(customer);



        //saving the admin

        Admin admin1 = new Admin("kandy@tothenew.com", "Kandy", "", "Arora");
        admin1.setPassword(passwordEncoder.encode("Kandy@2145"));
        userRepository.save(admin1);

        Admin admin2 = new Admin("admin2@tothenew.com", "Admin2", "", "");
        admin2.setPassword(passwordEncoder.encode("Kandy@2145"));
        userRepository.save(admin2);

        Admin admin3 = new Admin("admin3@tothenew.com", "Admin", "", "Admin");
        admin3.setPassword(passwordEncoder.encode("Kandy@2145"));
        userRepository.save(admin2);



        //saving the seller;

        Seller seller1 = new Seller("seller1@xyz.com", "seller1", "", "", "gst1", "seller1_company", 250840l);
        seller1.setPassword(passwordEncoder.encode("Kandy@2145"));


        Seller seller2 = new Seller("seller2@xyz.com", "seller2", "", "", "gst2", "seller2_company", 250841l);
        seller2.setPassword(passwordEncoder.encode("Kandy@2145"));

        userRepository.save(seller1);
        userRepository.save(seller2);

        Address address4 = new Address("Delhi", "Delhi", "India", "Hauz Khas Village", "110016", "Store");
        address4.setSeller(seller1);
        addressRepository.save(address4);


        //saving the customer

        Customer customer1 = new Customer("ritik@mru.edu.in", "Ritik", "", "Mehta", 9728405565l);
        customer1.setPassword(passwordEncoder.encode("Kandy@2145"));

        Customer customer2 = new Customer("muskan@niift.edu.in", "Muskan", "", "Arora", 7015317584l);
        customer2.setPassword(passwordEncoder.encode("Kandy@2145"));

        userRepository.save(customer1);
        userRepository.save(customer2);

        Address address1 = new Address("Faridabad", "Haryana", "India", "169, Second Floor, Sector 21C", "121001", "Flat");
        Address address2 = new Address("Hisar", "Haryana", "India", "SCO-100, Urban Estate", "125001", "Madame Store");
        Address address3 = new Address("Hisar", "Haryana", "India", "158, Sector 16", "125001", "Home");

        address1.setCustomer(customer1);
        address2.setCustomer(customer1);
        address3.setCustomer(customer2);

        addressRepository.save(address1);
        addressRepository.save(address2);
        addressRepository.save(address3);



        //saving the categories & sub - categories

        ProductCategory electronics = new ProductCategory("Electronics");
        productCategoryRepository.save(electronics);


        ProductCategory phones = new ProductCategory("Phones");
        phones.setParentCategory(electronics);
        productCategoryRepository.save(phones);

        ProductCategory laptops = new ProductCategory("Laptops");
        laptops.setParentCategory(electronics);
        productCategoryRepository.save(laptops);


        ProductCategory clothing = new ProductCategory("Clothing & Fashion");
        productCategoryRepository.save(clothing);


        ProductCategory jacketandcoats = new ProductCategory("Jacket and Coats");
        jacketandcoats.setParentCategory(clothing);
        productCategoryRepository.save(jacketandcoats);

        ProductCategory shirts = new ProductCategory("Shirts & T-shirts");
        shirts.setParentCategory(clothing);
        productCategoryRepository.save(shirts);

        ProductCategory jeans = new ProductCategory("Jeans & Pants");
        jeans.setParentCategory(clothing);
        productCategoryRepository.save(jeans);

        ProductCategory shoes = new ProductCategory("Shoes");
        shoes.setParentCategory(clothing);
        productCategoryRepository.save(shoes);



        //saving the product

        Product product1 = new Product("Iphone 11 Pro Max", "Smart-Phone", "Apple", false, false, false);
        product1.setSeller(seller1);
        product1.setProductcategory(phones);
        productRepository.save(product1);

        Product product2 = new Product("One Plus 7T Pro", "Smart-Phone", "One Plus", false, false, false);
        product2.setSeller(seller1);
        product2.setProductcategory(phones);
        productRepository.save(product2);

        Product product3 = new Product("Samsung Galaxy S20", "Smart-Phone", "Samsung", false, false, false);
        product3.setSeller(seller1);
        product3.setProductcategory(phones);
        productRepository.save(product3);

        Product product4 = new Product("Redmi K20 Pro", "Smart-Phone", "Xiaomi", false, false, false);
        product4.setSeller(seller1);
        product4.setProductcategory(phones);
        productRepository.save(product4);



        //Category meta data fields for Electronics

        CategoryMetadataField os=new CategoryMetadataField("Operating System");
        metadataFieldRepository.save(os);
        CategoryMetadataField ram=new CategoryMetadataField("Ram");
        metadataFieldRepository.save(ram);
        CategoryMetadataField processor=new CategoryMetadataField("Processor");
        metadataFieldRepository.save(processor);
        CategoryMetadataField internalMemory=new CategoryMetadataField("Internal Memory");
        metadataFieldRepository.save(internalMemory);



        //Category meta data fields for Clothing

        CategoryMetadataField clothMaterial=new CategoryMetadataField("Cloth Material");
        metadataFieldRepository.save(clothMaterial);
        CategoryMetadataField size=new CategoryMetadataField("Size");
        metadataFieldRepository.save(size);
        CategoryMetadataField colour=new CategoryMetadataField("Colour");
        metadataFieldRepository.save(colour);
        CategoryMetadataField soleMaterial=new CategoryMetadataField("Sole Material");
        metadataFieldRepository.save(soleMaterial);



        //For Category of Phones....

        CategoryMetadataFieldValues osValues=new CategoryMetadataFieldValues
                ("iOS, Android, Windows");
        osValues.setCategoryMetadataField(os);
        osValues.setProductCategory(phones);
        metadataFieldValuesRepository.save(osValues);

        CategoryMetadataFieldValues ramValues=new CategoryMetadataFieldValues
                ("4GB,6GB,8GB,12GB");
        ramValues.setCategoryMetadataField(ram);
        ramValues.setProductCategory(phones);
        metadataFieldValuesRepository.save(ramValues);

        CategoryMetadataFieldValues processorValues=new CategoryMetadataFieldValues
                ("Apple Bionic processor," +
                        "Qualcomm® Snapdragon™ processor ,Samsung Exynos processor");
        processorValues.setCategoryMetadataField(processor);
        processorValues.setProductCategory(phones);
        metadataFieldValuesRepository.save(processorValues);

        CategoryMetadataFieldValues internalMemoryValues=new CategoryMetadataFieldValues
                ("32GB,64GB,128GB,256GB,512GB");
        internalMemoryValues.setCategoryMetadataField(internalMemory);
        internalMemoryValues.setProductCategory(phones);
        metadataFieldValuesRepository.save(internalMemoryValues);



        //For Category of Clothing....

        //For Shirts

        CategoryMetadataFieldValues shirtSizeVal=new CategoryMetadataFieldValues
                ("XS, S, M, L, XL, 2XL");
        shirtSizeVal.setCategoryMetadataField(size);
        shirtSizeVal.setProductCategory(shirts);
        metadataFieldValuesRepository.save(shirtSizeVal);

        CategoryMetadataFieldValues shirtColorVal=new CategoryMetadataFieldValues
                ("Black, Red, Green, Blue, Pink, Orange, " +
                        "Navy blue, Gray, Yellow, Maroon, White, Rainbow ");
        shirtColorVal.setCategoryMetadataField(colour);
        shirtColorVal.setProductCategory(shirts);
        metadataFieldValuesRepository.save(shirtColorVal);

        CategoryMetadataFieldValues clothMaterialVal=new CategoryMetadataFieldValues
                ("Cotton, Poly-Cotton, Polystyrene");
        clothMaterialVal.setCategoryMetadataField(clothMaterial);
        clothMaterialVal.setProductCategory(shirts);
        metadataFieldValuesRepository.save(clothMaterialVal);


        //For Shoes

        CategoryMetadataFieldValues shoeSizeVal=new CategoryMetadataFieldValues
                ("4,5,6,7,8,9,10");
        shoeSizeVal.setCategoryMetadataField(size);
        shoeSizeVal.setProductCategory(shoes);
        metadataFieldValuesRepository.save(shoeSizeVal);

        CategoryMetadataFieldValues shoeColorVal=new CategoryMetadataFieldValues
                ("Black, Red, Green, Blue");
        shoeColorVal.setCategoryMetadataField(colour);
        shoeColorVal.setProductCategory(shoes);
        metadataFieldValuesRepository.save(shoeColorVal);

        CategoryMetadataFieldValues soleMaterialVal=new CategoryMetadataFieldValues
                ("Fiber, Memory-Foam");
        soleMaterialVal.setCategoryMetadataField(soleMaterial);
        soleMaterialVal.setProductCategory(shoes);
        metadataFieldValuesRepository.save(soleMaterialVal);

    }
}

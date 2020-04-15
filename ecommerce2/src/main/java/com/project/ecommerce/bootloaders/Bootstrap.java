package com.project.ecommerce.bootloaders;

import com.project.ecommerce.entities.*;
import com.project.ecommerce.entities.Address;
import com.project.ecommerce.repositries.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class Bootstrap implements ApplicationRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        //saving the roles in to the role table
        Role admin = new Role(1l, "ROLE_ADMIN");
        Role seller = new Role(2l, "ROLE_SELLER");
        Role customer = new Role(3l, "ROLE_CUSTOMER");
        roleRepository.save(admin);
        roleRepository.save(seller);
        roleRepository.save(customer);

        //saving the admin

        Admin admin1=new Admin("kandy@tothenew.com","Kandy","","Arora");
        admin1.setPassword(passwordEncoder.encode("Kandy@2145"));
        userRepository.save(admin1);

        Admin admin2=new Admin("admin@tothenew.com","Admin","","Admin");
        admin2.setPassword(passwordEncoder.encode("Admin@2145"));
        userRepository.save(admin2);



        //saving the seller;
        Seller seller1 = new Seller("seller1@xyz.com", "seller1", "", "", "gst1", "seller1_company", 250840l);
        seller1.setPassword(passwordEncoder.encode("Seller@1"));


        Seller seller2 = new Seller("seller2@xyz.com", "seller2", "", "", "gst2", "seller2_company", 250841l);
        seller2.setPassword(passwordEncoder.encode("Seller@2"));

        userRepository.save(seller1);
        userRepository.save(seller2);

        Address address4=new Address("Delhi","Delhi","India","Gtb Nagar","110009","Store");
        address4.setSeller(seller1);
        addressRepository.save(address4);



        //saving the customer
        Customer customer1 = new Customer("ritik@mru.edu.in", "Ritik", "", "Mehta", 9728405565l);
        customer1.setPassword(passwordEncoder.encode("Ritik#1020"));

        Customer customer2 = new Customer("muskan@niift.edu.in", "Muskan", "", "Arora", 7015317584l);
        customer2.setPassword(passwordEncoder.encode("Muskan#2012"));

        userRepository.save(customer1);
        userRepository.save(customer2);

        Address address1=new Address("Faridabad","Haryana","India","169, Second Floor, Sector 21C","121001","Flat");
        Address address2=new Address("Hisar","Haryana","India","SCO-100, Urban Estate","125001","Madame Store");
        Address address3=new Address("Hisar","Haryana","India","158, Sector 16","125001","Home");

        address1.setCustomer(customer1);
        address2.setCustomer(customer1);
        address3.setCustomer(customer2);

        addressRepository.save(address1);
        addressRepository.save(address2);
        addressRepository.save(address3);












//        //User and Address mapping
//
//        User user = new User();
//        user.setFirstname("Kandy");
//        user.setMiddlename(" ");
//        user.setLastname("Arora");
//        user.setEmail("arorakandy16@gmail.com");
//        user.setPassword("Kandy@2145");
////*************************************************************************************************************
//
////        Multiple Addresses
//
//        List<Address> l1 = new ArrayList<Address>();
//        Address address1 = new Address();
//        address1.setLabel("Home");
//        address1.setAddress("24, Sector 16");
//        address1.setCity("Hisar");
//        address1.setState("Haryana");
//        address1.setCountry("India");
//        address1.setZipcode("125001");
////        address1.setUser(user);
//        l1.add(address1);
//        Address address2 = new Address();
//        address2.setLabel("Flat");
//        address2.setAddress("019,Ground Floor, ST1, Logix Blossom Zest Society, Sector 143");
//        address2.setCity("Noida");
//        address2.setState("Uttar Pradesh");
//        address2.setCountry("India");
//        address2.setZipcode("201306");
////        address2.setUser(user);
//        l1.add(address2);
//        Address address3 = new Address();
//        address3.setLabel("Office");
//        address3.setAddress("To The New company, NSL Techzone, Sector 144");
//        address3.setCity("Noida");
//        address3.setState("Uttar Pradesh");
//        address3.setCountry("India");
//        address3.setZipcode("201306");
////        address3.setUser(user);
//        l1.add(address3);
////        user.setAddress(l1);
//        userRepository.save(user);
////*************************************************************************************************************
//
////        Roles
//
//        List<Role> roles = new ArrayList<Role>();
//        Role role1 = new Role();
//        Role role2 = new Role();
//        Role role3 = new Role();
//        role1.setRoleid(1);
//        role2.setRoleid(2);
//        role3.setRoleid(3);
//        role1.setAuthority("Admin");
//        role2.setAuthority("Seller");
//        role3.setAuthority("Customer");
//        roles.add(role1);
//        roles.add(role2);
//        roles.add(role3);
////        user.setRole(roles);
////**************************************************************************************************************
//
////        Mapping of customer with user
//
//        Customer customer = new Customer();
//        customer.setContact("9728405565");
//        customer.setFirstname("Ritik");
//        customer.setMiddlename(" ");
//        customer.setLastname("Mehta");
//        customer.setEmail("ritikmehta614@gmail.com");
//        customer.setPassword("Ritik#1234");
//        List<Address> l2 = new ArrayList<Address>();
//        Address address4 = new Address();
//        address4.setLabel("Flat");
//        address4.setAddress("169, Sector 21C");
//        address4.setCity("Faridabad");
//        address4.setState("Haryana");
//        address4.setCountry("India");
//        address4.setZipcode("121004");
////        address4.setUser(user);
//        l2.add(address4);
////        customer.setAddress(l2);
//        userRepository.save(customer);
////**************************************************************************************************************
//
////        Mapping of seller with user
//
//        Seller seller = new Seller();
//        seller.setGST("23.55");
//        seller.setCompanyContact(250840);
//        seller.setCompanyName("MRU");
//        seller.setFirstname("Lizel");
//        seller.setMiddlename(" ");
//        seller.setLastname("Mehta");
//        seller.setEmail("lizelmehta4@gmail.com");
//        seller.setPassword("Lizel#1020");
//        List<Address> l3 = new ArrayList<Address>();
//        Address address5 = new Address();
//        address5.setLabel("Hostel");
//        address5.setAddress("MRU Hostel, Sector 43");
//        address5.setCity("Faridabad");
//        address5.setState("Haryana");
//        address5.setCountry("India");
//        address5.setZipcode("121004");
////        address5.setUser(seller);
//        l3.add(address5);
////        seller.setAddress(l3);
//        userRepository.save(seller);
////**************************************************************************************************************
//
////  Customer and Order Mapping
//
//        Customer customer1 = new Customer();
//        customer1.setContact("7015417584");
//        customer1.setFirstname("Muskan");
//        customer1.setMiddlename(" ");
//        customer1.setLastname("Arora");
//        customer1.setEmail("muskanarora20121997@gmail.com");
//        customer1.setPassword("Muskan@2012");
//        List<Address> l4 = new ArrayList<Address>();
//        Address address6 = new Address();
//        address6.setLabel("Home");
//        address6.setAddress("158, Sector 16");
//        address6.setCity("Hisar");
//        address6.setState("Haryana");
//        address6.setCountry("India");
//        address6.setZipcode("125001");
////        address6.setUser(user);
//        l4.add(address6);
////        customer1.setAddress(l4);
//
//        //-------------------------------------------------------------------------------------------------------
//
//        List<Orders> orders=new ArrayList<Orders>();
//        Orders order1=new Orders();
//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.YEAR, 2020);
//        cal.set(Calendar.MONTH, Calendar.MARCH);
//        cal.set(Calendar.DAY_OF_MONTH, 31);
//        Date dateRepresentation = cal.getTime();
//        order1.setDateCreated(dateRepresentation);
//        order1.setAmountPaid(5000);
//        order1.setPaymentMethod("COD");
//
//
//        Orders order2=new Orders();
//        Calendar cal1 = Calendar.getInstance();
//        cal1.set(Calendar.YEAR, 2020);
//        cal1.set(Calendar.MONTH, Calendar.APRIL);
//        cal1.set(Calendar.DAY_OF_MONTH,5);
//        Date dateRepresentation1 = cal1.getTime();
//        order2.setDateCreated(dateRepresentation1);
//        order2.setAmountPaid(7000);
//        order2.setPaymentMethod("Card");
//        orders.add(order1);
//        orders.add(order2);
//
////        customer1.setOrders(orders);
//        order1.setCustomer(customer1);
//        order2.setCustomer(customer1);
//
//        orderRepository.save(order1);


        //Product and ProductCategory Mapping-------------------

//        ProductCategory productCategory=new ProductCategory();
//        productCategory.setName("Clothes");
//
//
//        List<Product> p1=new ArrayList<Product>();
//        Product product1=new Product();
//        product1.setProductName("T-Shirt");
//        product1.setDescription("Black medium size T-shirt");
//        product1.setBrand("Zara");
//          Seller seller1=new Seller();
//          seller1.setGST(23.55);
//          seller1.setCompanyContact(12345);
//          seller1.setCompanyName("Zara");
//          seller1.setFirstname("Arjun");
//          seller1.setMiddlename(" ");
//          seller1.setLastname("Arora");
//          seller.setPassword("Arjun07@@");
//          seller1.setEmail("arjun2010@gmail.com");
//          product1.setSeller(seller1);
//        product1.setProductcategory(productCategory);


//        //----------Adding Variants----------------------------------
//        List<ProductVariant> productVariants=new ArrayList<ProductVariant>();
//        ProductVariant productVariant1=new ProductVariant();
//        productVariant1.setQuantityAvailable(2);
//        productVariant1.setPrice(1500.5);
//        productVariant1.setPrimary_Image_Name("resources/Images/skirt.jpg");
//        productVariant1.setProduct(product1);
//
//        ProductVariant productVariant2=new ProductVariant();
//        productVariant2.setQuantityAvailable(3);
//        productVariant2.setPrice(2000);
//        productVariant2.setPrimary_Image_Name("resources/Images/skirt.jpg");
//        productVariant2.setProduct(product1);
//
//        productVariants.add(productVariant1);
//        productVariants.add(productVariant2);
//        product1.setProductVariants(productVariants);
//
//
//        Product product2=new Product();
//        product2.setProductName("Jeans");
//        product2.setDescription("Blue medium size jeans");
//        product2.setBrand("Levis");
//        Seller seller2=new Seller();
//        seller2.setGST(23.55);
//        seller2.setCompanyContact(12345);
//        seller2.setCompanyName("Levis");
//        seller2.setFirstname("Simfi");
//        seller2.setMiddlename(" ");
//        seller2.setLastname("Arora");
//        seller2.setPassword("Simfi07@@");
//        seller2.setEmail("simfi@gmail.com");
//        product2.setSeller(seller2);
//        product2.setProductcategory(productCategory);
//
//        Product product3=new Product();
//        product3.setProductName("plazo");
//        product3.setDescription("Blue medium size plazo");
//        product3.setBrand("");
////        Seller seller2=new Seller();
////        seller2.setGST(23.55);
////        seller2.setCompanyContact(12345);
////        seller2.setCompanyName("Levis");
////        seller2.setFirstname("Anamika");
////        seller2.setMiddlename(" ");
////        seller2.setLastname("Arora");
////        seller2.setEmail("anamika@gmail.com");
//        product3.setSeller(seller2);
//        product3.setProductcategory(productCategory);
//
//        p1.add(product1);
//        p1.add(product2);
//        p1.add(product3);
//
//        productCategory.setProducts(p1);
//        productRepository.save(productCategory);
//
//











        //------------------------Product and ProductVariation Mapping---------------
//        Product product3=new Product();
//        product3.setProductName("Iphone");
//        product3.setDescription("64 GB ,Iphone 11 pro max");
//        product3.setBrand("Apple");
////        product3.setProductVariants();
////        product3.setProductcategory();
//        Seller seller2=new Seller();
//        seller2.setGST(23.55);
//        seller2.setCompanyContact(12345);
//        seller2.setCompanyName("Apple");
//        seller2.setFirstname("Virender);
//        seller2.setMiddlename(" ");
//        seller2.setLastname("dawar");
//        seller2.setEmail("virender@gmail.com");
//        product2.setSeller(seller2);
//
//        ProductCategory productCategory2=new ProductCategory();
//        productCategory2.setName("Electronics");
//        productCategory2.set
//        product1.setProductcategory(productCategory);


         //-----------User and Role mapping------------------------------------

//        List<User> users = new ArrayList<User>();
//        User user1 = new User();
//        user1.setFirstname("Pulkit");
//        user1.setMiddlename(" ");
//        user1.setLastname("Kathuria");
//        user1.setEmail("pulkitkathuria@gmail.com");
//        user1.setPassword("****");
//        List<Address> l2=new ArrayList<Address>();
//        Address address1=new Address();
//        address1.setCity("delhi");
//        address1.setCountry("india");
//        address1.setLabel("office");
//        address1.setState("north delhi");
//        address1.setZipcode("110009");
//        address1.setAddress("ground");
//        address1.setUser(user1);
//        l2.add(address1);
//        users.add(user1);
//
//        List<Role> roles = new ArrayList<Role>();
//        Role role1 = new Role();
//        Role role2 = new Role();
//        role1.setRole_authority("Admin");
//        role2.setRole_authority("User");
//        role1.setUser(users);
//        role2.setUser(users);
//
//        roles.add(role1);
////        roles.add(role2);
//        user1.setRole(roles);
//        roleRepository.save(role1);
//        roleRepository.save(role2);
//


//
//        User user2=new User();
//        user2.setFirstname("Kandu");
//        user2.setMiddlename(" ");
//        user2.setLastname("Kat");
//        user2.setEmail("pulkitkathuria@gmail.com");
//        user2.setPassword("****");
//        List<Address> l3=new ArrayList<Address>();
//        Address address2=new Address();
//        address2.setCity("delhi");
//        address2.setCountry("india");
//        address2.setLabel("office");
//        address2.setState("north delhi");
//        address2.setZipcode("110009");
//        address2.setAddress("ground");
//        address2.setUser(user2);
//        l3.add(address2);
//
//
//        user1.setAddress(l2);
//
//        users.add(user1);
//        role.setUser(users);
//        userRepository.save(user1);

    }
}
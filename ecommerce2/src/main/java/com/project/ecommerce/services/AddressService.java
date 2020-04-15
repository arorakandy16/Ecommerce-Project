package com.project.ecommerce.services;

import com.project.ecommerce.entities.Address;
import com.project.ecommerce.entities.Customer;
import com.project.ecommerce.entities.Seller;
import com.project.ecommerce.repositries.AddressRepository;
import com.project.ecommerce.repositries.CustomerRepository;
import com.project.ecommerce.repositries.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
public class AddressService {


    @Autowired
    AddressRepository addressRepository;

    @Autowired
    CustomerService customerService;
    
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    SellerService sellerService;

    @Autowired
    SellerRepository sellerRepository;

    public Set<Address> viewCustomerAddress() {
        Customer customer = customerService.getLoggedInCustomer();
        Long id = customer.getUserid();
        return addressRepository.findAllAddressByCustomer(id);
    }

    public void addCustomerAddress(Address address) {
        Customer customer = customerService.getLoggedInCustomer();
            address.setCustomer(customer);
            addressRepository.save(address);
    }
    
    @Transactional
    public String updateCustomerAddress(Address address, Long addressId) {
        Customer customer=customerService.getLoggedInCustomer();
        Address address1 = addressRepository.findByUserIdAndAddressId(addressId,customer.getUserid());
        if(address1.getAddressId()!=null){
            address.setAddressId(addressId);
            address.setCustomer(customer);
            addressRepository.save(address);
            return "Address Updated";
        }
        else 
            return "Address with AddressId "+addressId+" is not associated with user "+customer.getFirstname();
    }

    @Transactional
    public String deleteCustomerAddress(Long addressId) {
        Customer customer=customerService.getLoggedInCustomer();
        Address address1 = addressRepository.findByUserIdAndAddressId(addressId,customer.getUserid());
        if(address1.getAddressId()!=null){
            addressRepository.deleteById(Math.toIntExact(addressId));
            return "Address deleted successfully";
        }
        else
            return "You can not delete Address with AddressId "+addressId+" because it is not associated with user "+customer.getFirstname();
    }

    @Transactional
    public String updateSellerAddress(Address address, Long addressId) {
        Seller seller=sellerService.getLoggedInSeller();
        Address address1 = addressRepository.findBySellerIdAndAddressId(addressId,seller.getUserid());
        if(address1.getAddressId()!=null){
            address.setAddressId(addressId);
            address.setSeller(seller);
            addressRepository.save(address);
            return "Address Updated";
        }
        else
            return "Address with AddressId "+addressId+" is not associated with user "+seller.getFirstname();
    }
}

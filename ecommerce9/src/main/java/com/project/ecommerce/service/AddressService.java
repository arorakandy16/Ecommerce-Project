package com.project.ecommerce.service;

import com.project.ecommerce.entity.Address;
import com.project.ecommerce.entity.Customer;
import com.project.ecommerce.entity.Seller;
import com.project.ecommerce.exception.Message;
import com.project.ecommerce.exception.UserNotFoundException;
import com.project.ecommerce.exception.ValidationException;
import com.project.ecommerce.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Locale;
import java.util.Set;

@Service
public class AddressService {


    @Autowired
    AddressRepository addressRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    SellerService sellerService;

    @Autowired
    private MessageSource messageSource;

    // View all Customer Address

    public Set<Address> viewCustomerAddress() {
        Customer customer = customerService.getLoggedInCustomer();
        Long id = customer.getUserid();
        return addressRepository.findAllAddressByCustomer(id);
    }

    //Add new Customer Address

    public void addCustomerAddress(Address address, Locale locale) {
        Customer customer = customerService.getLoggedInCustomer();
            address.setCustomer(customer);
            addressRepository.save(address);
            throw new Message(messageSource.getMessage
                    ("customer.add.address.message",null,locale));
    }


    //Update Customer Address

    @Transactional
    public void updateCustomerAddress(Address address, Long addressId,Locale locale) {
        Customer customer=customerService.getLoggedInCustomer();
        Address address1 = addressRepository.findByUserIdAndAddressId
                (addressId,customer.getUserid());
        if(address1.getAddressId()==null)
            throw new UserNotFoundException("Address with AddressId " +
                    ""+addressId+" is not associated with user "+customer.getFirstname());
            address.setAddressId(addressId);
            address.setCustomer(customer);
            addressRepository.save(address);
        throw new Message(messageSource.getMessage
                ("customer.update.address.message",null,locale));
        }


    //Delete Customer Address

    @Transactional
    public void deleteCustomerAddress(Long addressId,Locale locale) {
        Customer customer=customerService.getLoggedInCustomer();
        Address address1 = addressRepository.findByUserIdAndAddressId
                (addressId,customer.getUserid());
        if(address1.getAddressId()==null)
            throw new ValidationException("You can not delete Address with AddressId " +
                    ""+addressId+" because it is not associated with user "+customer.getFirstname());
            addressRepository.deleteById(addressId);
        throw new Message(messageSource.getMessage
                ("customer.delete.address.message",null,locale));
    }


    //Update Seller address

    @Transactional
    public void updateSellerAddress(Address address, Long addressId, Locale locale) {
        Seller seller=sellerService.getLoggedInSeller();
        Address address1 = addressRepository.findBySellerIdAndAddressId
                (addressId,seller.getUserid());
        if(address1.getAddressId()==null)
            throw new ValidationException("Address with AddressId " +
                    ""+addressId+" " + "is not associated with user "+seller.getFirstname());
            address.setAddressId(addressId);
            address.setSeller(seller);
            addressRepository.save(address);

        throw new Message(messageSource.getMessage
                ("seller.update.address.message",null,locale));
    }
}

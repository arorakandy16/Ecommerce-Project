package com.project.ecommerce.repository;

import com.project.ecommerce.entity.Seller;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface SellerRepository extends CrudRepository<Seller,Long> {

    @Query("from Seller")
    List<Seller> findAllSeller(Pageable pageable);

    @Query("from Seller where email=:username")
    Seller findByUsername(@Param("username") String username);

    @Transactional
    @Modifying
    @Query(value = "update Seller set firstname=:firstname ,middlename=:middleName ," +
            "lastname=:lastName ,GST=:gst ,companyName=:companyName ," +
            "companyContact=:companyContact where userid=:userid")
    void updateSeller(@Param("userid") Long id,@Param("firstname") String firstname,
                            @Param("middleName") String middleName,@Param("lastName") String lastName,
                                @Param("gst") String gst,@Param("companyName") String companyName,
                                    @Param("companyContact") Long  companyContact);
}
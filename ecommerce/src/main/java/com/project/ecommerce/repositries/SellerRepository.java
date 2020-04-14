package com.project.ecommerce.repositries;

import com.project.ecommerce.entities.Seller;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface SellerRepository extends CrudRepository<Seller,Integer> {

    @Query("from Seller")
    List<Seller> findAllSeller(Pageable pageable);
   
    @Query(value = "select user_id from seller where user_id=:user_id",nativeQuery = true)
    Integer sellerUserId(@Param("user_id") Integer user_id);

    @Transactional
    @Modifying
    @Query(value = "update Seller set email=:email ,firstname=:firstname ,middlename=:middleName ," +
            "lastname=:lastName ,GST=:gst ,companyName=:companyName ," +
            "companyContact=:companyContact where userid=:userid")
    void updateSeller(@Param("userid") Integer id,@Param("email") String email,
                        @Param("firstname") String firstname,
                            @Param("middleName") String middleName,@Param("lastName") String lastName,
                                @Param("gst") String gst,@Param("companyName") String companyName,
                                    @Param("companyContact") Integer  companyContact);
}
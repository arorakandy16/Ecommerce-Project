package com.project.ecommerce.repository;

import com.project.ecommerce.entity.VerificationToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface VerificationTokenRepository extends CrudRepository<VerificationToken,Long> {
    @Query(value = "select token_id, generated_date," +
            " token" +
            ", user_email from verification_token " +
            "where token=:token",nativeQuery = true)
    VerificationToken getByToken
            (@Param("token") String token);
}

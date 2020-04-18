package com.project.ecommerce.Repositries;

import com.project.ecommerce.Entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends CrudRepository<User,Long> {
    
    @Query("from User")
    List<User> getAllUsers();

    @Query("from User where email=:username")
    User findByUsername(@Param("username") String username);

    @Transactional
    @Modifying
    @Query(value = "update user set is_active=true where userid=:userid",nativeQuery = true)
    void activateUser(@Param("userid") Long userid);

    @Transactional
    @Modifying
    @Query(value = "update user set is_active=false where userid=:userid",nativeQuery = true)
    void deActivateUser(@Param("userid") Long userid);

    @Transactional
    @Modifying
    @Query(value = "update User set password=:password where email=:email")
    void updatePassword(@Param("password") String password,@Param("email") String email);

    @Transactional
    @Modifying
    @Query(value = "update user set failed_attempts=0 where user_id=:userId",nativeQuery = true)
    void changeFailedAttempts(@Param("userId") Long userId);
}

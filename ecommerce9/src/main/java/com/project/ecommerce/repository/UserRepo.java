package com.project.ecommerce.repository;

import com.project.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {

}

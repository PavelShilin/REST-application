package com.myProject.onlineShop.repository;

import com.myProject.onlineShop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;

public interface UserRepository extends JpaRepository<User,Integer>, CrudRepository<User,Integer> {
    @Query(value = "select id,first_name,last_name FROM users",nativeQuery = true)
    List<Map<Integer,String>> findByUsers();
}

package com.myProject.onlineShop.service;

import com.myProject.onlineShop.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import com.myProject.onlineShop.repository.UserRepository;

import java.util.List;
import java.util.Map;

@Service("UserService")
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User findById(Integer id) {
        return userRepository.getOne(id);
    }


    public List<Map<Integer,String>> findByUsers() {
        return userRepository.findByUsers();
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }


}

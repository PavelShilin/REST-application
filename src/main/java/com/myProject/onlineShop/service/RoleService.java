package com.myProject.onlineShop.service;

import com.myProject.onlineShop.model.Role;
import com.myProject.onlineShop.model.User;
import com.myProject.onlineShop.repository.RoleRepository;
import com.myProject.onlineShop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("RoleService")
public class RoleService  {

    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    public List<Role> findAll() {
        return roleRepository.findAll();
    }
/*    public String findByRole(Integer id) {
        return roleRepository.findByRole(id);
    }*/

    public Role findByIdRole(Integer id) {
        return roleRepository.getOne(id);
    }

    public void saveRole(Role role){
        roleRepository.save(role);
    }

}

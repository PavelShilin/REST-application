package com.myProject.onlineShop.controller;

import com.myProject.onlineShop.model.Role;
import com.myProject.onlineShop.model.User;
import com.myProject.onlineShop.repository.UserRepository;
import com.myProject.onlineShop.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.myProject.onlineShop.service.UserService;


import java.util.*;

@RestController
@RequestMapping(value = "/users")
public class MainController {

    private UserService userService;
    private UserRepository userRepository;

    private RoleService roleService;

    @Autowired
    public MainController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<Map<Integer, String>> finedAll(Model model) {
/*        System.out.println("======================" + userService.findUsers() + "===============================");
        return userService.findUsers();*/
        return userService.findByUsers();
    }


    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<User> customUser(@PathVariable("id") Integer id) {
        System.out.println("+++++++++++++++++++++++++++++++++++++++" + userService.findById(id).getLastName() + "++++++++++++++++++++++++++++++++++");
        User restUser = new User();
        restUser.setFirstName(userService.findById(id).getFirstName());
        restUser.setLastName(userService.findById(id).getLastName());
        restUser.setId(userService.findById(id).getId());
        restUser.setRoles(userService.findById(id).getRoles());

        return new ResponseEntity<User>(restUser, HttpStatus.OK);

    }

    @DeleteMapping(value = "{id}", produces = "application/json")
    public String deleteUser(@PathVariable("id") Integer id) {
        try {
            userService.deleteById(id);
            return "true";
        } catch (Exception e) {
            return "false";
        }
    }


    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE})
    public Map<String, String> createUser(@RequestBody User requestUserDetails) {
        User newUser = new User();
        Map<String, String> mapResponse = new HashMap<>();
        if (validateNameIsUpper(requestUserDetails.getFirstName())) {
            newUser.setFirstName(requestUserDetails.getFirstName());
        } else {
            mapResponse.put("status", "false");
            mapResponse.put("Error", "UpperCaseIsEmpty");
            return mapResponse;
        }
        List<Role> roleFromBD = new ArrayList<>();
        roleFromBD = roleService.findAll();
        boolean flag = false;
        for (Role rol : requestUserDetails.getRoles()) {
            for (Role rolBD : roleFromBD) {
                if (rolBD.equals(rol)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                roleService.saveRole(rol);
            } else {
                flag = false;
            }
        }
        newUser.setFirstName(requestUserDetails.getFirstName());
        newUser.setLastName(requestUserDetails.getLastName());
        newUser.setRoles(requestUserDetails.getRoles());

        userService.saveUser(newUser);
        mapResponse.put("status", "success");
        return mapResponse;


    }

    @GetMapping(value = "/ro", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<Role> getAllRole() {
        System.out.println("==================" + roleService.findAll() + "==================================");
        return roleService.findAll();
    }

    @GetMapping(value = "/test", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public void testing() {
        Role role = new Role();
        role.setRole("Администратор1");
        role.setId(30);
        roleService.saveRole(role);

        Set<Role> setRoles1 = new HashSet<>();
        System.out.println("--------------------------------------" + setRoles1 + "-----------------------------------------");
        User user = new User();
        user.setFirstName("test0");
        user.setLastName("test01");
        user.setRoles(setRoles1);
        userService.saveUser(user);

    }

/*   @GetMapping(path = "/ro/{id}", produces = "application/json")
    public ResponseEntity<Role> customRole(@PathVariable("id") Integer id) {
        Role restRole = new Role();
        restRole.setRole(roleService.findByIdRole(id).getRole());
        return new ResponseEntity<Role>(restRole, HttpStatus.OK);
    }*/


    private boolean validateNameIsUpper(String name) {
        char[] temp = name.toCharArray();
        for (Character character : temp) {
            if (Character.isUpperCase(character)) {
                return true;
            }
        }
        return false;
    }





/*    @PostMapping("/user-update")
    public String updateUser(User user) {
        userService.saveUser(user);
        return "redirect:/users";
    }*/
}

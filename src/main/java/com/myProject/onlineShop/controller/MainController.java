package com.myProject.onlineShop.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myProject.onlineShop.model.Role;
import com.myProject.onlineShop.model.User;
import com.myProject.onlineShop.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.myProject.onlineShop.service.UserService;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/users")
public class MainController {

    private UserService userService;

    private RoleService roleService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<User> finedAll(Model model) {
        List<User> users = userService.findAll();
        // model.addAttribute("users", users);
        return users;
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<User> customUser(@PathVariable("id") Long id) {
        User restUser = new User();
        restUser.setFirstName(userService.findById(id).getFirstName());
        restUser.setLastName(userService.findById(id).getLastName());
        restUser.setId(userService.findById(id).getId());
        return new ResponseEntity<User>(restUser, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}", produces = "application/json")
    public String deleteUser(@PathVariable("id") Long id) {
        try {
            userService.deleteById(id);
            return "true";
        } catch (Exception e) {
            return "false";
        }
    }

    @PostMapping(produces ={MediaType.APPLICATION_JSON_VALUE,
                            MediaType.APPLICATION_XML_VALUE},
                consumes = {MediaType.APPLICATION_JSON_VALUE,
                            MediaType.APPLICATION_XML_VALUE})
    public Map<String,String> createUser(@RequestBody User requestUserDetails) {
        User newUser = new User();
        Map <String,String> mapResponse= new HashMap<>();
        if (validateNameIsUpper(requestUserDetails.getFirstName())){
            newUser.setFirstName(requestUserDetails.getFirstName());
        }
        else {
            mapResponse.put("status","false");
            mapResponse.put("Error","UpperCaseIsEmpty");
            return mapResponse;
        }
        newUser.setFirstName(requestUserDetails.getFirstName());
        newUser.setFirstName(requestUserDetails.getFirstName());
        newUser.setLastName(requestUserDetails.getLastName());
        userService.saveUser(newUser);

        mapResponse.put("status","success");

        return mapResponse;


    }

    @GetMapping(value = "Role", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<Role> getAllRole(){
        List<Role> role = roleService.findAllRole();
        return role;
    }

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

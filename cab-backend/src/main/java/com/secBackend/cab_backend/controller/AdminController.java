package com.secBackend.cab_backend.controller;

import com.secBackend.cab_backend.enumerations.Role;
import com.secBackend.cab_backend.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/admin")
@RestController
public class AdminController {

    private AdminService adminService;
    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }


    @GetMapping("/list-all-customers")
    public ResponseEntity<?> listAllCustomer(){
        return adminService.getAllCustomer();
    }

    @GetMapping("/list-all-drivers")
    public ResponseEntity<?> listAllDriver(){
        return adminService.getAllDriver();
    }
}

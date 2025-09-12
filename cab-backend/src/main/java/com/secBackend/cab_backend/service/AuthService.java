package com.secBackend.cab_backend.service;


import com.secBackend.cab_backend.model.DriverProfile;
import com.secBackend.cab_backend.dataTansferObject.RegisterUserRequest;
import com.secBackend.cab_backend.enumerations.Role;
import com.secBackend.cab_backend.model.User;
import com.secBackend.cab_backend.repository.driverProfileRepository;
import com.secBackend.cab_backend.repository.userRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;


@Service
public class AuthService {


    private userRepository userRepo;
    private driverProfileRepository driverRepo;
    private BCryptPasswordEncoder passwordEncoder;

    //Constructor For The Above Field
    public AuthService(userRepository userRepo,
                       driverProfileRepository driverRepo,
                       BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.driverRepo = driverRepo;
        this.passwordEncoder = passwordEncoder;
    }


    //Register The User
   public ResponseEntity<?> registerUser(RegisterUserRequest registerUserRequest){
       User user = new User();
       user.setUsername(registerUserRequest.getUserName());
       user.setEmail(registerUserRequest.getEmail());
       user.setPhoneNumber(registerUserRequest.getPhoneNumber());
       user.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
       user.setRole(Role.valueOf(registerUserRequest.getRole().toUpperCase()));

       userRepo.save(user);

        //If The Register User Is Driver
       if(user.getRole()==Role.DRIVER && registerUserRequest.getDriverDetails() != null){
           DriverProfile driverProfile = new DriverProfile();
           driverProfile.setUser(user);
           driverProfile.setLicenseNumber(registerUserRequest.getDriverDetails().
                   getLicenseNumber());
           driverProfile.setVehicleNumber(registerUserRequest.getDriverDetails().
                   getVehicleNumber());
            driverRepo.save(driverProfile);

       }
       return  ResponseEntity.status(HttpStatus.OK).body(Map.of("message",
               registerUserRequest.getRole()+" registered successfully!"));
   }

    public Optional<User> findEmail(String email) {
       return userRepo.findByEmail(email);
    }

    public Optional<User> findPhonenumber(String phoneNumber) {
       return  userRepo.findByPhoneNumber(phoneNumber);
    }
}

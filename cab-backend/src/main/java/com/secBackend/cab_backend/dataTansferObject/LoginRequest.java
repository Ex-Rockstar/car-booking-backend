package com.secBackend.cab_backend.dataTansferObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//DTO For LoginRequest
public class LoginRequest {
    private String email;
    private String password;
}

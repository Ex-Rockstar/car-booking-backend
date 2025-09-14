package com.secBackend.cab_backend.dataTansferObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverDetailDto {
    private Long id;
    private Long userId;
    private String userName;
    private String email;
    private String password;
    private String licenseNumber;
    private String vehicleNumber;
}

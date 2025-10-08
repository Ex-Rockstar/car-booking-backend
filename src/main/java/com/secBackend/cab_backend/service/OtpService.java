package com.secBackend.cab_backend.service;

import com.secBackend.cab_backend.model.Otp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {

    private final Random random = new Random();
    private final int OTP_VALID_MINUTES=5;
    private final ConcurrentHashMap<String,Otp>otpStore=new ConcurrentHashMap<>();



    public String generateOtp(String emailId) {
        int otpNum=100000+random.nextInt(900000);
        String otpStr=String.valueOf(otpNum);

        Otp otp=new Otp(otpStr,OTP_VALID_MINUTES);

        otpStore.put(emailId,otp);
        return otpStr;

    }

    public boolean verifyOtp(String emailId, String otp) {
        Otp otpObj=otpStore.get(emailId);
        if(otpObj !=null && !otpObj.isExpired()&&otpObj.getCode().equals(otp)){
            otpStore.remove(emailId);
            return true;
        }
        return false;
    }
}

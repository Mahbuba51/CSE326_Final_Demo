package com.foodibd.backend.controller;

import com.foodibd.backend.dto.user.AddressResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/addresses/default")
    public ResponseEntity<AddressResponseDTO> getDefaultAddress(
            @RequestHeader("Authorization") String authorizationHeader) {

        // Demo default address — no auth/user system implemented yet
        AddressResponseDTO address = AddressResponseDTO.builder()
                .street("12 Gulshan Avenue")
                .city("Dhaka")
                .state("Dhaka Division")
                .postalCode("1212")
                .country("Bangladesh")
                .build();

        return ResponseEntity.ok(address);
    }
}
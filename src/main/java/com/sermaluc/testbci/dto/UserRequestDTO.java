package com.sermaluc.testbci.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record UserRequestDTO(@NotBlank(message = "Name code cannot be empty")
                             String name,
                             @Email(message = "Email should be valid")
                             @NotBlank(message = "Email code cannot be empty")
                             String email,
                             String password,
                             @NotEmpty(message = "Phones must have at least one value")
                             @Valid
                             List<PhoneDTO> phones) {
}
package com._DM.E_commerce.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterDTO(
        @NotBlank String login,
        @NotBlank String senha,
        @NotBlank String role
) {
}

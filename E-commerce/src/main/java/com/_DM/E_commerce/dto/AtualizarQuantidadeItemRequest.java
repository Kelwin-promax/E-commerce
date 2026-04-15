package com._DM.E_commerce.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AtualizarQuantidadeItemRequest(
        @NotNull @Positive Integer quantidade
) {
}

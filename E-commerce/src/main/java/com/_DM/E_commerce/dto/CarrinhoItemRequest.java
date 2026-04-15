package com._DM.E_commerce.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record CarrinhoItemRequest(
        @NotNull UUID produtoId,
        @NotNull @Positive Integer quantidade
) {
}

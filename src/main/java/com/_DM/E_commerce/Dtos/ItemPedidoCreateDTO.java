package com._DM.E_commerce.Dtos;

import java.util.UUID;

public record ItemPedidoCreateDTO(
        UUID produtoId,
        Integer quantidade
) {}

package com._DM.E_commerce.Dtos;

import java.time.Instant;
import java.util.UUID;

public record PagamentoCreateDTO(
        UUID pedidoId,
        Instant momento
) {}

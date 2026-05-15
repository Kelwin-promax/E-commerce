package com._DM.E_commerce.Dtos;

import com._DM.E_commerce.Entity.Pagamento;

import java.time.Instant;
import java.util.UUID;

public record PagamentoDTO(
        UUID id,
        Instant momento,
        UUID pedidoId
) {
    public PagamentoDTO(Pagamento pagamento) {
        this(pagamento.getId(), pagamento.getMomento(), pagamento.getPedido().getId());
    }
}

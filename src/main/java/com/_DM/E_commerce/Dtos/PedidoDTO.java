package com._DM.E_commerce.Dtos;

import com._DM.E_commerce.Entity.Pedido;
import com._DM.E_commerce.Enum.StatusDoPedido;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record PedidoDTO(
        UUID id,
        LocalDate momento,
        StatusDoPedido status,
        UUID clienteId,
        String clienteNome,
        PagamentoDTO pagamento,
        Set<ItemDoPedidoDTO> itens,
        Double total
) {
    public PedidoDTO(Pedido pedido) {
        this(
                pedido.getId(),
                pedido.getMomento(),
                pedido.getStatus(),
                pedido.getCliente().getId(),
                pedido.getCliente().getNome(),
                pedido.getPagamento() != null ? new PagamentoDTO(pedido.getPagamento()) : null,
                pedido.getItens().stream().map(ItemDoPedidoDTO::new).collect(Collectors.toSet()),
                pedido.getTotal()
        );
    }
}

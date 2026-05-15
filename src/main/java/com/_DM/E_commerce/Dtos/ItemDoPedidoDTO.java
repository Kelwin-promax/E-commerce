package com._DM.E_commerce.Dtos;

import com._DM.E_commerce.Entity.ItemDoPedido;

import java.util.UUID;

public record ItemDoPedidoDTO(
        UUID produtoId,
        String produtoNome,
        Integer quantidade,
        Double preco,
        Double subTotal
) {
    public ItemDoPedidoDTO(ItemDoPedido item) {
        this(
                item.getProduto().getId(),
                item.getProduto().getNome(),
                item.getQuantidade(),
                item.getPreco(),
                item.getSubTotal()
        );
    }
}

package com._DM.E_commerce.Dtos;

import java.util.List;
import java.util.UUID;

public record PedidoCreateDTO(
        UUID clienteId,
        List<ItemPedidoCreateDTO> itens
) {}

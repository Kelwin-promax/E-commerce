package com._DM.E_commerce.Dtos;

import java.util.UUID;

public record ProdutoCreateDTO(
        String nome,
        Double preco,
        String descricao,
        String imgUrl,
        java.util.Set<UUID> categoriasIds
) {}

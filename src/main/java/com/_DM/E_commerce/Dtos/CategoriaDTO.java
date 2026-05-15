package com._DM.E_commerce.Dtos;

import com._DM.E_commerce.Entity.Categoria;

import java.util.UUID;

public record CategoriaDTO(
        UUID id,
        String nome
) {
    public CategoriaDTO(Categoria categoria) {
        this(categoria.getId(), categoria.getNome());
    }
}

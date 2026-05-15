package com._DM.E_commerce.Dtos;

import com._DM.E_commerce.Entity.Produto;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record ProdutoDTO(
        UUID id,
        String nome,
        Double preco,
        String descricao,
        String imgUrl,
        Set<CategoriaDTO> categorias
) {
    public ProdutoDTO(Produto produto) {
        this(
                produto.getId(),
                produto.getNome(),
                produto.getPreco(),
                produto.getDescricao(),
                produto.getImgUrl(),
                produto.getCategorias().stream().map(CategoriaDTO::new).collect(Collectors.toSet())
        );
    }
}

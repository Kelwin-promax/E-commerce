package com._DM.E_commerce.Service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service("produtoService")
public class Produto {

    private final com._DM.E_commerce.Repository.Produto repository;

    public Produto(com._DM.E_commerce.Repository.Produto repository) {
        this.repository = repository;
    }

    public List<com._DM.E_commerce.Entity.Produto> listar() {
        return repository.findAll();
    }

    public com._DM.E_commerce.Entity.Produto buscarPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado"));
    }

    public com._DM.E_commerce.Entity.Produto criar(com._DM.E_commerce.Entity.Produto produto) {
        produto.setId(null);
        return repository.save(produto);
    }

    public com._DM.E_commerce.Entity.Produto atualizar(UUID id, com._DM.E_commerce.Entity.Produto produtoAtualizado) {
        com._DM.E_commerce.Entity.Produto produto = buscarPorId(id);
        produto.setNome(produtoAtualizado.getNome());
        produto.setDescricao(produtoAtualizado.getDescricao());
        produto.setPreco(produtoAtualizado.getPreco());
        produto.setEstoque(produtoAtualizado.getEstoque());
        produto.setCategoria(produtoAtualizado.getCategoria());
        return repository.save(produto);
    }

    public void remover(UUID id) {
        repository.delete(buscarPorId(id));
    }
}

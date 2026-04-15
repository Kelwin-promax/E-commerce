package com._DM.E_commerce.Service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service("categoriaService")
public class Categoria {

    private final com._DM.E_commerce.Repository.Categoria repository;

    public Categoria(com._DM.E_commerce.Repository.Categoria repository) {
        this.repository = repository;
    }

    public List<com._DM.E_commerce.Entity.Categoria> listar() {
        return repository.findAll();
    }

    public com._DM.E_commerce.Entity.Categoria buscarPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria nao encontrada"));
    }

    public com._DM.E_commerce.Entity.Categoria criar(com._DM.E_commerce.Entity.Categoria categoria) {
        categoria.setId(null);
        return repository.save(categoria);
    }

    public com._DM.E_commerce.Entity.Categoria atualizar(UUID id, com._DM.E_commerce.Entity.Categoria categoriaAtualizada) {
        com._DM.E_commerce.Entity.Categoria categoria = buscarPorId(id);
        categoria.setNome(categoriaAtualizada.getNome());
        categoria.setDescricao(categoriaAtualizada.getDescricao());
        return repository.save(categoria);
    }

    public void remover(UUID id) {
        repository.delete(buscarPorId(id));
    }
}

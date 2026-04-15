package com._DM.E_commerce.Service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service("pedidoService")
public class Pedido {

    private final com._DM.E_commerce.Repository.Pedido repository;

    public Pedido(com._DM.E_commerce.Repository.Pedido repository) {
        this.repository = repository;
    }

    public List<com._DM.E_commerce.Entity.Pedido> listar() {
        return repository.findAll();
    }

    public com._DM.E_commerce.Entity.Pedido buscarPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido nao encontrado"));
    }

    public com._DM.E_commerce.Entity.Pedido criar(com._DM.E_commerce.Entity.Pedido pedido) {
        pedido.setId(null);
        return repository.save(pedido);
    }

    public com._DM.E_commerce.Entity.Pedido atualizar(UUID id, com._DM.E_commerce.Entity.Pedido pedidoAtualizado) {
        com._DM.E_commerce.Entity.Pedido pedido = buscarPorId(id);
        pedido.setMomento(pedidoAtualizado.getMomento());
        pedido.setStatus(pedidoAtualizado.getStatus());
        pedido.setCliente(pedidoAtualizado.getCliente());
        pedido.setPagamento(pedidoAtualizado.getPagamento());
        return repository.save(pedido);
    }

    public void remover(UUID id) {
        repository.delete(buscarPorId(id));
    }
}

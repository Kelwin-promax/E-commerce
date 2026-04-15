package com._DM.E_commerce.Service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service("pagamentoService")
public class Pagamento {

    private final com._DM.E_commerce.Repository.Pagamento repository;

    public Pagamento(com._DM.E_commerce.Repository.Pagamento repository) {
        this.repository = repository;
    }

    public List<com._DM.E_commerce.Entity.Pagamento> listar() {
        return repository.findAll();
    }

    public com._DM.E_commerce.Entity.Pagamento buscarPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pagamento nao encontrado"));
    }

    public com._DM.E_commerce.Entity.Pagamento criar(com._DM.E_commerce.Entity.Pagamento pagamento) {
        pagamento.setId(null);
        return repository.save(pagamento);
    }

    public com._DM.E_commerce.Entity.Pagamento atualizar(UUID id, com._DM.E_commerce.Entity.Pagamento pagamentoAtualizado) {
        com._DM.E_commerce.Entity.Pagamento pagamento = buscarPorId(id);
        pagamento.setMomento(pagamentoAtualizado.getMomento());
        pagamento.setMetodo(pagamentoAtualizado.getMetodo());
        pagamento.setValor(pagamentoAtualizado.getValor());
        pagamento.setPedido(pagamentoAtualizado.getPedido());
        return repository.save(pagamento);
    }

    public void remover(UUID id) {
        repository.delete(buscarPorId(id));
    }
}

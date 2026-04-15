package com._DM.E_commerce.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController("produtoController")
@RequestMapping("/produtos")
public class Produto {

    private final com._DM.E_commerce.Service.Produto service;

    public Produto(com._DM.E_commerce.Service.Produto service) {
        this.service = service;
    }

    @GetMapping
    public List<com._DM.E_commerce.Entity.Produto> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public com._DM.E_commerce.Entity.Produto buscarPorId(@PathVariable UUID id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public com._DM.E_commerce.Entity.Produto criar(@RequestBody com._DM.E_commerce.Entity.Produto produto) {
        return service.criar(produto);
    }

    @PutMapping("/{id}")
    public com._DM.E_commerce.Entity.Produto atualizar(@PathVariable UUID id,
                                                        @RequestBody com._DM.E_commerce.Entity.Produto produto) {
        return service.atualizar(id, produto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable UUID id) {
        service.remover(id);
    }
}

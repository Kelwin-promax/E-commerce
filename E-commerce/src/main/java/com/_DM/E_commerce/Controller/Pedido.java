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

@RestController("pedidoController")
@RequestMapping("/pedidos")
public class Pedido {

    private final com._DM.E_commerce.Service.Pedido service;

    public Pedido(com._DM.E_commerce.Service.Pedido service) {
        this.service = service;
    }

    @GetMapping
    public List<com._DM.E_commerce.Entity.Pedido> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public com._DM.E_commerce.Entity.Pedido buscarPorId(@PathVariable UUID id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public com._DM.E_commerce.Entity.Pedido criar(@RequestBody com._DM.E_commerce.Entity.Pedido pedido) {
        return service.criar(pedido);
    }

    @PutMapping("/{id}")
    public com._DM.E_commerce.Entity.Pedido atualizar(@PathVariable UUID id,
                                                       @RequestBody com._DM.E_commerce.Entity.Pedido pedido) {
        return service.atualizar(id, pedido);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable UUID id) {
        service.remover(id);
    }
}

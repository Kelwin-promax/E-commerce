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

@RestController("categoriaController")
@RequestMapping("/categorias")
public class Categoria {

    private final com._DM.E_commerce.Service.Categoria service;

    public Categoria(com._DM.E_commerce.Service.Categoria service) {
        this.service = service;
    }

    @GetMapping
    public List<com._DM.E_commerce.Entity.Categoria> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public com._DM.E_commerce.Entity.Categoria buscarPorId(@PathVariable UUID id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public com._DM.E_commerce.Entity.Categoria criar(@RequestBody com._DM.E_commerce.Entity.Categoria categoria) {
        return service.criar(categoria);
    }

    @PutMapping("/{id}")
    public com._DM.E_commerce.Entity.Categoria atualizar(@PathVariable UUID id,
                                                          @RequestBody com._DM.E_commerce.Entity.Categoria categoria) {
        return service.atualizar(id, categoria);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable UUID id) {
        service.remover(id);
    }
}

package com._DM.E_commerce.Controller;

import com._DM.E_commerce.Entity.Carrinho;
import com._DM.E_commerce.Service.CarrinhoService;
import com._DM.E_commerce.dto.AtualizarQuantidadeItemRequest;
import com._DM.E_commerce.dto.CarrinhoItemRequest;
import jakarta.validation.Valid;
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

import java.util.UUID;

@RestController
@RequestMapping("/carrinhos")
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    public CarrinhoController(CarrinhoService carrinhoService) {
        this.carrinhoService = carrinhoService;
    }

    @GetMapping("/cliente/{clienteId}")
    public Carrinho buscarPorCliente(@PathVariable UUID clienteId) {
        return carrinhoService.buscarOuCriarPorCliente(clienteId);
    }

    @PostMapping("/cliente/{clienteId}/itens")
    @ResponseStatus(HttpStatus.CREATED)
    public Carrinho adicionarItem(@PathVariable UUID clienteId,
                                  @RequestBody @Valid CarrinhoItemRequest request) {
        return carrinhoService.adicionarItem(clienteId, request);
    }

    @PutMapping("/itens/{itemId}")
    public Carrinho atualizarQuantidade(@PathVariable UUID itemId,
                                        @RequestBody @Valid AtualizarQuantidadeItemRequest request) {
        return carrinhoService.atualizarQuantidadeItem(itemId, request.quantidade());
    }

    @DeleteMapping("/itens/{itemId}")
    public Carrinho removerItem(@PathVariable UUID itemId) {
        return carrinhoService.removerItem(itemId);
    }

    @DeleteMapping("/{carrinhoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void limparCarrinho(@PathVariable UUID carrinhoId) {
        carrinhoService.limparCarrinho(carrinhoId);
    }
}

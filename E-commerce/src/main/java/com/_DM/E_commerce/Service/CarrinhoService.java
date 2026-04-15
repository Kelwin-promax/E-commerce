package com._DM.E_commerce.Service;

import com._DM.E_commerce.Entity.Carrinho;
import com._DM.E_commerce.Entity.ItemCarrinho;
import com._DM.E_commerce.Entity.Usuario;
import com._DM.E_commerce.Repository.CarrinhoRepository;
import com._DM.E_commerce.Repository.ItemCarrinhoRepository;
import com._DM.E_commerce.Repository.Produto;
import com._DM.E_commerce.Repository.UsuarioRepository;
import com._DM.E_commerce.dto.CarrinhoItemRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;
    private final ItemCarrinhoRepository itemCarrinhoRepository;
    private final UsuarioRepository usuarioRepository;
    private final Produto produtoRepository;

    public CarrinhoService(CarrinhoRepository carrinhoRepository,
                           ItemCarrinhoRepository itemCarrinhoRepository,
                           UsuarioRepository usuarioRepository,
                           Produto produtoRepository) {
        this.carrinhoRepository = carrinhoRepository;
        this.itemCarrinhoRepository = itemCarrinhoRepository;
        this.usuarioRepository = usuarioRepository;
        this.produtoRepository = produtoRepository;
    }

    public Carrinho buscarPorCliente(UUID clienteId) {
        validarCliente(clienteId);
        return carrinhoRepository.findByClienteId(clienteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrinho nao encontrado"));
    }

    public Carrinho buscarOuCriarPorCliente(UUID clienteId) {
        Usuario cliente = validarCliente(clienteId);
        return carrinhoRepository.findByClienteId(clienteId)
                .orElseGet(() -> carrinhoRepository.save(new Carrinho(
                        null,
                        cliente,
                        new java.util.ArrayList<>(),
                        BigDecimal.ZERO,
                        BigDecimal.ZERO,
                        BigDecimal.ZERO,
                        BigDecimal.ZERO,
                        LocalDateTime.now(),
                        LocalDateTime.now()
                )));
    }

    public Carrinho adicionarItem(UUID clienteId, CarrinhoItemRequest request) {
        Carrinho carrinho = buscarOuCriarPorCliente(clienteId);
        com._DM.E_commerce.Entity.Produto produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado"));

        if (produto.getEstoque() == null || produto.getEstoque() < request.quantidade()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estoque insuficiente para o produto");
        }

        Optional<ItemCarrinho> itemExistente = itemCarrinhoRepository
                .findByCarrinhoIdAndProdutoId(carrinho.getId(), request.produtoId());

        if (itemExistente.isPresent()) {
            ItemCarrinho item = itemExistente.get();
            int novaQuantidade = item.getQuantidade() + request.quantidade();
            validarQuantidade(produto, novaQuantidade);
            item.setQuantidade(novaQuantidade);
            item.setPrecoUnitario(produto.getPreco());
            item.setSubtotal(produto.getPreco().multiply(BigDecimal.valueOf(novaQuantidade)));
            itemCarrinhoRepository.save(item);
        } else {
            ItemCarrinho novoItem = new ItemCarrinho();
            novoItem.setCarrinho(carrinho);
            novoItem.setProduto(produto);
            novoItem.setQuantidade(request.quantidade());
            novoItem.setPrecoUnitario(produto.getPreco());
            novoItem.setSubtotal(produto.getPreco().multiply(BigDecimal.valueOf(request.quantidade())));
            carrinho.getItens().add(itemCarrinhoRepository.save(novoItem));
        }

        recalcularTotais(carrinho);
        return carrinhoRepository.save(carrinho);
    }

    public Carrinho atualizarQuantidadeItem(UUID itemId, Integer quantidade) {
        ItemCarrinho item = buscarItemPorId(itemId);
        validarQuantidade(item.getProduto(), quantidade);

        item.setQuantidade(quantidade);
        item.setPrecoUnitario(item.getProduto().getPreco());
        item.setSubtotal(item.getProduto().getPreco().multiply(BigDecimal.valueOf(quantidade)));
        itemCarrinhoRepository.save(item);

        Carrinho carrinho = item.getCarrinho();
        recalcularTotais(carrinho);
        return carrinhoRepository.save(carrinho);
    }

    public Carrinho removerItem(UUID itemId) {
        ItemCarrinho item = buscarItemPorId(itemId);
        Carrinho carrinho = item.getCarrinho();
        carrinho.getItens().removeIf(carrinhoItem -> carrinhoItem.getId().equals(itemId));
        itemCarrinhoRepository.delete(item);
        recalcularTotais(carrinho);
        return carrinhoRepository.save(carrinho);
    }

    public void limparCarrinho(UUID carrinhoId) {
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrinho nao encontrado"));
        carrinho.getItens().clear();
        carrinho.setSubtotal(BigDecimal.ZERO);
        carrinho.setTotal(BigDecimal.ZERO);
        carrinho.setAtualizadoEm(LocalDateTime.now());
        carrinhoRepository.save(carrinho);
    }

    private ItemCarrinho buscarItemPorId(UUID itemId) {
        return itemCarrinhoRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item do carrinho nao encontrado"));
    }

    private Usuario validarCliente(UUID clienteId) {
        return usuarioRepository.findById(clienteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario nao encontrado"));
    }

    private void validarQuantidade(com._DM.E_commerce.Entity.Produto produto, Integer quantidade) {
        if (quantidade == null || quantidade <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantidade deve ser maior que zero");
        }
        if (produto.getEstoque() == null || produto.getEstoque() < quantidade) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estoque insuficiente para o produto");
        }
    }

    private void recalcularTotais(Carrinho carrinho) {
        BigDecimal subtotal = carrinho.getItens().stream()
                .map(ItemCarrinho::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        carrinho.setSubtotal(subtotal);
        carrinho.setTotal(subtotal
                .subtract(carrinho.getDesconto() == null ? BigDecimal.ZERO : carrinho.getDesconto())
                .add(carrinho.getFreteEstimado() == null ? BigDecimal.ZERO : carrinho.getFreteEstimado()));
        carrinho.setAtualizadoEm(LocalDateTime.now());
    }
}

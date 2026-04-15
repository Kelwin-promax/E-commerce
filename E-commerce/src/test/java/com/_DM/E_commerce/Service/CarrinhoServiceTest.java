package com._DM.E_commerce.Service;

import com._DM.E_commerce.Entity.Carrinho;
import com._DM.E_commerce.Entity.ItemCarrinho;
import com._DM.E_commerce.Entity.Produto;
import com._DM.E_commerce.Entity.Usuario;
import com._DM.E_commerce.Repository.CarrinhoRepository;
import com._DM.E_commerce.Repository.ItemCarrinhoRepository;
import com._DM.E_commerce.Repository.UsuarioRepository;
import com._DM.E_commerce.dto.CarrinhoItemRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarrinhoServiceTest {

    @Mock
    private CarrinhoRepository carrinhoRepository;

    @Mock
    private ItemCarrinhoRepository itemCarrinhoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private com._DM.E_commerce.Repository.Produto produtoRepository;

    @InjectMocks
    private CarrinhoService carrinhoService;

    private UUID clienteId;
    private UUID produtoId;
    private Usuario usuario;
    private Produto produto;
    private Carrinho carrinho;

    @BeforeEach
    void setUp() {
        clienteId = UUID.randomUUID();
        produtoId = UUID.randomUUID();

        usuario = new Usuario();
        usuario.setId(clienteId);
        usuario.setLogin("cliente");
        usuario.setSenha("123");
        usuario.setRole("USER");

        produto = new Produto();
        produto.setId(produtoId);
        produto.setNome("Notebook");
        produto.setPreco(new BigDecimal("100.00"));
        produto.setEstoque(10);

        carrinho = new Carrinho();
        carrinho.setId(UUID.randomUUID());
        carrinho.setCliente(usuario);
        carrinho.setItens(new ArrayList<>());
        carrinho.setSubtotal(BigDecimal.ZERO);
        carrinho.setDesconto(BigDecimal.ZERO);
        carrinho.setFreteEstimado(BigDecimal.ZERO);
        carrinho.setTotal(BigDecimal.ZERO);
        carrinho.setCriadoEm(LocalDateTime.now());
        carrinho.setAtualizadoEm(LocalDateTime.now());
    }

    @Test
    void deveAdicionarItemENormalizarTotais() {
        when(usuarioRepository.findById(clienteId)).thenReturn(Optional.of(usuario));
        when(carrinhoRepository.findByClienteId(clienteId)).thenReturn(Optional.of(carrinho));
        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produto));
        when(itemCarrinhoRepository.findByCarrinhoIdAndProdutoId(carrinho.getId(), produtoId)).thenReturn(Optional.empty());
        when(itemCarrinhoRepository.save(ArgumentMatchers.any(ItemCarrinho.class))).thenAnswer(invocation -> {
            ItemCarrinho item = invocation.getArgument(0);
            if (item.getId() == null) {
                item.setId(UUID.randomUUID());
            }
            return item;
        });
        when(carrinhoRepository.save(ArgumentMatchers.any(Carrinho.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Carrinho resultado = carrinhoService.adicionarItem(clienteId, new CarrinhoItemRequest(produtoId, 2));

        assertEquals(1, resultado.getItens().size());
        assertEquals(new BigDecimal("200.00"), resultado.getSubtotal());
        assertEquals(new BigDecimal("200.00"), resultado.getTotal());
    }

    @Test
    void deveFalharQuandoQuantidadeUltrapassaEstoque() {
        when(usuarioRepository.findById(clienteId)).thenReturn(Optional.of(usuario));
        when(carrinhoRepository.findByClienteId(clienteId)).thenReturn(Optional.of(carrinho));
        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produto));

        assertThrows(RuntimeException.class,
                () -> carrinhoService.adicionarItem(clienteId, new CarrinhoItemRequest(produtoId, 20)));
    }
}

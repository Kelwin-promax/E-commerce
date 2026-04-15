package com._DM.E_commerce.Entity;

import com._DM.E_commerce.Enum.StatusDoPedido;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDateTime momento;

    @Enumerated(EnumType.STRING)
    private StatusDoPedido status;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonIgnoreProperties({"pedidos", "enderecos"})
    private Usuario cliente;

    @ManyToOne
    @JoinColumn(name = "endereco_entrega_id", nullable = false)
    private Endereco enderecoEntrega;

    @OneToOne(mappedBy = "pedido")
    private Pagamento pagamento;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("pedido")
    private List<ItemPedido> itens = new ArrayList<>();

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("pedido")
    private List<HistoricoStatusPedido> historicoStatus = new ArrayList<>();

    @Column(nullable = false)
    private BigDecimal subtotal = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal desconto = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal frete = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal total = BigDecimal.ZERO;
}

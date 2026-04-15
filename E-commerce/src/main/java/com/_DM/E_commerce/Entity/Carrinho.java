package com._DM.E_commerce.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "cliente_id", nullable = false, unique = true)
    @JsonIgnoreProperties({"pedidos", "enderecos"})
    private Usuario cliente;

    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("carrinho")
    private List<ItemCarrinho> itens = new ArrayList<>();

    @Column(nullable = false)
    private BigDecimal subtotal = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal desconto = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal freteEstimado = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal total = BigDecimal.ZERO;

    @Column(nullable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime atualizadoEm = LocalDateTime.now();
}

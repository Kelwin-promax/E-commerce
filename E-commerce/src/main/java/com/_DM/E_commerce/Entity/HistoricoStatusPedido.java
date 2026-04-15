package com._DM.E_commerce.Entity;

import com._DM.E_commerce.Enum.StatusDoPedido;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoStatusPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    @JsonIgnoreProperties({"itens", "historicoStatus"})
    private Pedido pedido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusDoPedido status;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Column(nullable = false)
    private String descricao;
}

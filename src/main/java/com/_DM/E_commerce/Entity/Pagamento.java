package com._DM.E_commerce.Entity;

import com._DM.E_commerce.Enum.StatusDoPedido;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Pagamento {

    @Id
    private UUID id;
    private Instant momento;

    @OneToOne
    @JoinColumn(name = "pedido_id")
    @MapsId
    private Pedido pedido;

    public Pagamento(UUID id, Instant momento, Pedido pedido) {
        this.id = id;
        this.momento = momento;
        this.pedido = pedido;
    }
}

package com._DM.E_commerce.Entity;

import com._DM.E_commerce.Enum.StatusDoPedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
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
    private LocalDate momento;
    private StatusDoPedido status;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Usuario cliente;

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Pagamento pagamento;

    @OneToMany(mappedBy = "id.pedido")
    private Set<ItemDoPedido> itens = new HashSet<>();

    public Double getTotal() {
        double sum = 0.0;
        for (ItemDoPedido x : itens) {
            sum += x.getSubTotal();
        }
        return sum;
    }
}
